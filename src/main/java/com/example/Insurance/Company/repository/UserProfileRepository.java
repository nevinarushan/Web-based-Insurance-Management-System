
/**
 * This interface is a Spring Data JPA repository for UserProfile entities.
 * It provides methods for performing CRUD operations on user profiles,
 * as well as a custom method for finding a user profile by user ID.
 */
package com.example.Insurance.Company.repository;

import com.example.Insurance.Company.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUserId(Integer userId);
}
