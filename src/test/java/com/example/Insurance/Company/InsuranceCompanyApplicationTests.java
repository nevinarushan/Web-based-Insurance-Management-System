
/**
 * This class contains a test class for the Spring Boot application.
 * The @SpringBootTest annotation indicates that this is a test class that should run with a Spring Boot application context.
 * The contextLoads() method is a simple test that checks if the application context can be loaded without any errors.
 */
package com.example.Insurance.Company;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InsuranceCompanyApplicationTests {

	@Test
	void contextLoads() {
	}

}
