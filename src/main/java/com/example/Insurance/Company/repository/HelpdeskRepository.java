
/**
 * This interface is a Spring Data JPA repository for Helpdesk entities.
 * It provides methods for performing CRUD operations on helpdesk requests,
 * as well as a custom method for finding helpdesk requests by user ID.
 * The interface extends the JpaRepository interface, which provides a number of generic methods for working with entities.
 */
package com.example.Insurance.Company.repository;

import com.example.Insurance.Company.model.Helpdesk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HelpdeskRepository extends JpaRepository<Helpdesk, Long> {
    List<Helpdesk> findByUserProfile_User_Id(Long userId);
}
