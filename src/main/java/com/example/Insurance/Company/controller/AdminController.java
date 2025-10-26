
/**
 * This class is a REST controller that handles HTTP requests related to admin users.
 * It provides an endpoint for creating a new admin user.
 * The controller uses the AdminService to perform the business logic.
 * The class is mapped to the "/api/admin" URL.
 */
package com.example.Insurance.Company.controller;

import com.example.Insurance.Company.model.Admins;
import com.example.Insurance.Company.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")

public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/create")
    public ResponseEntity<?> createAdmin(@RequestBody CreateAdminRequest createAdminRequest) {
        try {
            Admins admin = new Admins();
            admin.setUsername(createAdminRequest.getUsername());
            admin.setEmail(createAdminRequest.getEmail());
            admin.setPassword(createAdminRequest.getPassword());
            admin.setRole(createAdminRequest.getRole());
            admin.setSubRole(createAdminRequest.getSubrole());

            adminService.createAdmin(admin);
            return ResponseEntity.ok("Admin created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
