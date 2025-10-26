
/**
 * This class is a REST controller that handles HTTP requests related to bookings.
 * It provides endpoints for creating, retrieving, and updating bookings, as well as checking agent availability.
 * The controller uses the BookingService to perform the business logic.
 * The class is mapped to the "/api/bookings" URL.
 */
package com.example.Insurance.Company.controller;

import com.example.Insurance.Company.dto.AvailabilityResponse;
import com.example.Insurance.Company.dto.BookingDetailsDTO;
import com.example.Insurance.Company.dto.BookingRequest;
import com.example.Insurance.Company.dto.BookingResponseDTO;
import com.example.Insurance.Company.exception.NoAgentsAvailableException;
import com.example.Insurance.Company.model.Booking;
import com.example.Insurance.Company.service.BookingService;
import com.example.Insurance.Company.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingRequest bookingRequest) {
        try {
            Booking newBooking = bookingService.createBooking(
                    bookingRequest.getUserProfileId(),
                    bookingRequest.getPolicyId(),
                    bookingRequest.getDesiredDate(),
                    bookingRequest.getDesiredTime(),
                    bookingRequest.getAdminId()
            );
            return new ResponseEntity<>(newBooking, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUserId(@PathVariable Long userId) {
        // Assuming userProfileId is the same as userId for simplicity here
        // In a real app, you might need to fetch userProfileId from userId
        return ResponseEntity.ok(bookingService.getBookingsByUserProfileId(userId));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Booking> updateBookingStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String status = body.get("status");
        try {
            Booking updatedBooking = bookingService.updateBookingStatus(id, status);
            return ResponseEntity.ok(updatedBooking);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<Booking>> getBookingsByAdminId(@PathVariable Integer adminId) {
        return ResponseEntity.ok(bookingService.getBookingsByAdminId(adminId));
    }

    @GetMapping("/availability")
    public ResponseEntity<?> getAvailability(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            AvailabilityResponse availability = bookingService.getAvailabilityForNextAgent(date);
            return ResponseEntity.ok(availability);
        } catch (NoAgentsAvailableException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @GetMapping("/details")
    public ResponseEntity<List<BookingResponseDTO>> getAllBookingsDetails() {
        return ResponseEntity.ok(bookingService.getAllBookingsDetails());
    }

    @GetMapping("/agent/details")
    public ResponseEntity<List<BookingResponseDTO>> getAgentBookingsDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(bookingService.getAgentBookingsDetails(userDetails.getId()));
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<BookingDetailsDTO> getBookingDetailsById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingDetailsById(id));
    }
}
