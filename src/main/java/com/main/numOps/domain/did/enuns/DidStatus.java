package com.main.numOps.domain.did.enuns;

import lombok.Getter;

@Getter
public enum DidStatus {
    AVAILABLE("AVAILABLE", "Did available"),
    UNAVAILABLE("UNAVAILABLE", "Did unavailable"),
    RESERVED("RESERVED", "Did reserved");

    private final String code;
    private final String description;

    DidStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
