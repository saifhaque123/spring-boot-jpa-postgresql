package com.travl.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum ProposalStatus {
    INITIAL("initial"),
    TRAVELLING("travelling"),
    COMPLETED("completed");
    private String status;

    ProposalStatus(String status) {
        this.status = status;
    }

    @JsonCreator
    public static ProposalStatus fromValue(String status) {
        for (ProposalStatus proposalStatus : values()) {
            if (proposalStatus.status.equalsIgnoreCase(status)) {
                return proposalStatus;
            }
        }
        throw new IllegalArgumentException("Allowed values are: " + Arrays.toString(values()));
    }

    @JsonValue
    public String toValue() {
        return status;
    }
}
