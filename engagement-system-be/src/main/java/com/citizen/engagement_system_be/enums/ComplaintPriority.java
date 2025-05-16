package com.citizen.engagement_system_be.enums;

import lombok.Getter;

@Getter
public enum ComplaintPriority {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    URGENT("Urgent");

    private final String displayName;

    ComplaintPriority(String displayName) {
        this.displayName = displayName;
    }

}
