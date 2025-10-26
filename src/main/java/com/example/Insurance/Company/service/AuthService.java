
/**
 * This class provides the business logic for user authentication.
 * It includes a method that checks the provided email and password against both the Admins and User tables in the database.
 */
package com.example.Insurance.Company.service;

import com.example.Insurance.Company.model.Admins;
import com.example.Insurance.Company.model.User;
import com.example.Insurance.Company.repository.AdminsRepository;
import com.example.Insurance.Company.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AdminsRepository adminsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Object authenticate(String email, String rawPassword) throws Exception {
        Optional<Admins> adminOptional = adminsRepository.findByEmail(email);
        if (adminOptional.isPresent()) {
            Admins admin = adminOptional.get();
            if (passwordEncoder.matches(rawPassword, admin.getPassword())) {
                return admin;
            }
        }

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                return user;
            }
        }

        throw new Exception("Invalid email or password");
    }
}
