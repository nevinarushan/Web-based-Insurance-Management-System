
/**
 * This class is a request body class used for user registration.
 * It contains fields for the user's name, email, password, role, and an admin secret key (for creating admin users).
 */
package com.example.Insurance.Company.controller;

public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String role;
    private String adminSecretKey;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAdminSecretKey() {
        return adminSecretKey;
    }

    public void setAdminSecretKey(String adminSecretKey) {
        this.adminSecretKey = adminSecretKey;
    }
}
