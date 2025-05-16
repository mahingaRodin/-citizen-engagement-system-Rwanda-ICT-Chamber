package com.citizen.engagement_system_be.enums;

import lombok.Getter;

@Getter
public enum ResponseType {
    INITIAL_RESPONSE("Initial Response"),
    UPDATE("Update"),
    RESOLUTION("Resolution"),
    CLARIFICATION("Clarification"),
    REJECTION("Rejection");

    private final String displayName;

    ResponseType(String displayName) {
        this.displayName = displayName;
    }

}
