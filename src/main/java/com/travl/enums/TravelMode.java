package com.travl.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;

public enum TravelMode {
    AIR("air"),
    TRAIN("train"),
    ROAD("road");
    private String mode;

    TravelMode(String mode) {
        this.mode = mode;
    }

    @JsonCreator
    public static TravelMode fromValue(String mode) {
        for (TravelMode travelMode : values()) {
            if (travelMode.mode.equalsIgnoreCase(mode)) {
                return travelMode;
            }
        }
        throw new IllegalArgumentException("Allowed values are: " + Arrays.toString(values()));
    }

    @JsonValue
    public String toValue() {
        return mode;
    }
}
