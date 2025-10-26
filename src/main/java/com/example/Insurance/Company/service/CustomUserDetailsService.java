
/**
 * This class is a custom implementation of Spring Security's UserDetailsService interface.
 * It is responsible for loading user-specific data.
 * It first tries to load a user from the UserRepository.
 * If that fails, it tries to load an admin from the AdminsRepository.
 * It then creates a CustomUserDetails object with the user's or admin's details and authorities.
 */
package com.example.Insurance.Company.service;

import com.example.Insurance.Company.model.Admins;
import com.example.Insurance.Company.model.User;
import com.example.Insurance.Company.repository.AdminsRepository;
import com.example.Insurance.Company.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.example.Insurance.Company.model.Admins;
import com.example.Insurance.Company.model.User;
import com.example.Insurance.Company.repository.AdminsRepository;
import com.example.Insurance.Company.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminsRepository adminsRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new CustomUserDetails(user.getEmail(), user.getPassword(), authorities, "USER", null, user.getId());
        }

        Optional<Admins> adminOptional = adminsRepository.findByEmail(email);
        if (adminOptional.isPresent()) {
            Admins admin = adminOptional.get();
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + admin.getRole()));
            if (admin.getSubRole() != null && !admin.getSubRole().isEmpty()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + admin.getSubRole().toUpperCase().replace(" ", "_")));
            }
            return new CustomUserDetails(admin.getEmail(), admin.getPassword(), authorities, admin.getRole(), admin.getSubRole(), admin.getIdAdmin());
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}
