package com.example.demo.dtos.builders;

import com.example.demo.dtos.UserDTO;
import com.example.demo.dtos.RegistrationRequestDTO;
import com.example.demo.entities.Role;
import com.example.demo.entities.User;

public class UserBuilder {

    private UserBuilder() {
    }

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(user.getUser_id(), user.getUsername(), user.getRole().getRoleName());
    }
//asta poate fi stearsa
    public static RegistrationRequestDTO toUserDetailsDTO(User user) {
        return new RegistrationRequestDTO(RoleBuilder.toRoleDTO(user.getRole()),
                        user.getUsername(),
                        user.getPassword(),
                user.getUser_id()
                        );
    }

    public static User toEntity(RegistrationRequestDTO registrationRequestDTO, Role role) {
        return new User(
                registrationRequestDTO.getUsername(),
                registrationRequestDTO.getPassword(),
                role
        );
    }
}
