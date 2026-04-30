package com.main.numOps.domain.did.dtos;

import com.main.numOps.domain.did.DidModel;

public record NumberAvailableResponse(
        Integer id,
        String numero
) {
    public static NumberAvailableResponse fromEntity(DidModel didModel) {
        return new NumberAvailableResponse(
                didModel.getId(),
                didModel.getNumero()
        );
    }
}
