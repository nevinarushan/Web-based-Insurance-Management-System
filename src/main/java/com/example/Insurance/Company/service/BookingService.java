
/**
 * This class provides the business logic for managing bookings.
 * It includes methods for creating, retrieving, and updating bookings, as well as a method for getting the availability of the next agent.
 * The service uses a round-robin approach to assign agents to bookings.
 */
package com.example.Insurance.Company.service;

import com.example.Insurance.Company.dto.AvailabilityResponse;
import com.example.Insurance.Company.dto.BookingDetailsDTO;
import com.example.Insurance.Company.dto.BookingResponseDTO;
import com.example.Insurance.Company.exception.NoAgentsAvailableException;
import com.example.Insurance.Company.model.Admins;
import com.example.Insurance.Company.model.Booking;
import com.example.Insurance.Company.model.User;
import com.example.Insurance.Company.model.UserProfile;
import com.example.Insurance.Company.repository.AdminsRepository;
import com.example.Insurance.Company.repository.BookingRepository;
import com.example.Insurance.Company.repository.UserProfileRepository;
import com.example.Insurance.Company.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private AdminsRepository adminsRepository;

    @Autowired
    private UserRepository userRepository;

    private static AtomicInteger nextAgentIndex = new AtomicInteger(0);

    public Booking createBooking(Long userProfileId, Long policyId, LocalDate desiredDate, String desiredTime, Integer adminId) throws Exception {
        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(userProfileId);
        if (userProfileOptional.isEmpty()) {
            throw new Exception("User profile not found");
        }

        Optional<Admins> adminOptional = adminsRepository.findById(adminId);
        if (adminOptional.isEmpty()) {
            throw new Exception("Admin not found");
        }

        Booking booking = new Booking();
        booking.setUserProfile(userProfileOptional.get());
        booking.setPolicyId(policyId);
        booking.setDesiredDate(desiredDate);
        booking.setDesiredTime(desiredTime);
        booking.setAdmin(adminOptional.get());
        booking.setStatus("Pending");

        return bookingRepository.save(booking);
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public List<Booking> getBookingsByUserProfileId(Long userProfileId) {
        return bookingRepository.findByUserProfileId(userProfileId);
    }

    public List<Booking> getBookingsByAdminId(Integer adminId) {
        return bookingRepository.findByAdminIdAdmin(adminId);
    }

    public Booking updateBookingStatus(Long bookingId, String status) throws Exception {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isEmpty()) {
            throw new Exception("Booking not found");
        }
        Booking booking = bookingOptional.get();
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    public AvailabilityResponse getAvailabilityForNextAgent(LocalDate date) throws NoAgentsAvailableException {
        List<Admins> allAdmins = adminsRepository.findAll();
        List<Admins> agents = allAdmins.stream()
                .filter(a -> a.getSubRole() != null && a.getSubRole().trim().equalsIgnoreCase("Insurance Agent"))
                .collect(Collectors.toList());

        if (agents.isEmpty()) {
            throw new NoAgentsAvailableException("No agents available for booking");
        }

        int index = nextAgentIndex.getAndIncrement() % agents.size();
        Admins assignedAgent = agents.get(index);

        List<Booking> bookings = bookingRepository.findBookingsByAdminAndDate(assignedAgent.getIdAdmin(), date);
        List<String> bookedSlots = bookings.stream().map(Booking::getDesiredTime).collect(Collectors.toList());

        return new AvailabilityResponse(assignedAgent.getIdAdmin(), bookedSlots);
    }

    public List<BookingResponseDTO> getAllBookingsDetails() {
        return bookingRepository.findAll().stream().map(this::convertToBookingResponseDTO).collect(Collectors.toList());
    }

    public List<BookingResponseDTO> getAgentBookingsDetails(Integer adminId) {
        return bookingRepository.findByAdminIdAdmin(adminId).stream().map(this::convertToBookingResponseDTO).collect(Collectors.toList());
    }

    public BookingDetailsDTO getBookingDetailsById(Long id) {
        return bookingRepository.findById(id).map(this::convertToBookingDetailsDTO).orElse(null);
    }

    private BookingResponseDTO convertToBookingResponseDTO(Booking booking) {
        User user = booking.getUserProfile().getUser();
        Admins admin = booking.getAdmin();
        return new BookingResponseDTO(
                booking.getId(),
                user.getName(),
                booking.getPolicyId() != null ? booking.getPolicyId().toString() : "N/A",
                admin.getUsername(),
                booking.getStatus(),
                booking.getDesiredDate(),
                booking.getDesiredTime()
        );
    }

    private BookingDetailsDTO convertToBookingDetailsDTO(Booking booking) {
        UserProfile userProfile = booking.getUserProfile();
        Admins admin = booking.getAdmin();
        return new BookingDetailsDTO(
                booking.getId(),
                booking.getDesiredTime(),
                booking.getDesiredDate(),
                booking.getStatus(),
                userProfile.getFullName(),
                userProfile.getAge(),
                userProfile.getAddress(),
                // Convert java.util.Date to java.time.LocalDate
                new java.sql.Date(userProfile.getBirthDate().getTime()).toLocalDate(),
                userProfile.getOccupation(),
                admin.getUsername(),
                admin.getEmail()
        );
    }
}

