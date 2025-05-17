package com.citizen.engagement_system_be.dtos;

public class ResponseCreateDTO {
    private Long complaintId;
    private String message;

    public Long getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(Long complaintId) {
        this.complaintId = complaintId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
