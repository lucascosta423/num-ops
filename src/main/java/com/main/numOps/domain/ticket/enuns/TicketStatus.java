package com.main.numOps.domain.ticket.enuns;

import lombok.Getter;

@Getter
public enum TicketStatus {

    CREATED("CREATED", "Ticket created"),
    IN_ANALYSIS("IN_ANALYSIS", "Under analysis"),
    WAITING_CUSTOMER("WAITING_CUSTOMER", "Waiting for customer response"),
    PROCESSING("PROCESSING", "In processing"),
    COMPLETED("COMPLETED", "Successfully completed"),
    CANCELED("CANCELED", "Canceled"),
    ERROR("ERROR", "Finished with error");

    private final String code;
    private final String description;

    TicketStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
