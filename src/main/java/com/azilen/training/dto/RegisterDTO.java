package com.azilen.training.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class RegisterDTO {

    @Email(message = "Email format is invalid.")
    @NotBlank(message = "Email cannot be empty. Please provide your email address.")
    private String email;

    @Pattern(regexp = "^[A-Za-z]+$", message = "First name can only contain alphabets.")
    @NotBlank(message = "First name cannot be empty. Please enter your first name.")
    private String firstName;

    @Pattern(regexp = "^[A-Za-z]+$", message = "Last name can only contain alphabets.")
    @NotBlank(message = "Last name cannot be empty. Please enter your last name.")
    private String lastName;

    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]*$", message = "Username must start with an alphabet and can contain only letters, numbers, and underscores.")
    @NotBlank(message = "Username cannot be empty. Please enter a username.")
    private String username;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
            message = "Password must be 8-20 characters long and include at least one uppercase letter, one lowercase letter, one number, and one special character.")
    @NotBlank(message = "Password cannot be empty. Please enter a password.")
    private String password;

    @NotBlank(message = "Please confirm your password.")
    private String confirmPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}