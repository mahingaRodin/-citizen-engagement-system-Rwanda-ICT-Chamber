package com.citizen.engagement_system_be.services;

import com.citizen.engagement_system_be.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService {
    UserDetailsService userDetailsService();
    User createUser(User user);
    Optional<User> findById(long id);
    User updateUser(Long id,User updatedUser);
}
