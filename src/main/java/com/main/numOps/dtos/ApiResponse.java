package com.main.numOps.dtos;

import com.main.numOps.utils.DateUtils;
import com.main.numOps.utils.RequestUtils;

import java.time.LocalDateTime;
import java.util.List;

public record ApiResponse<T>(
        LocalDateTime timestamp,
        int status,
        boolean success,
        String message,
        T data,
        List<ApiError> errors,
        String path
) {

    public static <T> ApiResponse<T> success(T data, String message, int status) {
        return new ApiResponse<>(
                DateUtils.nowWithoutNanos(),
                status,
                true,
                message,
                data,
                null,
                RequestUtils.getCurrentPath());
    }

    public static <T> ApiResponse<T> error(String message, List<ApiError> errors, int status) {
        return new ApiResponse<>(
                DateUtils.nowWithoutNanos(),
                status,
                false,
                message,
                null,
                errors,
                RequestUtils.getCurrentPath());
    }
}
