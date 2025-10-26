
/**
 * This class represents a claim in the application.
 * It is an entity class that is mapped to the "claims" table in the database.
 * The class includes information about the claim, such as the user profile, the policy, and the status of the claim.
 */
package com.example.Insurance.Company.model;

import jakarta.persistence.*;

@Entity
@Table(name = "claims")
public class Claims {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_profile_id", referencedColumnName = "id", nullable = false)
    private UserProfile userProfile;

    @ManyToOne
    @JoinColumn(name = "policynum", referencedColumnName = "policynum", nullable = false)
    private Policy policy;

    @Column(name = "status", nullable = false)
    private String status = "Pending";

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

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
