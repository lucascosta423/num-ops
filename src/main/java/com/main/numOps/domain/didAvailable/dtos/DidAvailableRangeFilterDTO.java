package com.main.numOps.domain.didAvailable.dtos;

import jakarta.validation.constraints.NotEmpty;

public record DidAvailableRangeFilterDTO(
        @NotEmpty
        String cn,
        @NotEmpty
        String prefixo,
        @NotEmpty
        String start,
        @NotEmpty
        String end
) {}
