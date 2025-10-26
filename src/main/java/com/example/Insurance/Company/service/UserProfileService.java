
/**
 * This class provides the business logic for managing user profiles.
 * It includes methods for retrieving, creating, and updating user profiles.
 */
package com.example.Insurance.Company.service;

import com.example.Insurance.Company.model.User;
import com.example.Insurance.Company.model.UserProfile;
import com.example.Insurance.Company.repository.UserProfileRepository;
import com.example.Insurance.Company.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<UserProfile> getUserProfileByUserId(Integer userId) {
        return userProfileRepository.findByUserId(userId);
    }

    public UserProfile saveUserProfile(UserProfile userProfile, Integer userId) throws Exception {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new Exception("User not found");
        }

        if (userProfileRepository.findByUserId(userId).isPresent()) {
            throw new Exception("User profile already exists");
        }

        userProfile.setUser(userOptional.get());
        return userProfileRepository.save(userProfile);
    }

    public UserProfile updateUserProfile(UserProfile userProfile, Integer userId) throws Exception {
        Optional<UserProfile> existingProfileOptional = userProfileRepository.findByUserId(userId);
        if (existingProfileOptional.isEmpty()) {
            throw new Exception("User profile not found");
        }

        UserProfile existingProfile = existingProfileOptional.get();
        existingProfile.setFullName(userProfile.getFullName());
        existingProfile.setAge(userProfile.getAge());
        existingProfile.setAddress(userProfile.getAddress());
        existingProfile.setBirthDate(userProfile.getBirthDate());
        existingProfile.setOccupation(userProfile.getOccupation());

        return userProfileRepository.save(existingProfile);
    }
}
