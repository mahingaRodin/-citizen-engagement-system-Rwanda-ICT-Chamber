package com.citizen.engagement_system_be.enums;

import lombok.Getter;

@Getter
public enum ComplaintType {
    INFRASTRUCTURE("Infrastructure"),
    PUBLIC_SERVICE("Public Service"),
    ENVIRONMENTAL("Environmental"),
    SAFETY("Safety"),
    HEALTH("Health"),
    EDUCATION("Education"),
    TRANSPORTATION("Transportation"),
    OTHER("Other");

    private final String displayName;

    ComplaintType(String displayName) {
        this.displayName = displayName;
    }

}
