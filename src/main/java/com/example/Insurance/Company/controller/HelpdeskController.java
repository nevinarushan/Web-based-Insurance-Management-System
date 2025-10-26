
/**
 * This class is a REST controller that handles HTTP requests related to helpdesk requests.
 * It provides endpoints for creating, retrieving, and updating helpdesk requests.
 * The controller uses the HelpdeskService to perform the business logic.
 * The class is mapped to the "/api/helpdesk" URL.
 */
package com.example.Insurance.Company.controller;

import com.example.Insurance.Company.dto.HelpdeskDTO;
import com.example.Insurance.Company.model.Helpdesk;
import com.example.Insurance.Company.service.CustomUserDetails;
import com.example.Insurance.Company.service.HelpdeskService;
import com.example.Insurance.Company.dto.StatusUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/helpdesk")
public class HelpdeskController {

    @Autowired
    private HelpdeskService helpdeskService;

    @PostMapping
    public ResponseEntity<?> createHelpdeskRequest(@RequestBody HelpdeskDTO helpdeskDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            helpdeskDTO.setUserId(userDetails.getId().longValue());

            Helpdesk helpdesk = helpdeskService.saveHelpdeskRequest(helpdeskDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(helpdesk);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getHelpdeskRequestsByUserId(@PathVariable Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long authenticatedUserId = userDetails.getId().longValue();

        if (!authenticatedUserId.equals(userId) && !authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_SUPERADMIN") || a.getAuthority().equals("ROLE_HELPDESK_OFFICER"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to access this resource.");
        }

        List<HelpdeskDTO> helpdeskRequests = helpdeskService.getHelpdeskRequestsByUserId(userId);
        return ResponseEntity.ok(helpdeskRequests);
    }

    @GetMapping
    public ResponseEntity<List<HelpdeskDTO>> getAllHelpdeskRequests() {
        List<HelpdeskDTO> helpdeskRequests = helpdeskService.getAllHelpdeskRequests();
        return ResponseEntity.ok(helpdeskRequests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHelpdeskRequestById(@PathVariable Long id) {
        try {
            HelpdeskDTO helpdeskRequest = helpdeskService.getHelpdeskRequestById(id);
            return ResponseEntity.ok(helpdeskRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateHelpdeskStatus(@PathVariable Long id, @RequestBody StatusUpdateDTO statusUpdateDTO) {
        try {
            Helpdesk helpdesk = helpdeskService.updateHelpdeskStatus(id, statusUpdateDTO.getStatus());
            return ResponseEntity.ok(helpdesk);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
