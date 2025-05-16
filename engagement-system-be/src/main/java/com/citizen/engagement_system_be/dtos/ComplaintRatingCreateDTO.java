package com.citizen.engagement_system_be.dtos;

import lombok.Data;

@Data
public class ComplaintRatingCreateDTO {
    private Long complaintId;
    private Integer rating;
    private String feedback;
}
