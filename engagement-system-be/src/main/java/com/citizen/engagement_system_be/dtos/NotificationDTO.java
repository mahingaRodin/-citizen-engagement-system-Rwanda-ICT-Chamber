package com.citizen.engagement_system_be.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDTO {
    private Long id;
    private Long userId;
    private String type;
    private String message;
    private Long complaintId;
    private boolean isRead;
    private LocalDateTime createdAt;
}
