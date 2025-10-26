
/**
 * This class is a Data Transfer Object (DTO) for updating the status of a helpdesk request.
 * It is used to transfer the new status of the request from the client to the server.
 * The class has a single field, "status", which represents the new status of the request.
 */
package com.example.Insurance.Company.dto;

public class StatusUpdateDTO {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
