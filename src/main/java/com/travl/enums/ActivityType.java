package com.travl.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum ActivityType {
    ADVENTURE("ADVENTURE"),
    TREK("TREK"),
    SCUBADIVING("SCUBA DIVING");
    private String type;

    ActivityType(String type) {
        this.type = type;
    }

    @JsonCreator
    public static ActivityType fromValue(String status) {
        for (ActivityType activityType : values()) {
            if (activityType.type.equalsIgnoreCase(status)) {
                return activityType;
            }
        }
        throw new IllegalArgumentException("Allowed values are: " + Arrays.toString(values()));
    }

    @JsonValue
    public String toValue() {
        return type;
    }
}
