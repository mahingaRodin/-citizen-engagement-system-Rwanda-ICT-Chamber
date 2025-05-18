package com.citizen.engagement_system_be.mapper;

import com.citizen.engagement_system_be.dtos.UserDTO;
import com.citizen.engagement_system_be.dtos.UserRegistrationDTO;
import com.citizen.engagement_system_be.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRole(user.getRole());

        return dto;
    }

    public User toEntity(UserRegistrationDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setPhoneNumber(dto.getPhoneNumber());

        return user;
    }
}
