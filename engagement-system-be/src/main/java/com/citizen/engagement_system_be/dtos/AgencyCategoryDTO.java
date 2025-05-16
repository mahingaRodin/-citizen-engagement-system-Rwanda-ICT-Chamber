package com.citizen.engagement_system_be.dtos;


import lombok.Data;

@Data
public class AgencyCategoryDTO {
    private Long id;
    private Long agencyId;
    private Long categoryId;
    private boolean isPrimary;
}
