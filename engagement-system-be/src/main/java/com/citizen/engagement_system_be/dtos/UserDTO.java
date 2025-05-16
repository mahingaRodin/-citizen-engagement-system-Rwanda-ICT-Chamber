package com.citizen.engagement_system_be.dtos;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String role;
}
