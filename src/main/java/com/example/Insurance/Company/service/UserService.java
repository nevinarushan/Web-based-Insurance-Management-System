/**
 * This class provides the business logic for managing users.
 * It includes methods for finding a user by email, registering a new user, and authenticating a user.
 */
package com.example.Insurance.Company.service;

import com.example.Insurance.Company.model.User;
import com.example.Insurance.Company.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User registerNewUser(User user) throws Exception {
        if (findByEmail(user.getEmail()).isPresent()) {
            throw new Exception("Email already registered");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User authenticateUser(String email, String rawPassword) throws Exception {
        Optional<User> userOptional = findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new Exception("Invalid email or password");
        }

        User user = userOptional.get();
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new Exception("Invalid email or password");
        }
        return user;
    }

    public void changePassword(Integer userId, String oldPassword, String newPassword) throws Exception {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new Exception("User not found");
        }

        User user = userOptional.get();

        // Validate old password
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new Exception("Current password is incorrect");
        }

        // Validate new password is different from old password
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new Exception("New password must be different from current password");
        }

        // Encrypt and save new password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public Optional<User> findById(Integer userId) {
        return userRepository.findById(userId);
    }
}