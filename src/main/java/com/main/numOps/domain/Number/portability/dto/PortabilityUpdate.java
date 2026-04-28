package com.main.numOps.domain.Number.portability.dto;

import com.main.numOps.Enuns.StatusPortability;

import java.util.List;

public record PortabilityUpdate(
        List<Integer> numerosId,
        String dataAgendamento,
        String horaAgendamento,
        StatusPortability status
) {
}
