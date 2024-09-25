package com.doan.employeeservice.services;

import lombok.Getter;

import java.util.Objects;

@Getter
public enum RequestStatus {
    PENDING("pending"),
    APPROVED("approved"),
    REJECTED("rejected");

    private final String value;

    RequestStatus(String value) {
        this.value = value;
    }

    public static RequestStatus fromValue(String value) {
        for (RequestStatus status : RequestStatus.values()) {
            if (Objects.equals(status.getValue(), value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + value);
    }
}
