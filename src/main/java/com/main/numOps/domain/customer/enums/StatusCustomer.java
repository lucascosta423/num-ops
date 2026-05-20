package com.main.numOps.domain.customer.enums;

import lombok.Getter;

@Getter
public enum StatusCustomer {
       ACTIVE("ACTIVE", "Customer Active");

    private final String code;
    private final String description;

    StatusCustomer(String code, String description) {
        this.code = code;
        this.description = description;
    }
}

