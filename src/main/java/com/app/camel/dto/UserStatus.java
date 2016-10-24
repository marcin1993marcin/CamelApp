package com.app.camel.dto;

public enum UserStatus {

    RETIRED("Retired"), ACTIVE("Active");

    private final String status;

    UserStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
