package com.citizen.engagement_system_be.config;

import com.citizen.engagement_system_be.enums.UserRole;
import com.citizen.engagement_system_be.models.User;
import com.citizen.engagement_system_be.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Create system admin if not exists
        if (!userRepository.existsByEmail("admin@system.com")) {
            User admin = new User();
            admin.setName("System Admin");
            admin.setEmail("admin@system.com");
            admin.setPassword(passwordEncoder.encode("Admin@123"));
            admin.setRole(UserRole.SYSTEM_ADMIN);
            admin.setPhoneNumber("+250788888888");
            admin.setCreatedAt(LocalDateTime.now());
            admin.setUpdatedAt(LocalDateTime.now());
            userRepository.save(admin);
        }

        // Create agency admin if not exists
        if (!userRepository.existsByEmail("agency@system.com")) {
            User agencyAdmin = new User();
            agencyAdmin.setName("Agency Admin");
            agencyAdmin.setEmail("agency@system.com");
            agencyAdmin.setPassword(passwordEncoder.encode("Agency@123"));
            agencyAdmin.setRole(UserRole.AGENCY_ADMIN);
            agencyAdmin.setPhoneNumber("+250788888889");
            agencyAdmin.setCreatedAt(LocalDateTime.now());
            agencyAdmin.setUpdatedAt(LocalDateTime.now());
            userRepository.save(agencyAdmin);
        }

        // Create citizen user if not exists
        if (!userRepository.existsByEmail("citizen@system.com")) {
            User citizen = new User();
            citizen.setName("Test Citizen");
            citizen.setEmail("citizen@system.com");
            citizen.setPassword(passwordEncoder.encode("Citizen@123"));
            citizen.setRole(UserRole.CITIZEN);
            citizen.setPhoneNumber("+250788888890");
            citizen.setCreatedAt(LocalDateTime.now());
            citizen.setUpdatedAt(LocalDateTime.now());
            userRepository.save(citizen);
        }
    }
} 