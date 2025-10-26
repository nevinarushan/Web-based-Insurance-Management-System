
/**
 * This class is a Data Transfer Object (DTO) for transferring detailed information about a claim.
 * It includes the Claims object, the UserProfile object associated with the claim, and the Policy object associated with the claim.
 */
package com.example.Insurance.Company.dto;

import com.example.Insurance.Company.model.Claims;
import com.example.Insurance.Company.model.Policy;
import com.example.Insurance.Company.model.UserProfile;

public class ClaimDetailsDTO {

    private Claims claim;
    private UserProfile userProfile;
    private Policy policy;

    public ClaimDetailsDTO(Claims claim, UserProfile userProfile, Policy policy) {
        this.claim = claim;
        this.userProfile = userProfile;
        this.policy = policy;
    }

    // Getters and Setters
    public Claims getClaim() {
        return claim;
    }

    public void setClaim(Claims claim) {
        this.claim = claim;
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
}
