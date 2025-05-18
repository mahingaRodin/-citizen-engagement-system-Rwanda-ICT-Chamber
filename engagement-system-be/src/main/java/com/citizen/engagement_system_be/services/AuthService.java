package com.citizen.engagement_system_be.services;

import com.citizen.engagement_system_be.dtos.SignupRequestDTO;
import com.citizen.engagement_system_be.dtos.UserDTO;
import com.citizen.engagement_system_be.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface AuthService {
    UserDetailsService userDetailsService();
    UserDTO createUser(SignupRequestDTO signupRequest);
    Optional<User> findById(long id);
    User updateUser(Long id,User updatedUser);
}
