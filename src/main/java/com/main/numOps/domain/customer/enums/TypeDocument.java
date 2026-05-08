package com.main.numOps.domain.customer.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TypeDocument {

    CNPJ("CNPJ", "Cadastro Nacional da Pessoa Jurídica"),
    CPF("CPF", "Cadastro de Pessoa Física");

    private final String code;
    private final String description;

    public static TypeDocument fromCode(String code) {
        for (TypeDocument value : values()) {
            if (value.code.equalsIgnoreCase(code)) {
                return value;
            }
        }

        throw new IllegalArgumentException("Invalid document type: " + code);
    }
}