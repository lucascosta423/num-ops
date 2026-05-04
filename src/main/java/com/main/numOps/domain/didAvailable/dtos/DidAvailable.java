package com.main.numOps.domain.didAvailable.dtos;

import com.main.numOps.domain.didAvailable.DidAvailableModel;

public record DidAvailable(
        Integer id,
        String numero
) {
    public static DidAvailable fromEntity(DidAvailableModel didModel) {
        return new DidAvailable(
                didModel.getId(),
                didModel.getNumero()
        );
    }
}
