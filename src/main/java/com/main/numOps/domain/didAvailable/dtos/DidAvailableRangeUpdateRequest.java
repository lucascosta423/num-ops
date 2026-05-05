package com.main.numOps.domain.didAvailable.dtos;

import com.main.numOps.Enuns.DidStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record DidAvailableRangeUpdateRequest(
        @NotEmpty
        String cn,

        @NotEmpty
        String prefixo,

        @NotEmpty
        String start,

        @NotEmpty
        String end,

        @NotNull
        DidStatus status
) {
}
