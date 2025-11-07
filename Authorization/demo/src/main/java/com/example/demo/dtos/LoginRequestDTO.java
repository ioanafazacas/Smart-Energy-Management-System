package com.example.demo.dtos.builders;

import com.example.demo.dtos.UserDTO;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class LoginRequestDTO {
    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "password is required")
    private String password;

    public LoginRequestDTO() {}

    public LoginRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
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

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginRequestDTO that = (LoginRequestDTO) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(password, that.password);

    }
    @Override public int hashCode() { return Objects.hash(username, password); }
}
