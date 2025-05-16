package com.citizen.engagement_system_be.dtos.search;

import com.citizen.engagement_system_be.enums.UserRole;
import lombok.Data;

@Data
public class UserSearchDTO {
    private String name;
    private String email;
    private UserRole role;
    private String phoneNumber;
    private Integer page = 0;
    private Integer size = 10;
    private String sortBy = "name";
    private String sortDirection = "ASC";
}
