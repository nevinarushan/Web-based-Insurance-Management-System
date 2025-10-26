
/**
 * This class provides the business logic for managing claims.
 * It includes methods for creating, retrieving, and updating claims.
 */
package com.example.Insurance.Company.service;

import com.example.Insurance.Company.dto.ClaimDetailsDTO;
import com.example.Insurance.Company.model.Claims;
import com.example.Insurance.Company.model.Policy;
import com.example.Insurance.Company.model.UserProfile;
import com.example.Insurance.Company.repository.ClaimsRepository;
import com.example.Insurance.Company.repository.PolicyRepository;
import com.example.Insurance.Company.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClaimsService {

    @Autowired
    private ClaimsRepository claimsRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private PolicyRepository policyRepository;

    public Claims createClaim(Long userProfileId, String policyNum) throws Exception {
        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(userProfileId);
        if (userProfileOptional.isEmpty()) {
            throw new Exception("User profile not found");
        }

        Optional<Policy> policyOptional = policyRepository.findById(policyNum);
        if (policyOptional.isEmpty()) {
            throw new Exception("Policy not found");
        }

        Claims claim = new Claims();
        claim.setUserProfile(userProfileOptional.get());
        claim.setPolicy(policyOptional.get());

        return claimsRepository.save(claim);
    }

    public List<Claims> getClaimsByUserProfileId(Long userProfileId) {
        return claimsRepository.findByUserProfileId(userProfileId);
    }

    public List<ClaimDetailsDTO> getAllClaims() {
        return claimsRepository.findAll().stream()
                .map(claim -> new ClaimDetailsDTO(claim, claim.getUserProfile(), claim.getPolicy()))
                .collect(Collectors.toList());
    }

    public Optional<ClaimDetailsDTO> getClaimDetailsById(Long claimId) {
        return claimsRepository.findById(claimId)
                .map(claim -> new ClaimDetailsDTO(claim, claim.getUserProfile(), claim.getPolicy()));
    }

    public Optional<Claims> updateClaimStatus(Long claimId, String status) {
        return claimsRepository.findById(claimId)
                .map(claim -> {
                    claim.setStatus(status);
                    return claimsRepository.save(claim);
                });
    }
}
