package com.citizen.engagement_system_be.serviceImpl;

import com.citizen.engagement_system_be.enums.UserRole;
import com.citizen.engagement_system_be.exceptions.ResourceNotFoundException;
import com.citizen.engagement_system_be.models.User;
import com.citizen.engagement_system_be.repository.UserRepository;
import com.citizen.engagement_system_be.services.UserService;
import jakarta.persistence.EntityExistsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private  final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetailsService userDetailsService() {
        return username -> (org.springframework.security.core.userdetails.UserDetails) userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public User createUser(User user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EntityExistsException("User already exists");
        }

        User u = new User();
        u.setName(user.getName());
        u.setEmail(user.getEmail());
        u.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        u.setPhoneNumber(user.getPhoneNumber());
        u.setRole(user.getRole() != null ? user.getRole() : UserRole.CITIZEN);
        u.setCreatedAt(LocalDateTime.now());
        return null;
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

        // Save updated user
        return userRepository.save(existingUser);

    }
}