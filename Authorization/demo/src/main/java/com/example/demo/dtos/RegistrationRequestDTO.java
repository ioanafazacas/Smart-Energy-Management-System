package com.example.demo.dtos;


import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class RegistrationRequestDTO {

    private UUID user_id;

    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "password is required")
    private String password;
    private RoleDTO role;

    @NotBlank(message = "firstName is required")
    private String firstName;
    @NotBlank(message = "lastName is required")
    private String lastName;
    @NotBlank(message = "email is required")
    private String email;
    @NotBlank(message = "phone number is required")
    private String phoneNumber;
    @NotBlank(message = "address is required")
    private String address;
    private LocalDate birthDate;



    public RegistrationRequestDTO() {
    }

    public RegistrationRequestDTO(RoleDTO role,  String username,String password, UUID user_id) {
        this.role = role;
        this.password = password;
        this.username = username;
        this.user_id = user_id;
    }

    public RegistrationRequestDTO(UUID user_id, String username, String password, RoleDTO role, String firstName, String lastName, String email, String phoneNumber, String address, LocalDate birthDate) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.birthDate = birthDate;
    }

    public RegistrationRequestDTO(String username, String password, RoleDTO role, String firstName, String lastName, String email, String phoneNumber, String address, LocalDate birthDate) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.birthDate = birthDate;
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

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrationRequestDTO that = (RegistrationRequestDTO) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(address, that.address) &&
                Objects.equals(email, that.email) &&
                Objects.equals(birthDate, that.birthDate)
                ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, firstName, lastName, email, phoneNumber, address, birthDate);
    }
}
