package com.example.demo.dtos;


import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class UserDetailsDTO {

    private UUID user_id;

    @NotBlank(message = "first_name is required")
    private String firstName;
    @NotBlank(message = "last_name is required")
    private String lastName;
    @NotBlank(message = "email is required")
    private String email;
    @NotBlank(message = "phone_number is required")
    private String phoneNumber;
    @NotBlank(message = "address is required")
    private String address;

    private LocalDate birthDate;


    public UserDetailsDTO() {
    }

    public UserDetailsDTO(String firstName, String lastName, String email, String phoneNumber, String address, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.birthDate = birthDate;
    }

    public UserDetailsDTO(UUID user_id, String firstName, String lastName, String email, String phoneNumber, String address, LocalDate birthDate) {
        this.user_id = user_id;
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


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        UserDetailsDTO that = (UserDetailsDTO) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(email, that.email) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(address, that.address) &&
                Objects.equals(birthDate, that.birthDate) ; //oare aici ar trebui altceva?
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, phoneNumber, address, birthDate);
    }
}
