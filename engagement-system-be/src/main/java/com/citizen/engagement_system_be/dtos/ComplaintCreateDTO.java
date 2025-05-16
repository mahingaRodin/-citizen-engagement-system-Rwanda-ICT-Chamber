package com.citizen.engagement_system_be.dtos;

import lombok.Data;

@Data
public class ComplaintCreateDTO {
    private String title;
    private String description;
    private String location;
    private Long categoryId;
    private Long agencyId;
}
