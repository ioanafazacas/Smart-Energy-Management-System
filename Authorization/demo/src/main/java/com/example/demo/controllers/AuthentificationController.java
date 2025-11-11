package com.example.demo.controllers;

import com.example.demo.dtos.*;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@Validated
@Tag(name = "Authentication API", description = "Autentificare, înregistrare și gestionarea utilizatorilor în serviciul Auth")
public class AuthentificationController {
    private final UserService userService;

    public AuthentificationController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Înregistrează un utilizator nou", description = "Creează un nou cont de utilizator în sistemul de autentificare.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Utilizator înregistrat cu succes",
                    content = @Content(schema = @Schema(implementation = UserDetailsDTO.class))),
            @ApiResponse(responseCode = "400", description = "Date de înregistrare invalide")
    })
    @PostMapping("/register")
    public ResponseEntity<UserDetailsDTO> register(@Valid @RequestBody RegistrationRequestDTO user) {
        UUID id = userService.register(user);
        UserDetailsDTO dto = userService.
                getUserById(id);
        if (dto == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Autentificare utilizator", description = "Returnează un token JWT și detaliile utilizatorului.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autentificare reușită",
                    content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Autentificare eșuată")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
//        System.out.println("login???");
//        System.out.println(dto);
        String jwt = userService.login(dto);

        UserDTO userDto = userService.getUserByUsername(dto.getUsername());

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(jwt, userDto);

        return ResponseEntity.ok(loginResponseDTO);
    }

    @Operation(summary = "Obține detaliile utilizatorului curent", description = "Returnează datele utilizatorului logat curent.")
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        System.out.println(username);
        UserDTO userDto = userService.getUserByUsername(username);
        return ResponseEntity.ok(userDto);
    }

    @Operation(summary = "Șterge un utilizator", description = "Șterge un utilizator din sistemul de autentificare.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (Exception e) {
            System.err.println("❌ Eroare la ștergerea utilizatorului din AUTH: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }


}
