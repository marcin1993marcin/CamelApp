package com.app.camel;

public enum UserStatus {

    ACTIVE(1), DISABLED(0);

    private final int status;

    UserStatus(int i) {
        this.status = i;
    }

    public int getStatus() {
        return status;
    }
}
