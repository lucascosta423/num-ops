package com.main.numOps.domain.didAvailable.enums;

import lombok.Getter;

@Getter
public enum DidAvailableStatus {
    AVAILABLE("AVAILABLE", "Did available"),
    UNAVAILABLE("UNAVAILABLE", "Did unavailable"),
    RESERVED("RESERVED", "Did reserved");

    private final String code;
    private final String description;

    DidAvailableStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
