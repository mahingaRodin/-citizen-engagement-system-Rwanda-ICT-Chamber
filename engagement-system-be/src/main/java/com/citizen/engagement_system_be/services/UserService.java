package com.citizen.engagement_system_be.services;

import com.citizen.engagement_system_be.enums.NotificationType;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();
}
