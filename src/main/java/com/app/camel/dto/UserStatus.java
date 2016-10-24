package com.app.camel.dto;

public enum UserStatus {

    DISABLED(0), ACTIVE(1);

    private final int status;

    UserStatus(int i) {
        this.status = i;
    }

    public int getStatus() {
        return status;
    }
}
