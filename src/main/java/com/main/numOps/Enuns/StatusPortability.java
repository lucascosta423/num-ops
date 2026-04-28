package com.main.numOps.Enuns;

import lombok.Getter;

@Getter
public enum StatusPortability {

    AUTHORIZED("A", "Autorizado"),
    PENDING("P", "Pendente"),
    REJECTED("R", "Rejeitado"),
    PORTED("PO", "Portado");

    private final String code;
    private final String description;

    StatusPortability(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static StatusPortability fromCode(String code) {
        for (StatusPortability status : values()) {
            if (status.code.equalsIgnoreCase(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código inválido: " + code);
    }
}
