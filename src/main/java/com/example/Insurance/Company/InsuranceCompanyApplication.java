
/**
 * This is the main entry point for the Spring Boot application.
 * It contains the main method that starts the application and a @SpringBootApplication annotation that enables auto-configuration, component scanning, and other Spring Boot features.
 * It also defines a bean for BCryptPasswordEncoder.
 */
package com.example.Insurance.Company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class InsuranceCompanyApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsuranceCompanyApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
