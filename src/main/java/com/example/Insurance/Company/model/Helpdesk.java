
/**
 * This class represents a helpdesk request in the application.
 * It is an entity class that is mapped to the "helpdesk" table in the database.
 * The class includes information about the helpdesk request, such as the user profile, phone number, problem description, and status.
 */
package com.example.Insurance.Company.model;

import jakarta.persistence.*;

@Entity
@Table(name = "helpdesk")
public class Helpdesk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_profile_id", nullable = false)
    private UserProfile userProfile;

    @Column(name = "phone_number", nullable = false, length = 45)
    private String phoneNumber;

    @Lob
    @Column(name = "problem", nullable = false)
    private String problem;

    @Column(name = "status", nullable = false, length = 45)
    private String status;

    public Helpdesk() {
    }

    public Helpdesk(UserProfile userProfile, String phoneNumber, String problem, String status) {
        this.userProfile = userProfile;
        this.phoneNumber = phoneNumber;
        this.problem = problem;
        this.status = status;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}