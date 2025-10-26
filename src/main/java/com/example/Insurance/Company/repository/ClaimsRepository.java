
/**
 * This interface is a Spring Data JPA repository for Claims entities.
 * It provides methods for performing CRUD operations on claims,
 * as well as a custom method for finding claims by user profile ID.
 */
package com.example.Insurance.Company.repository;

import com.example.Insurance.Company.model.Claims;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimsRepository extends JpaRepository<Claims, Long> {
    List<Claims> findByUserProfileId(Long userProfileId);
}
