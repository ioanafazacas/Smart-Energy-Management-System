package com.example.demo.controllers;

import com.example.demo.dtos.*;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthentificationController {
    private final UserService userService;

    public AuthentificationController(UserService userService) {
        this.userService = userService;
    }


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


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
//        System.out.println("login???");
//        System.out.println(dto);
        String jwt = userService.login(dto);

        UserDTO userDto = userService.getUserByUsername(dto.getUsername());

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(jwt, userDto);

        return ResponseEntity.ok(loginResponseDTO);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        System.out.println(username);
        UserDTO userDto = userService.getUserByUsername(username);
        return ResponseEntity.ok(userDto);
    }

}
