package com.main.numOps.domain.didAvailable.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record DidAvailableRangeFilterDTO(
        @NotEmpty
        @Size(min = 2,max = 2,message = "The size should be 2.")
        String cn,

        @Size(min = 4,max = 4,message = "The size should be 4.")
        @NotEmpty
        String prefixo,

        @Size(min = 4,max = 4, message = "The size should be 4.")
        @NotEmpty
        String start,

        @Size(min = 4,max = 4, message = "The size should be 4.")
        @NotEmpty
        String end
) {}
