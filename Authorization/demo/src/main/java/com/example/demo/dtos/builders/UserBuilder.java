package com.example.demo.dtos.builders;

import com.example.demo.dtos.RoleDTO;
import com.example.demo.dtos.UserDTO;
import com.example.demo.dtos.UserDetailsDTO;
import com.example.demo.entities.Role;
import com.example.demo.entities.User;

public class UserBuilder {

    private UserBuilder() {
    }
//trenuie facute modificari , nu stim ce anume vrem sa transmitem prin obiectul de tip dto
    public static UserDTO toUserDTO(User user) {
        return new UserDTO(user.getUser_id(), user.getUsername());
    }
//asta poate fi stearsa
    public static UserDetailsDTO toUserDetailsDTO(User user) {
        return new UserDetailsDTO(user.getUser_id(),
                user.getUsername(),
                user.getPassword(),
                new RoleDTO(user.getRole().getRole_id(), user.getRole().getRole_name()));
    }

    public static User toEntity(UserDetailsDTO userDetailsDTO) {
        return new User(userDetailsDTO.getUsername(),
                userDetailsDTO.getPassword(),
                new Role(userDetailsDTO.getRole().getRole_id(), userDetailsDTO.getRole().getRole_name()));

    }
}
