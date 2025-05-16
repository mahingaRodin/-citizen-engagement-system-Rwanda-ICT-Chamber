package com.citizen.engagement_system_be.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComplaintTrackingDTO {
    private Long id;
    private Long complaintId;
    private String previousStatus;
    private String newStatus;
    private Long updatedById;
    private String comment;
    private LocalDateTime changedAt;
}
