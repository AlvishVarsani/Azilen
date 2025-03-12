package com.azilen.training.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserUpdateDTO {
    @Pattern(regexp = "^[A-Za-z]+$", message = "First name can only contain alphabets.")
    @NotBlank(message = "First name cannot be empty. Please enter your first name.")
    private String firstName;

    @Pattern(regexp = "^[A-Za-z]+$", message = "Last name can only contain alphabets.")
    @NotBlank(message = "Last name cannot be empty. Please enter your last name.")
    private String lastName;

    @Email(message = "Email format is invalid.")
    @NotBlank(message = "Email cannot be empty. Please provide your email address.")
    private String email;

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
}
