package com.main.numOps.Enuns;

import lombok.Getter;

@Getter
public enum Status {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String descriptor;

    Status(String descriptor) {
        this.descriptor = descriptor;
    }

}
