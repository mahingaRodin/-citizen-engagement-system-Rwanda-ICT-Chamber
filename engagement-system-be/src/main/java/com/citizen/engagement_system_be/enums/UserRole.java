package com.citizen.engagement_system_be.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    CITIZEN("Citizen"),
    AGENCY_ADMIN("Agency Administrator"),
    SYSTEM_ADMIN("System Administrator");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

}
