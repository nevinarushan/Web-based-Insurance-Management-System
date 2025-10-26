
/**
 * This class is a REST controller that handles HTTP requests related to user profiles.
 * It provides endpoints for creating, retrieving, and updating user profiles.
 * The controller uses the UserProfileService to perform the business logic and ensures that users can only access and modify their own profiles.
 * The class is mapped to the "/api/user/profile" URL.
 */
package com.example.Insurance.Company.controller;

import com.example.Insurance.Company.service.CustomUserDetails;
import com.example.Insurance.Company.model.UserProfile;
import com.example.Insurance.Company.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/user/profile")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @PostMapping
    public ResponseEntity<?> saveUserProfile(@RequestBody UserProfile userProfile, Principal principal) {
        try {
            // By using Principal, we ensure only the logged-in user can create their own profile.
            // You would need a way to get the user ID from the principal (e.g., from a custom UserDetails object).
            // For this example, I'll assume you have a method to extract the user ID.
            // This is a placeholder and needs to be implemented based on your authentication setup.
            Integer userId = getUserIdFromPrincipal(principal);
            UserProfile savedProfile = userProfileService.saveUserProfile(userProfile, userId);
            return ResponseEntity.ok(savedProfile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable Integer userId, Principal principal) {
        // Ensure the logged-in user is requesting their own profile
        Integer principalId = getUserIdFromPrincipal(principal);
        if (!principalId.equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only view your own profile.");
        }

        return userProfileService.getUserProfileByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUserProfile(@PathVariable Integer userId, @RequestBody UserProfile userProfile, Principal principal) {
        // Ensure the logged-in user is updating their own profile
        Integer principalId = getUserIdFromPrincipal(principal);
        if (!principalId.equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only update your own profile.");
        }

        try {
            UserProfile updatedProfile = userProfileService.updateUserProfile(userProfile, userId);
            return ResponseEntity.ok(updatedProfile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
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
