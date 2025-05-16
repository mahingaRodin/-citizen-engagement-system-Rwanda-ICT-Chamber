package com.citizen.engagement_system_be.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseDTO {
    private Long id;
    private Long complaintId;
    private Long userId;
    private String message;
    private LocalDateTime createdAt;
}
