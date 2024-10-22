package com.wora.common.infrastructure.web;

import com.wora.common.domain.ErrorResponse;
import com.wora.common.domain.exception.EntityCreationException;
import com.wora.common.domain.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestAdviceController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationExceptions(final MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            final String fieldName = ((FieldError) error).getField();
            final String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                "Validation failed",
                request.getDescription(false),
                errors);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse entityNotFoundException(final EntityNotFoundException ex, WebRequest request) {
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                Map.of()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse illegalArgumentException(final IllegalArgumentException ex, WebRequest request) {
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                Map.of()
        );
    }

    @ExceptionHandler(EntityCreationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse entityCreationException(final EntityCreationException ex, WebRequest request) {
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                Map.of("error", String.join("\n",ex.errors()))
        );
    }
}
