
/**
 * This class is a request body class used for creating a new booking.
 * It contains fields for the user profile ID, policy ID, desired date, desired time, and admin ID.
 */
package com.example.Insurance.Company.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequest {
    private Long userProfileId;
    private Long policyId;
    private LocalDate desiredDate;
    private String desiredTime;
    private Integer adminId;
}
