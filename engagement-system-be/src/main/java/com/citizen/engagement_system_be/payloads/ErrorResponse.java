package com.citizen.engagement_system_be.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResponse {
    private final boolean success = false;
    private String message;
    private Object info;

    public ErrorResponse(String message, Object info) {
        this.message = message;
        this.info = info;
    }
}

