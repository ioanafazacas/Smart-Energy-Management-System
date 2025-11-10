package com.example.demo.dtos.builders;

import com.example.demo.dtos.UserDTO;
import com.example.demo.dtos.UserDetailsDTO;
import com.example.demo.entities.User;

public class UserBuilder {

    private UserBuilder() {
    }
//trenuie facute modificari , nu stim ce anume vrem sa transmitem prin obiectul de tip dto
    public static UserDTO toUserDTO(User user) {
        return new UserDTO(user.getUser_id(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail());
    }
//asta poate fi stearsa
    public static UserDetailsDTO toUserDetailsDTO(User user) {
        return new UserDetailsDTO(user.getUser_id(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAddress(),
                user.getBirthDate());
    }

    public static User toEntity(UserDetailsDTO userDetailsDTO) {
        return new User(userDetailsDTO.getUser_id(),
                userDetailsDTO.getFirstName(),
                userDetailsDTO.getLastName(),
                userDetailsDTO.getEmail(),
                userDetailsDTO.getPhoneNumber(),
                userDetailsDTO.getAddress(),
                userDetailsDTO.getBirthDate());
    }
}
