
/**
 * This class is a Data Transfer Object (DTO) for helpdesk requests.
 * It is used to transfer data between the client and the server.
 * The class includes information about the helpdesk request, such as the user ID, phone number, problem description, and status.
 * It also includes user profile information, such as the user's full name, address, age, birth date, and occupation.
 */
package com.example.Insurance.Company.dto;

import java.util.Date;

public class HelpdeskDTO {

    private Long id;
    private Long userId;
    private String phoneNumber;
    private String problem;
    private String fullName;
    private String address;
    private int age;
    private Date birthDate;
    private String occupation;
    private String status;

    public HelpdeskDTO() {
    }

    public HelpdeskDTO(Long id, Long userId, String phoneNumber, String problem, String fullName, String address, int age, Date birthDate, String occupation, String status) {
        this.id = id;
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.problem = problem;
        this.fullName = fullName;
        this.address = address;
        this.age = age;
        this.birthDate = birthDate;
        this.occupation = occupation;
        this.status = status;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}