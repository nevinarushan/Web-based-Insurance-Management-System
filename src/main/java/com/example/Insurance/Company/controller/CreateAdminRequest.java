
/**
 * This class is a request body class used for creating a new admin user.
 * It contains fields for the admin's username, email, password, role, and sub-role.
 */
package com.example.Insurance.Company.controller;

public class CreateAdminRequest {
    private String username;
    private String email;
    private String password;
    private String role;
    private String subrole;

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getSubrole() {
        return subrole;
    }

    public void setSubrole(String subrole) {
        this.subrole = subrole;
    }
}
