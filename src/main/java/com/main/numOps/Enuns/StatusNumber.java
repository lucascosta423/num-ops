package com.main.numOps.Enuns;

import lombok.Getter;

@Getter
public enum StatusNumber {
    AVAILABLE("Disponível"),
    UNAVAILABLE("Não disponível"),
    ACTIVE("Ativo"),
    INACTIVE("Inativo");

    private final String descriptor;

    StatusNumber(String descriptor) {
        this.descriptor = descriptor;
    }

}
