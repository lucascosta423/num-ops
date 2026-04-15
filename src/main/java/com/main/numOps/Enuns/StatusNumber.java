package com.main.numOps.Enuns;

public enum StatusNumber {
    AVAILABLE("Disponível"),
    UNAVAILABLE("Não disponível"),
    ACTIVE("Ativo"),
    INACTIVE("Inativo");

    private final String descricao;

    StatusNumber(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
