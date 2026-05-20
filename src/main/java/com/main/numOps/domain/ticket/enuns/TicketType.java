package com.main.numOps.domain.ticket.enuns;

import lombok.Getter;

@Getter
public enum TicketType {
    DID("DID", "SDID"),
    PORTABILITY("PORTABILITY", "SPOR"),
    CANCELLATION("CANCELLATION","SCAN" );

    private final String descriptor;
    private final String prefixo;

    TicketType(String descriptor, String prefixo) {
        this.descriptor = descriptor;
        this.prefixo = prefixo;
    }

    public static TicketType from(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Ticket type cannot be null");
        }

        for (TicketType type : values()) {
            if (type.descriptor.equalsIgnoreCase(value)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Invalid ticket type: " + value);
    }

}
