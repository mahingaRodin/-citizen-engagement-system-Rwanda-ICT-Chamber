package com.citizen.engagement_system_be.dtos.search;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseSearchDTO {
    private String keyword;
    private Long complaintId;
    private Long userId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer page = 0;
    private Integer size = 10;
    private String sortBy = "createdAt";
    private String sortDirection = "DESC";
}
