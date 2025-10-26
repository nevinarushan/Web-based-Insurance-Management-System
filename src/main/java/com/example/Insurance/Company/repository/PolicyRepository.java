
/**
 * This interface is a Spring Data JPA repository for Policy entities.
 * It provides methods for performing CRUD operations on policies.
 */
package com.example.Insurance.Company.repository;

import com.example.Insurance.Company.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, String> {
}
