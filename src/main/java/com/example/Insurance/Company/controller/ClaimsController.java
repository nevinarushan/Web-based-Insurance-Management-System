
/**
 * This class is a REST controller that handles HTTP requests related to claims from a user perspective.
 * It provides endpoints for creating a new claim and for retrieving claims for a specific user.
 * The controller uses the ClaimsService and UserProfileService to perform the business logic.
 * The class is mapped to the "/api/claims" URL.
 */
package com.example.Insurance.Company.controller;

import com.example.Insurance.Company.model.Claims;
import com.example.Insurance.Company.model.UserProfile;
import com.example.Insurance.Company.service.ClaimsService;
import com.example.Insurance.Company.service.CustomUserDetails;
import com.example.Insurance.Company.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/claims")
public class ClaimsController {

    @Autowired
    private ClaimsService claimsService;

    @Autowired
    private UserProfileService userProfileService;

    @PostMapping
    public ResponseEntity<?> createClaim(@RequestBody Claims claimRequest, Principal principal) {
        try {
            Integer userId = getUserIdFromPrincipal(principal);
            Optional<UserProfile> userProfileOptional = userProfileService.getUserProfileByUserId(userId);

            if (userProfileOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User profile not found. Please create your profile first.");
            }

            Claims newClaim = claimsService.createClaim(userProfileOptional.get().getId(), claimRequest.getPolicy().getPolicynum());
            return ResponseEntity.ok(newClaim);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getClaimsByUserId(@PathVariable Integer userId, Principal principal) {
        Integer principalId = getUserIdFromPrincipal(principal);
        if (!principalId.equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only view your own claims.");
        }

        Optional<UserProfile> userProfileOptional = userProfileService.getUserProfileByUserId(userId);
        if (userProfileOptional.isEmpty()) {
            return ResponseEntity.ok(List.of()); // No profile, so no claims
        }

        List<Claims> claims = claimsService.getClaimsByUserProfileId(userProfileOptional.get().getId());
        return ResponseEntity.ok(claims);
    }

    private Integer getUserIdFromPrincipal(Principal principal) {
        if (principal instanceof Authentication) {
            Object principalObject = ((Authentication) principal).getPrincipal();
            if (principalObject instanceof CustomUserDetails) {
                return ((CustomUserDetails) principalObject).getId();
            }
        }
        throw new IllegalStateException("Cannot determine user ID from principal");
    }
}
