package com.main.numOps.domain.ticket.dtos;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record TicketRequest(
        String razao,
        String documento,
        String cep,
        String logradouro,
        String numeroEndereco,
        String complemento,
        String bairro,
        String cidade,
        String uf,
        String type,
        List<String> numeros,
        MultipartFile fatura
) {
}
