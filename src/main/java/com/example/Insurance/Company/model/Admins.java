
/**
 * This class represents an admin user in the application.
 * It is an entity class that is mapped to the "admins" table in the database.
 * The class includes information about the admin, such as their username, email, password, role, and sub-role.
 */
package com.example.Insurance.Company.model;

import jakarta.persistence.*;

@Entity
@Table(name = "admins")
public class Admins {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAdmin")
    private Integer idAdmin;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "subrole", nullable = true)
    private String subRole;

    // Getters and Setters
    public Integer getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Integer id) {
        this.idAdmin = id;
    }

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

    public String getSubRole() {
        return subRole;
    }

    public void setSubRole(String subRole) {
        this.subRole = subRole;
    }
}
