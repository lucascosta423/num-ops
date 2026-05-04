package com.main.numOps.Enuns;

import lombok.Getter;

@Getter
public enum DidStatus {
    AVAILABLE("AVAILABLE", "Did available"),
    UNAVAILABLE("UNAVAILABLE", "Did unavailable"),
    RESERVED("RESERVED", "Did reserved"),
    ACTIVE("ACTIVE", "Did Active");

    private final String code;
    private final String description;

    DidStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
