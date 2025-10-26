
/**
 * This class provides the security configuration for the application.
 * It defines the security filter chain, password encoder, authentication manager, and other security-related beans.
 * The security filter chain is configured to permit access to certain URLs without authentication,
 * while requiring authentication for all other URLs.
 * The class also configures the JWT request filter to handle authentication based on JSON Web Tokens.
 */
package com.example.Insurance.Company.config;

import com.example.Insurance.Company.filter.JwtRequestFilter;
import com.example.Insurance.Company.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless JWT
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/api/auth/login", "/api/auth/register").permitAll() // Allow unauthenticated access to register and login
                .requestMatchers(HttpMethod.GET, "/api/policies/images/**").permitAll() // Allow public access to policy images
                .requestMatchers(HttpMethod.POST, "/api/admin/create").hasRole("SUPERADMIN") // Only SUPERADMIN can create admins
                .requestMatchers(HttpMethod.GET, "/api/policies/public/**").permitAll() // Allow public access to policies
                .requestMatchers(HttpMethod.GET, "/api/policies/**").hasRole("SUPERADMIN") // Only SUPERADMIN can view policies
                .requestMatchers(HttpMethod.POST, "/api/policies").hasRole("SUPERADMIN") // Only SUPERADMIN can create policies
                .requestMatchers("/api/user/profile/**").hasRole("USER") // Only users can access their profiles
                .requestMatchers("/api/claims/**").hasRole("USER") // Only users can access claims
                .requestMatchers("/api/admin/claims/**").hasAnyRole("SUPERADMIN", "CLAIMS_OFFICER") // Only superadmins and claims officers can access admin claims
                .requestMatchers("/api/bookings/details").hasRole("SUPERADMIN")
                .requestMatchers("/api/bookings/agent/details").hasRole("CLAIMS_OFFICER")
                .requestMatchers("/api/bookings/details/{id}").hasAnyRole("SUPERADMIN", "CLAIMS_OFFICER")
                .requestMatchers(HttpMethod.POST, "/api/helpdesk").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/helpdesk/user/{userId}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/helpdesk").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/helpdesk/{id}").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/helpdesk/{id}/status").permitAll()
                .requestMatchers("/api/issues/**").authenticated()
                .anyRequest().authenticated() // All other requests require authentication
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Disable sessions
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter
        return http.build();
    }
}
