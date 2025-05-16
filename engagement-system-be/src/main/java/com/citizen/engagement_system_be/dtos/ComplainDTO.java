package com.citizen.engagement_system_be.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComplainDTO {
    private Long id;
    private String title;
    private String description;
    private String status;
    private String location;
    private Long userId;
    private Long categoryId;
    private Long agencyId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime resolvedAt;
}
