package com.citizen.engagement_system_be.dtos;

import lombok.Data;

@Data
public class ResponseCreateDTO {
    private Long complaintId;
    private String message;
}
