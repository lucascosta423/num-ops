package com.main.numOps.domain.did.enums;

import lombok.Getter;

@Getter
public enum DidStatus {
    AVAILABLE("AVAILABLE", "Did available"),
    UNAVAILABLE("UNAVAILABLE", "Did unavailable"),
    RESERVED("RESERVED", "Did reserved"),
    ACTIVE("ACTIVE", "Did Active"),
    CANCEL("CANCEL", "Did cancel");

    private final String code;
    private final String description;

    DidStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
