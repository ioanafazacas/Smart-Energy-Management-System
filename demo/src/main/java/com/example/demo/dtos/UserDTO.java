package com.example.demo.dtos;

import java.util.Objects;
import java.util.UUID;

public class UserDTO {
    private UUID user_id;
    private String first_name;
    private String last_name;
    private String email;

    public UserDTO() {}

    public UserDTO(UUID user_id, String first_name , String last_name, String email ) {
        this.email = email;
        this.last_name = last_name;
        this.first_name = first_name;
        this.user_id = user_id;
    }

    public UUID getUser_id() { return user_id; }
    public void setUser_id(UUID user_id) { this.user_id = user_id; }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO that = (UserDTO) o;
        return Objects.equals(first_name, that.first_name) && Objects.equals(last_name, that.last_name) && Objects.equals(email, that.email);
    }
    @Override public int hashCode() { return Objects.hash(first_name, last_name, email); }
}
