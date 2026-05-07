package com.main.numOps.dtos;

public record ApiError(
        String field,
        String message
) {
}