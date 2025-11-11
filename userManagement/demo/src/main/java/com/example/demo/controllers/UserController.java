package com.example.demo.controllers;

import com.example.demo.dtos.UserDTO;
import com.example.demo.dtos.UserDetailsDTO;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@Validated
@Tag(name = "User Management API", description = "Operații CRUD pentru gestionarea utilizatorilor")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDetailsDTO>> getPeople() {
        return ResponseEntity.ok(userService.findUsers());
    }

    @Operation(summary = "Creează un utilizator nou", description = "Adaugă un nou utilizator în sistemul User Management.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Utilizator creat cu succes"),
            @ApiResponse(responseCode = "400", description = "Date invalide")
    })
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody UserDetailsDTO user) {
        UUID id = userService.insert(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build(); // 201 + Location header
    }

    @Operation(summary = "Obține detaliile unui utilizator", description = "Returnează informațiile unui utilizator pe baza ID-ului.")
    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDTO> getUser(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @Operation(summary = "Obține lista tuturor utilizatorilor", description = "Returnează toți utilizatorii existenți.")
    @GetMapping("/all")
    public ResponseEntity<List<UserDetailsDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findUsers());
    }

    @Operation(summary = "Șterge un utilizator din userManagement si din auth microservice", description = "Șterge utilizatorul și dispozitivele asociate.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    @Operation(summary = "Actualizează un utilizator", description = "Modifică datele unui utilizator existent.")
    @PutMapping("/{id}")
    public ResponseEntity<UserDetailsDTO> updateUser(@PathVariable UUID id,
        @RequestBody UserDetailsDTO userDetailsDTO) {

            UserDetailsDTO updatedUser = userService.updateUser(id, userDetailsDTO);
            return ResponseEntity.ok(updatedUser);
    }
}
