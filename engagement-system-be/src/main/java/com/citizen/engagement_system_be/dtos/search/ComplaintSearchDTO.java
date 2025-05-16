package com.citizen.engagement_system_be.dtos.search;

import com.citizen.engagement_system_be.enums.ComplaintPriority;
import com.citizen.engagement_system_be.enums.ComplaintStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComplaintSearchDTO {
    private String title;
    private String description;
    private ComplaintStatus status;
    private ComplaintPriority priority;
    private Long agencyId;
    private Long categoryId;
    private String location;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer page = 0;
    private Integer size = 10;
    private String sortBy = "createdAt";
    private String sortDirection = "DESC";
}