package com.main.numOps.domain.did.dtos;

import com.main.numOps.domain.did.DidModel;
import com.main.numOps.domain.did.enums.DidStatus;

import java.time.LocalDateTime;

public record DidWithoutDidDTO(
        Long id,
        String numero,
        String razao,
        LocalDateTime createdAt,
        DidStatus status
) {
    public static DidWithoutDidDTO fromEntity(DidModel model){
        return new DidWithoutDidDTO(
                model.getId(),
                model.getDid().getNumero(),
                model.getCustomer().getRazao(),
                model.getCreatedAt(),
                model.getStatus()

        );
    }
}
