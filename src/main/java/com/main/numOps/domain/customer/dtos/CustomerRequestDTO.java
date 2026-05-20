package com.main.numOps.domain.customer.dtos;

import com.main.numOps.domain.customer.enums.TypeDocument;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CustomerRequestDTO(

        @NotEmpty
        String razao,

        @NotEmpty
        String document,

        @NotNull
        TypeDocument typeDocument,

        String cep,

        String logradouro,

        String numeroEndereco,

        String complemento,

        String bairro,

        String cidade,

        String uf
) {
}
