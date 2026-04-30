package com.main.numOps.domain.did.dtos;

import com.main.numOps.Enuns.StatusNumber;
import com.main.numOps.domain.did.DidModel;

import java.time.LocalDateTime;

public record NumberResponse(
        Integer id,
        String numero,
        String area,
        String ufArea,
        LocalDateTime dateAtivacao,
        StatusNumber statusNumber
) {
    public static NumberResponse fromEntity(DidModel n) {
        return new NumberResponse(
                n.getId(),
                n.getNumero(),
                n.getArea(),
                n.getUfArea(),
                n.getDateCreated(),
                n.getStatusNumber()
        );
    }
}
