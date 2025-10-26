
/**
 * This class is a Data Transfer Object (DTO) for transferring detailed information about a booking.
 * It includes information about the booking itself (ID, timeslot, date, status),
 * the user who made the booking (full name, age, address, etc.),
 * and the agent assigned to the booking (username, email).
 */
package com.example.Insurance.Company.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetailsDTO {

    private Long bookingId;
    private String timeslot;
    private LocalDate date;
    private String status;
    private String userFullName;
    private int userAge;
    private String userAddress;
    private LocalDate userBirthDate;
    private String userOccupation;
    private String agentUsername;
    private String agentEmail;
}
