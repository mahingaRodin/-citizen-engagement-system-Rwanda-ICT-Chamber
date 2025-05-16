package com.citizen.engagement_system_be.dtos;

import lombok.Data;

@Data
public class UserRegistrationDTO {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
}
