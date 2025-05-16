package com.citizen.engagement_system_be.dtos.search;

import lombok.Data;

@Data
public class AgencySearchDTO {
    private String name;
    private String description;
    private String contactEmail;
    private String contactPhone;
    private String categoryName;
    private Integer page = 0;
    private Integer size = 10;
    private String sortBy = "name";
    private String sortDirection = "ASC";
}
