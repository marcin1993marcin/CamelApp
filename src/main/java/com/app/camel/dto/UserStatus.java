package com.app.camel.dto;

// review - czy gdziekolwiek używamy te statusy? jeżeli nie to klasa do usunięca
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
