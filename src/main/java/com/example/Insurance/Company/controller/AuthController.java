
/**
 * This class is a REST controller that handles HTTP requests related to user authentication.
 * It provides endpoints for registering a new user and for logging in a user.
 * The controller uses the UserService, AuthenticationManager, CustomUserDetailsService, and JwtUtil to perform the business logic.
 * The class is mapped to the "/api/auth" URL.
 */
package com.example.Insurance.Company.controller;

import com.example.Insurance.Company.model.Admins;
import com.example.Insurance.Company.model.User;
import com.example.Insurance.Company.repository.AdminsRepository;
import com.example.Insurance.Company.repository.UserRepository;
import com.example.Insurance.Company.service.CustomUserDetailsService;
import com.example.Insurance.Company.service.UserService;
import com.example.Insurance.Company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "false") // Credentials not needed for stateless JWT
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AdminsRepository adminsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        try {
            User user = new User();
            user.setName(registerRequest.getName());
            user.setEmail(registerRequest.getEmail());
            user.setPassword(registerRequest.getPassword()); // Password will be encoded in UserService

            // Set role based on request, with default to USER
            String role = registerRequest.getRole();
            if (role == null || role.isEmpty()) {
                user.setRole("USER");
            } else if (role.equals("ADMIN") && "ADMIN123".equals(registerRequest.getAdminSecretKey())) {
                user.setRole("ADMIN");
            } else if (role.equals("AGENT")) {
                user.setRole("AGENT");
            } else {
                user.setRole("USER"); // Default to USER if role is not recognized or admin secret is wrong
            }

            userService.registerNewUser(user);
            return ResponseEntity.ok("User registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        System.out.println("Login attempt for email: " + loginRequest.getEmail() + ", password: " + loginRequest.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            // SecurityContextHolder.getContext().setAuthentication(authentication); // Not needed for stateless JWT

            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
            final String jwt = jwtUtil.generateToken(userDetails);

            Map<String, Object> response = new HashMap<>();
            // Retrieve the actual User or Admins object from the repository
            Optional<Admins> adminOptional = adminsRepository.findByEmail(loginRequest.getEmail());
            if (adminOptional.isPresent()) {
                Admins admin = adminOptional.get();
                response.put("id", admin.getIdAdmin());
                response.put("name", admin.getUsername());
                response.put("email", admin.getEmail());
                response.put("role", admin.getRole());
                response.put("sub_role", admin.getSubRole());
            } else {
                User userModel = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new Exception("User not found after authentication"));
                response.put("id", userModel.getId());
                response.put("name", userModel.getName());
                response.put("email", userModel.getEmail());
                String role = userModel.getRole();
                if (role == null || role.isEmpty()) {
                    role = "USER";
                }
                response.put("role", role);
            }
            response.put("token", jwt);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}