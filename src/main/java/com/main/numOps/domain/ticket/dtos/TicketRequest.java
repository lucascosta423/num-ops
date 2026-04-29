package com.main.numOps.domain.ticket.dtos;

import com.main.numOps.domain.ticket.enuns.TicketType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record TicketRequest(
        @NotBlank
        String razao,

        @NotBlank
        String documento,

        String cep,

        String logradouro,

        String numeroEndereco,

        String complemento,

        String bairro,

        String cidade,

        String uf,

        TicketType type,

        @NotEmpty
        List<String> numeros,

        MultipartFile fatura
) {
}
