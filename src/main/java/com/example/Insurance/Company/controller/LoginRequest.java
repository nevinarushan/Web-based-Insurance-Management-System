
/**
 * This class is a request body class used for user login.
 * It contains fields for the user's email and password.
 */
package com.example.Insurance.Company.controller;

public class LoginRequest {
    private String email;
    private String password;

    // Getters and Setters
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
}
