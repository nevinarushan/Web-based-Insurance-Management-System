
/**
 * This interface is a Spring Data JPA repository for Booking entities.
 * It provides methods for performing CRUD operations on bookings,
 * as well as custom methods for finding bookings by user profile ID, by admin ID, and by admin and date.
 */
package com.example.Insurance.Company.repository;

import com.example.Insurance.Company.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserProfileId(Long userProfileId);
    List<Booking> findByAdminIdAdmin(Integer adminId);

    @Query("SELECT b FROM Booking b WHERE b.admin.idAdmin = :adminId AND b.desiredDate = :date")
    List<Booking> findBookingsByAdminAndDate(@Param("adminId") Integer adminId, @Param("date") LocalDate date);
}
