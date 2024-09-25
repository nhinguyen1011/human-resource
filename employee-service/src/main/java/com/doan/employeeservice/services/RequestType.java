package com.doan.employeeservice.services;

import lombok.Getter;

import java.util.Objects;

@Getter
public enum RequestType {
    LEAVE("leave"),
    TIMESHEET_UPDATE("timesheet_update"),
    CHECK_IN("check_in"),
    CHECK_OUT("check_out"),
    WORK_FROM_HOME("work_from_home");

    private final String value;

    RequestType(String value) {
        this.value = value;
    }

    public static RequestType fromValue(String value) {
        for (RequestType type : RequestType.values()) {
            if (Objects.equals(type.getValue(), value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + value);
    }
}
