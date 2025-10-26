
/**
 * This class is a Data Transfer Object (DTO) for transferring information about a booking in a response.
 * It includes the booking ID, username, policy number, agent name, status, date, and timeslot.
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
public class BookingResponseDTO {

    private Long bookingId;
    private String username;
    private String policyNumber;
    private String agentName;
    private String status;
    private LocalDate date;
    private String timeslot;
}
