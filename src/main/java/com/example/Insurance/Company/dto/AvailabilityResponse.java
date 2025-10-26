
/**
 * This class is a Data Transfer Object (DTO) for representing the availability of an agent.
 * It contains the agent's ID and a list of their booked time slots.
 */
package com.example.Insurance.Company.dto;

import lombok.Data;

import java.util.List;

@Data
public class AvailabilityResponse {
    private Integer adminId;
    private List<String> bookedSlots;

    public AvailabilityResponse(Integer adminId, List<String> bookedSlots) {
        this.adminId = adminId;
        this.bookedSlots = bookedSlots;
    }
}
