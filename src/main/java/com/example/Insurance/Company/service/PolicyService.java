
/**
 * This class provides the business logic for managing insurance policies.
 * It includes methods for creating, retrieving, updating, and deleting policies, as well as saving and deleting policy images.
 */
package com.example.Insurance.Company.service;

import com.example.Insurance.Company.model.Policy;
import com.example.Insurance.Company.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PolicyService {

    @Autowired
    private PolicyRepository policyRepository;

    private final String UPLOAD_DIR = "data/policy_images/";

    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }

    public Policy getPolicyById(String policynum) {
        return policyRepository.findById(policynum).orElse(null);
    }

    public Policy createPolicy(String policynum, String title, String description, MultipartFile image) throws IOException {
        String imagePath = saveImage(image);
        Policy policy = new Policy();
        policy.setPolicynum(policynum);
        policy.setTitle(title);
        policy.setDescription(description);
        policy.setImagePath(imagePath);
        return policyRepository.save(policy);
    }

    public Policy updatePolicy(String policynum, String title, String description, MultipartFile newImage) throws IOException {
        Policy existingPolicy = policyRepository.findById(policynum).orElse(null);
        if (existingPolicy == null) {
            return null; // Policy not found
        }

        existingPolicy.setTitle(title);
        existingPolicy.setDescription(description);

        if (newImage != null && !newImage.isEmpty()) {
            // Delete old image if it exists
            if (existingPolicy.getImagePath() != null) {
                Path oldImagePath = Paths.get(UPLOAD_DIR + existingPolicy.getImagePath());
                Files.deleteIfExists(oldImagePath);
            }
            // Save new image
            String newImagePath = saveImage(newImage);
            existingPolicy.setImagePath(newImagePath);
        }

        return policyRepository.save(existingPolicy);
    }

    public void deletePolicy(String policynum) throws IOException {
        Policy policy = policyRepository.findById(policynum).orElse(null);
        if (policy != null) {
            // Delete associated image file
            if (policy.getImagePath() != null) {
                Path imagePath = Paths.get(UPLOAD_DIR + policy.getImagePath());
                Files.deleteIfExists(imagePath);
            }
            policyRepository.delete(policy);
        }
    }

    private String saveImage(MultipartFile image) throws IOException {
        if (image.isEmpty()) {
            return null;
        }
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename().replaceAll(" ", "_");
        Path path = Paths.get(UPLOAD_DIR + fileName);
        Files.write(path, image.getBytes());
        return fileName;
    }
}
