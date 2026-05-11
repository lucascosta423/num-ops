package com.main.numOps.domain.did.dtos;

import com.main.numOps.domain.did.enums.DidStatus;

import java.time.LocalDateTime;

public record DidWithoutDidDTO(
        Integer id,
        String razao,
        String document,
        String cep,
        String logradouro,
        String numeroEndereco,
        String complemento,
        String bairro,
        String cidade,
        String uf,
        String providerNome,
        LocalDateTime createdAt,
        DidStatus status
) {}
