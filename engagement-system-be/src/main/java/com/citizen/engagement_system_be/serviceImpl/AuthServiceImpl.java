package com.citizen.engagement_system_be.serviceImpl;

import com.citizen.engagement_system_be.dtos.SignupRequestDTO;
import com.citizen.engagement_system_be.dtos.UserDTO;
import com.citizen.engagement_system_be.enums.UserRole;
import com.citizen.engagement_system_be.exceptions.ResourceNotFoundException;
import com.citizen.engagement_system_be.models.User;
import com.citizen.engagement_system_be.repository.UserRepository;
import com.citizen.engagement_system_be.services.AuthService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private  final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetailsService userDetailsService() {
        return username -> (org.springframework.security.core.userdetails.UserDetails) userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @PostConstruct
    public void createAnAdminAccount() {
        List<User> adminAccount = userRepository.findByRole(UserRole.SYSTEM_ADMIN);
        if(adminAccount.isEmpty()) {

            User user = new User();
            user.setEmail("mahingarodin@gmail.com");
            user.setName("Uwonkunda Rodin");
            user.setRole(UserRole.SYSTEM_ADMIN);
            user.setPhoneNumber("0783582108");
            user.setPassword(new BCryptPasswordEncoder().encode("Admin!132"));
            userRepository.save(user);
            System.out.println("Admin account created successfully");

        } else {
            System.out.println("Admin account already exist");
        }
    }

    @Override
    public UserDTO createUser(SignupRequestDTO signupRequest) {
        if(userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new EntityExistsException("User already exists");
        }

        User u = new User();
        u.setName(signupRequest.getName());
        u.setEmail(signupRequest.getEmail());
        u.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        u.setPhoneNumber(signupRequest.getPhoneNumber());
        u.setRole(UserRole.CITIZEN);
        u.setCreatedAt(LocalDateTime.now());
        User createdUser =userRepository.save(u);
        return createdUser.getUserDTO();
    }

    @Override
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        // Update only the allowed fields
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setRole(updatedUser.getRole());

        // Save updated user
        return userRepository.save(existingUser);

    }
}