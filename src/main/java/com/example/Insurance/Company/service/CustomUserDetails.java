
/**
 * This class is a custom implementation of Spring Security's UserDetails interface.
 * It extends the User class and adds custom fields for role, subRole, and id.
 * This allows for more fine-grained access control and user identification within the application.
 */
package com.example.Insurance.Company.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {
    private final String role;
    private final String subRole;
    private final Integer id;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, String role, String subRole, Integer id) {
        super(username, password, authorities);
        this.role = role;
        this.subRole = subRole;
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public String getSubRole() {
        return subRole;
    }

    public Integer getId() {
        return id;
    }
}