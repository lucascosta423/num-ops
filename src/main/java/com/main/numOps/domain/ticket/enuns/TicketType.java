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

}
