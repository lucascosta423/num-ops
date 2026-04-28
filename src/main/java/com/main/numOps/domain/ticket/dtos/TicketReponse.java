package com.main.numOps.domain.ticket.dtos;

import com.main.numOps.domain.ticket.TicketModel;
import com.main.numOps.domain.ticket.enuns.TicketStatus;
import com.main.numOps.domain.ticket.enuns.TicketType;

import java.time.LocalDateTime;

public record TicketReponse(
        Integer id,

        String ticket,

        String razao,

        String documento,

        String cep,

        String logradouro,

        String numeroEndereco,

        String complemento,

        String bairro,

        String cidade,

        String uf,

        String fatura,

        String user,

        String provider,

        String cancelRequestedBy,

        LocalDateTime cancelRequestedAt,

        LocalDateTime dateCreated,

        LocalDateTime dateFinished,

        TicketType type,

        TicketStatus status

) {

    public static TicketReponse fromEntity(TicketModel model){
        return new TicketReponse(
                model.getId(),
                model.getTicket(),
                model.getRazao(),
                model.getDocumento(),
                model.getCep(),
                model.getLogradouro(),
                model.getNumeroEndereco(),
                model.getComplemento(),
                model.getBairro(),
                model.getCidade(),
                model.getUf(),
                model.getFatura(),
                model.getUser().getName(),
                model.getProvider().getNome(),
                model.getCancelRequestedBy(),
                model.getCancelRequestedAt(),
                model.getDateCreated(),
                model.getDateFinished(),
                model.getType(),
                model.getStatus()
        );
    }
}
