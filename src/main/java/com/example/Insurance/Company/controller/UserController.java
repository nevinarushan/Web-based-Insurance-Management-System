/**
 * REST controller for user-related operations including password management
 */
package com.example.Insurance.Company.controller;

import com.example.Insurance.Company.dto.ChangePasswordRequest;
import com.example.Insurance.Company.service.CustomUserDetails;
import com.example.Insurance.Company.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request,
                                          Principal principal) {
        try {
            Integer userId = getUserIdFromPrincipal(principal);

            userService.changePassword(userId, request.getOldPassword(), request.getNewPassword());

            Map<String, String> response = new HashMap<>();
            response.put("message", "Password changed successfully");
            response.put("status", "success");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            errorResponse.put("status", "error");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
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
