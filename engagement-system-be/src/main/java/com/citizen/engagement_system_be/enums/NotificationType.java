package com.citizen.engagement_system_be.enums;

import lombok.Getter;

@Getter
public enum NotificationType {
    COMPLAINT_CREATED("Complaint Created"),
    COMPLAINT_UPDATED("Complaint Updated"),
    COMPLAINT_RESOLVED("Complaint Resolved"),
    NEW_RESPONSE("New Response"),
    STATUS_CHANGED("Status Changed"),
    PRIORITY_CHANGED("Priority Changed"),
    ASSIGNMENT_CHANGED("Assignment Changed"),
    COMPLAINT_RATED("Complaint Rated"),
    COMPLAINT_ASSIGNED("Complaint Assigned"),
    COMPLAINT_REASSIGNED("Complaint Reassigned"),
    COMPLAINT_ESCALATED("Complaint Escalated"),
    COMPLAINT_DEESCALATED("Complaint Descaled");

    private final String displayName;

    NotificationType(String displayName) {
        this.displayName = displayName;
    }

}