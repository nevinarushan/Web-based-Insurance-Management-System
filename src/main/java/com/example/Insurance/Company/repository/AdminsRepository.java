
/**
 * This interface is a Spring Data JPA repository for Admins entities.
 * It provides methods for performing CRUD operations on admin users,
 * as well as custom methods for finding admin users by email and by role.
 */
package com.example.Insurance.Company.repository;

import com.example.Insurance.Company.model.Admins;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminsRepository extends JpaRepository<Admins, Integer> {
    Optional<Admins> findByEmail(String email);
    List<Admins> findByRole(String role);
}
