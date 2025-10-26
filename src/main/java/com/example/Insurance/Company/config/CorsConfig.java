
/**
 * This class provides the CORS configuration for the application.
 * It allows the frontend application to make requests to the backend API.
 * The configuration allows requests from http://localhost:3000 and permits the GET, POST, PUT, DELETE, and OPTIONS methods.
 * It also allows all headers and supports credentials.
 */
package com.example.Insurance.Company.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000") // Allow your frontend origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow necessary methods
                        .allowedHeaders("*") // Allow all headers
                        .allowCredentials(true); // Allow credentials (cookies, HTTP authentication)
            }
        };
    }
}
