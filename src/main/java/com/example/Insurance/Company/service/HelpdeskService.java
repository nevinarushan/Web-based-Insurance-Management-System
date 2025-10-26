
/**
 * This class provides the business logic for managing helpdesk requests.
 * It includes methods for creating, retrieving, and updating helpdesk requests.
 * The class interacts with the HelpdeskRepository and UserProfileRepository to perform database operations.
 */
package com.example.Insurance.Company.service;

import com.example.Insurance.Company.dto.HelpdeskDTO;
import com.example.Insurance.Company.model.Helpdesk;
import com.example.Insurance.Company.model.UserProfile;
import com.example.Insurance.Company.repository.HelpdeskRepository;
import com.example.Insurance.Company.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HelpdeskService {

    @Autowired
    private HelpdeskRepository helpdeskRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    public Helpdesk saveHelpdeskRequest(HelpdeskDTO helpdeskDTO) throws Exception {
        UserProfile userProfile = userProfileRepository.findByUserId(helpdeskDTO.getUserId().intValue())
                .orElseThrow(() -> new Exception("User profile not found"));

        Helpdesk helpdesk = new Helpdesk();
        helpdesk.setUserProfile(userProfile);
        helpdesk.setPhoneNumber(helpdeskDTO.getPhoneNumber());
        helpdesk.setProblem(helpdeskDTO.getProblem());
        helpdesk.setStatus("pending");

        return helpdeskRepository.save(helpdesk);
    }

    public List<HelpdeskDTO> getHelpdeskRequestsByUserId(Long userId) {
        return helpdeskRepository.findByUserProfile_User_Id(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<HelpdeskDTO> getAllHelpdeskRequests() {
        return helpdeskRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public HelpdeskDTO getHelpdeskRequestById(Long id) throws Exception {
        Helpdesk helpdesk = helpdeskRepository.findById(id)
                .orElseThrow(() -> new Exception("Helpdesk request not found"));
        return convertToDTO(helpdesk);
    }

    private HelpdeskDTO convertToDTO(Helpdesk helpdesk) {
        UserProfile userProfile = helpdesk.getUserProfile();
        return new HelpdeskDTO(
                helpdesk.getId(),
                userProfile.getUser().getId().longValue(),
                helpdesk.getPhoneNumber(),
                helpdesk.getProblem(),
                userProfile.getFullName(),
                userProfile.getAddress(),
                userProfile.getAge(),
                userProfile.getBirthDate(),
                userProfile.getOccupation(),
                helpdesk.getStatus()
        );
    }

    public Helpdesk updateHelpdeskStatus(Long id, String status) throws Exception {
        Helpdesk helpdesk = helpdeskRepository.findById(id)
                .orElseThrow(() -> new Exception("Helpdesk request not found"));
        helpdesk.setStatus(status);
        return helpdeskRepository.save(helpdesk);
    }
}
