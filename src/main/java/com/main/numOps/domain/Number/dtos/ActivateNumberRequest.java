package com.main.numOps.domain.Number.dtos;

import jakarta.validation.constraints.NotBlank;

public record ActivateNumberRequest(
        @NotBlank(message = "Cliente não pode ser vazio")
        String cliente,

        @NotBlank(message = "Documento não pode ser vazio")
        String documento,

        String cep,

        String logradouro,

        String numeroEndereco,

        String complemento,

        String bairro,

        String cidade,

        String uf
)
{}
