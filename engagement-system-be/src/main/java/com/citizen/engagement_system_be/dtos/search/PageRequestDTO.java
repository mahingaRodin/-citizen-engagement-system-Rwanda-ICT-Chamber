package com.citizen.engagement_system_be.dtos.search;

import lombok.Data;

@Data
public class PageRequestDTO {
    private Integer page = 0;
    private Integer size = 10;
    private String sortBy;
    private String sortDirection = "ASC";
}
