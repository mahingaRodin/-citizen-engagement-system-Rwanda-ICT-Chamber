package com.citizen.engagement_system_be.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComplaintRatingDTO {
    private Long id;
    private Long complaintId;
    private Integer rating;
    private String feedback;
    private LocalDateTime ratedAt;
}
