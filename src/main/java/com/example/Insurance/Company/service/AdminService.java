
/**
 * This class provides the business logic for managing admin users.
 * It includes a method for creating a new admin user, which includes validation for the admin's role and sub-role,
 * and encoding the admin's password before saving it to the database.
 */
package com.example.Insurance.Company.service;

import com.example.Insurance.Company.model.Admins;
import com.example.Insurance.Company.repository.AdminsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminsRepository adminsRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private static final List<String> ALLOWED_SUBROLES = Arrays.asList(
            "IT Department", "Claims Officer", "Insurance Agent", "Helpdesk Officer"
    );

    public Admins createAdmin(Admins admin) throws Exception {
        if (adminsRepository.findByEmail(admin.getEmail()).isPresent()) {
            throw new Exception("Email already registered");
        }

        // Handle role and subrole logic
        if ("SUPERADMIN".equals(admin.getRole())) {
            throw new Exception("Cannot create SUPERADMIN from this endpoint.");
        } else if ("ADMIN".equals(admin.getRole())) {
            // For ADMIN, subrole is required and must be from allowed list
            if (admin.getSubRole() == null || admin.getSubRole().isEmpty()) {
                throw new Exception("Sub-role is required for ADMIN role");
            }
            if (!ALLOWED_SUBROLES.contains(admin.getSubRole())) {
                throw new Exception("Invalid sub-role provided");
            }
        } else {
            throw new Exception("Invalid role provided");
        }

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminsRepository.save(admin);
    }
}
