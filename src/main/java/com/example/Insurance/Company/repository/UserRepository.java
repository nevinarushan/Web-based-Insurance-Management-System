
/**
 * This interface is a Spring Data JPA repository for User entities.
 * It provides methods for performing CRUD operations on users,
 * as well as a custom method for finding a user by email.
 */
package com.example.Insurance.Company.repository;

import com.example.Insurance.Company.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}