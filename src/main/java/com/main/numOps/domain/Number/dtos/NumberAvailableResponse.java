package com.main.numOps.domain.Number.dtos;

import com.main.numOps.domain.Number.NumberModel;

public record NumberAvailableResponse(
        Integer id,
        String numero
) {
    public static NumberAvailableResponse fromEntity(NumberModel numberModel) {
        return new NumberAvailableResponse(
                numberModel.getId(),
                numberModel.getNumero()
        );
    }
}
