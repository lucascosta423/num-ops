package com.main.numOps.domain.ticket.dtos;

import com.main.numOps.domain.ticket.TicketModel;
import com.main.numOps.domain.ticket.enuns.TicketStatus;
import com.main.numOps.domain.ticket.enuns.TicketType;

import java.time.LocalDateTime;

public record TicketReponse(
        Long id,

        String ticket,

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
                model.getFatura(),
                model.getUser().getName(),
                model.getProvider().getNome(),
                model.getCancelBy(),
                model.getCancelAt(),
                model.getDateCreated(),
                model.getDateFinished(),
                model.getType(),
                model.getStatus()
        );
    }
}
