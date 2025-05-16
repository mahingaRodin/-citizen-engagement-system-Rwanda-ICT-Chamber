package com.citizen.engagement_system_be.dtos.search;

import lombok.Data;

import java.util.List;

@Data
public class SearchResultDTO<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;
}
