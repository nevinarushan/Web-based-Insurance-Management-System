
/**
 * This class represents a booking in the application.
 * It is an entity class that is mapped to the "booking" table in the database.
 * The class includes information about the booking, such as the user profile, the admin (agent),
 * the policy ID, the desired date and time, and the status of the booking.
 */
package com.example.Insurance.Company.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_profile_id", nullable = false)
    private UserProfile userProfile;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Admins admin;

    @Column(name = "policy_id", nullable = false)
    private Long policyId;

    @Column(name = "desired_date", nullable = false)
    private LocalDate desiredDate;

    @Column(name = "desired_time", nullable = false)
    private String desiredTime;

    @Column(name = "status", nullable = false)
    private String status = "Pending";
}
