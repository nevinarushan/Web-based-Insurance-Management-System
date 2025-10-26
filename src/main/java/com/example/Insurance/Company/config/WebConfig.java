
/**
 * This class provides the web configuration for the application.
 * It includes a resource handler that maps the URL /api/policies/images/** to the data/policy_images/ directory.
 * This allows the application to serve images from that directory.
 */
package com.example.Insurance.Company.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/api/policies/images/**")
                .addResourceLocations("file:data/policy_images/");
    }
}
