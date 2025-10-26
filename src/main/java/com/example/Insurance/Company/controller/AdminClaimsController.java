
/**
 * This class is a REST controller that handles HTTP requests related to claims from an admin perspective.
 * It provides endpoints for retrieving all claims, retrieving a specific claim by ID, and updating the status of a claim.
 * The controller uses the ClaimsService to perform the business logic.
 * The class is mapped to the "/api/admin/claims" URL.
 */
package com.example.Insurance.Company.controller;

import com.example.Insurance.Company.dto.ClaimDetailsDTO;
import com.example.Insurance.Company.model.Claims;
import com.example.Insurance.Company.service.ClaimsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/claims")
public class AdminClaimsController {

    @Autowired
    private ClaimsService claimsService;

    @GetMapping
    public ResponseEntity<List<ClaimDetailsDTO>> getAllClaims() {
        return ResponseEntity.ok(claimsService.getAllClaims());
    }

    @GetMapping("/{claimId}")
    public ResponseEntity<ClaimDetailsDTO> getClaimDetails(@PathVariable Long claimId) {
        return claimsService.getClaimDetailsById(claimId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{claimId}/status")
    public ResponseEntity<Claims> updateClaimStatus(@PathVariable Long claimId, @RequestBody Map<String, String> body) {
        String status = body.get("status");
        return claimsService.updateClaimStatus(claimId, status)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
