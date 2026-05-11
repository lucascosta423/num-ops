package com.main.numOps.domain.did.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ActivateDidRequest(

        @NotEmpty(message = "A lista de números é obrigatória")
        List<@NotNull(message = "Número inválido") Long> numeros,

        @NotBlank(message = "Cliente não pode ser vazio")
        String razao,

        @NotBlank(message = "document não pode ser vazio")
        String document,

        String cep,

        String logradouro,

        String numeroEndereco,

        String complemento,

        String bairro,

        String cidade,

        String uf
)
{}
