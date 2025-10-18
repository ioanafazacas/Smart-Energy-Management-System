package com.example.demo.dtos;


import com.example.demo.entities.Role;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;
import java.util.UUID;

public class UserDetailsDTO {

    private UUID user_id;

    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "password is required")
    private String password;
    @NotBlank(message = "roleId is required")
    private Role role;



    public UserDetailsDTO() {
    }

    public UserDetailsDTO(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public UserDetailsDTO(UUID userId, String username, String password, Role role) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.role = role;
    }


    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetailsDTO that = (UserDetailsDTO) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
