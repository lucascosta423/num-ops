package com.main.numOps.exeptions;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.main.numOps.dtos.ApiError;
import com.main.numOps.dtos.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(
            MethodArgumentNotValidException ex
    ) {

        List<ApiError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::formatFieldError)
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponse.error(
                                "Validation error",
                                errors,
                                HttpStatus.BAD_REQUEST.value()
                        )
                );
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(
            BusinessException ex
    ) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponse.error(
                                "Business error",
                                List.of(
                                        new ApiError(
                                                null,
                                                ex.getMessage()
                                        )
                                ),
                                HttpStatus.BAD_REQUEST.value()
                        )
                );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataIntegrityViolation(
            DataIntegrityViolationException ex
    ) {

        String causeMessage = ex.getMostSpecificCause().getMessage();

        String details = causeMessage;

        Pattern pattern = Pattern.compile("Key \\((.*?)\\)");
        Matcher matcher = pattern.matcher(causeMessage);

        if (matcher.find()) {
            String field = matcher.group(1);
            details = String.format(
                    "Field '%s' already exists.",
                    field
            );
        }

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(
                        ApiResponse.error(
                                "Data integrity violation",
                                List.of(
                                        new ApiError(
                                                null,
                                                details
                                        )
                                ),
                                HttpStatus.CONFLICT.value()
                        )
                );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFoundException(
            NotFoundException ex
    ) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ApiResponse.error(
                                "Resource not found",
                                List.of(
                                        new ApiError(
                                                null,
                                                ex.getMessage()
                                        )
                                ),
                                HttpStatus.NOT_FOUND.value()
                        )
                );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex
    ) {

        String message = "Malformed request body.";

        ApiError error = new ApiError(
                null,
                ex.getMostSpecificCause().getMessage()
        );

        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException invalidFormatException) {

            if (invalidFormatException.getTargetType().isEnum()) {

                String field = invalidFormatException.getPath()
                        .stream()
                        .findFirst()
                        .map(JsonMappingException.Reference::getFieldName)
                        .orElse(null);

                Object[] acceptedValues =
                        invalidFormatException.getTargetType().getEnumConstants();

                error = new ApiError(
                        field,
                        String.format(
                                "Invalid value '%s'. Accepted values: %s",
                                invalidFormatException.getValue(),
                                java.util.Arrays.toString(acceptedValues)
                        )
                );
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponse.error(
                                message,
                                List.of(error),
                                HttpStatus.BAD_REQUEST.value()
                        )
                );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntimeException(
            RuntimeException ex
    ) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ApiResponse.error(
                                "Internal server error",
                                List.of(
                                        new ApiError(
                                                null,
                                                ex.getMessage()
                                        )
                                ),
                                HttpStatus.INTERNAL_SERVER_ERROR.value()
                        )
                );
    }

    private ApiError formatFieldError(FieldError error) {

        return new ApiError(
                error.getField(),
                error.getDefaultMessage()
        );
    }
}