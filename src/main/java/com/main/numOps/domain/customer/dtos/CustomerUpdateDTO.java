package com.main.numOps.domain.customer.dtos;

public record CustomerUpdateDTO(

        Long providerId,

        String cep,

        String logradouro,

        String numeroEndereco,

        String complemento,

        String bairro,

        String cidade,

        String uf

) {
}