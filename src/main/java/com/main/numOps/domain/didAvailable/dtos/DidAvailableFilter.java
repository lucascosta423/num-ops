package com.main.numOps.domain.didAvailable.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DidAvailableFilter(
        @NotBlank
        String area,

        @NotBlank
        @Size(min = 2, max = 2)
        String uf
) {
}
