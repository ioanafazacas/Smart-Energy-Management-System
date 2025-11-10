package com.example.demo.services;


import com.example.demo.config.JwtUtil;
import com.example.demo.dtos.*;
import com.example.demo.dtos.builders.RoleBuilder;
import com.example.demo.dtos.builders.UserBuilder;
import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.handlers.exceptions.model.ResourceNotFoundException;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtUtil = jwtUtil;
    }

    public UUID register(RegistrationRequestDTO userDTO) {
        Role userRole = roleRepository.findByRoleName("USER")
                .orElseThrow(() -> new RuntimeException("Default role USER not found"));

        if(userDTO.getRole()==null)
            userDTO.setRole(RoleBuilder.toRoleDTO(userRole));

        User user = UserBuilder.toEntity(userDTO,userRole);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        user = userRepository.save(user);
        LOGGER.debug("User with id {} was inserted in db", user.getUser_id());

        sendUserDetailsToUserManagement(user.getUser_id(), userDTO);

        return user.getUser_id();
    }

    private void sendUserDetailsToUserManagement(UUID userId, RegistrationRequestDTO request) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            Map<String, Object> body = new HashMap<>();
            body.put("user_id", userId);
            body.put("firstName", request.getFirstName());
            body.put("lastName", request.getLastName());
            body.put("email", request.getEmail());
            body.put("phoneNumber", request.getPhoneNumber());
            body.put("address", request.getAddress());

            body.put("birthDate", request.getBirthDate() != null
                    ? request.getBirthDate().toString() // produces "1999-01-01"
                    : null);

            System.out.println("📤 Sending JSON to user_management: " + body);
            System.out.println("📤 Sending birthdate: " + request.getBirthDate());

            restTemplate.postForObject(
                    "http://user-management:8080/user",
                    body,
                    Void.class
            );

            System.out.println("✅ User details sent to UserManagement for ID: " + userId);

        } catch (Exception e) {
            System.err.println("❌ Failed to send user details to UserManagement: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public String login(LoginRequestDTO dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getUsername());
    }

    public UserDTO getUserByUsername(String username) {
        Optional<User> prosumerOptional = userRepository.findByUsername(username);
        if (prosumerOptional.isEmpty()) {
            LOGGER.error("User with username {} was not found in db", username);
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with username: " + username);
        }
        return UserBuilder.toUserDTO(prosumerOptional.get());
    }

    public UserDetailsDTO getUserById(UUID id) {
        Optional<User> prosumerOptional = userRepository.findById(id);
        if (prosumerOptional.isEmpty()) {
            LOGGER.error("User with id {} was not found in db", id);
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with id: " + id);
        }
        RegistrationRequestDTO dto = UserBuilder.toUserDetailsDTO(prosumerOptional.get());

        UserManagementDTO detailsFromManagement = getUserDetailsFromUserManagement(id,dto);


        UserDetailsDTO userDetailsDTO = new UserDetailsDTO(
                id,
                dto.getUsername(),
                dto.getRole(),
                detailsFromManagement.getFirstName(),
                detailsFromManagement.getLastName(),
                detailsFromManagement.getEmail(),
                detailsFromManagement.getPhoneNumber(),
                detailsFromManagement.getAddress(),
                detailsFromManagement.getBirthDate()
        );

        System.out.println(userDetailsDTO);

        return userDetailsDTO;
    }

    private UserManagementDTO getUserDetailsFromUserManagement(UUID userId, RegistrationRequestDTO request) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            String url = "http://user-management:8080/user/" + userId;

            return restTemplate.getForObject(url, UserManagementDTO.class);

        } catch (Exception e) {
            System.err.println("❌ Failed to get user details from UserManagement: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
