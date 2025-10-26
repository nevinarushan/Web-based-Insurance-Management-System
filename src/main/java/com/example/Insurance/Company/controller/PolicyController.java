
/**
 * This class is a REST controller that handles HTTP requests related to insurance policies.
 * It provides endpoints for creating, retrieving, updating, and deleting policies, as well as retrieving policy images.
 * The controller uses the PolicyService to perform the business logic.
 * The class is mapped to the "/api/policies" URL.
 */
package com.example.Insurance.Company.controller;

import com.example.Insurance.Company.model.Policy;
import com.example.Insurance.Company.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    private final String UPLOAD_DIR = "data/policy_images/";

    @GetMapping
    public List<Policy> getAllPolicies() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("GET /api/policies - Authentication: " + authentication);
        System.out.println("GET /api/policies - Principal: " + authentication.getPrincipal());
        System.out.println("GET /api/policies - Authorities: " + authentication.getAuthorities());
        return policyService.getAllPolicies();
    }

    @GetMapping("/{policynum}")
    public Policy getPolicyById(@PathVariable String policynum) {
        return policyService.getPolicyById(policynum);
    }

    @PostMapping
    public Policy createPolicy(@RequestParam("policynum") String policynum,
                               @RequestParam("title") String title,
                               @RequestParam("description") String description,
                               @RequestParam("image") MultipartFile image) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("POST /api/policies - Authentication: " + authentication);
        System.out.println("POST /api/policies - Principal: " + authentication.getPrincipal());
        System.out.println("POST /api/policies - Authorities: " + authentication.getAuthorities());
        return policyService.createPolicy(policynum, title, description, image);
    }

    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws MalformedURLException {
        Path path = Paths.get(UPLOAD_DIR + filename);
        Resource resource = new UrlResource(path.toUri());
        return ResponseEntity.ok()
                .body(resource);
    }

    @GetMapping("/public")
    public List<Policy> getAllPublicPolicies() {
        return policyService.getAllPolicies(); // Reusing existing service method
    }

    @GetMapping("/public/{policynum}")
    public Policy getPublicPolicyById(@PathVariable String policynum) {
        return policyService.getPolicyById(policynum); // Reusing existing service method
    }

    @PutMapping("/{policynum}")
    public ResponseEntity<Policy> updatePolicy(@PathVariable String policynum,
                                               @RequestParam("title") String title,
                                               @RequestParam("description") String description,
                                               @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        Policy updatedPolicy = policyService.updatePolicy(policynum, title, description, image);
        if (updatedPolicy != null) {
            return ResponseEntity.ok(updatedPolicy);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{policynum}")
    public ResponseEntity<Void> deletePolicy(@PathVariable String policynum) throws IOException {
        policyService.deletePolicy(policynum);
        return ResponseEntity.noContent().build();
    }
}
