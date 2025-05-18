package com.citizen.engagement_system_be.enums;

import lombok.Getter;

@Getter
public enum ComplaintStatus {
    OPEN("Open"),
    IN_PROGRESS("In Progress"),
    UNDER_REVIEW("Under Review"),
    PENDING_INFORMATION("Pending Information"),
    RESOLVED("Resolved"),
    CLOSED("Closed"),
    REJECTED("Rejected"),
    ASSIGNED("Assigned");

    private final String displayName;

    ComplaintStatus(String displayName) {
        this.displayName = displayName;
    }

}
