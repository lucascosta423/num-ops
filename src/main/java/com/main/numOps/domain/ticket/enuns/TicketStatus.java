package com.main.numOps.domain.ticket.enuns;

import lombok.Getter;

@Getter
public enum TicketStatus {

    CREATED("CREATED", "Ticket created"),
    IN_ANALYSIS("IN_ANALYSIS", "Under analysis"),
    PROCESSING("PROCESSING", "In processing"),
    COMPLETED("COMPLETED", "Successfully completed"),
    CANCELED("CANCELED", "Canceled");

    private final String code;
    private final String description;

    TicketStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
