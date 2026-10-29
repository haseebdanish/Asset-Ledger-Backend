package com.haseeb.assetledger.Exception;

import com.haseeb.assetledger.Dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(
            UserNotFoundException ex) {

        return buildResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
    }

    @ExceptionHandler(AssetNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAssetNotFound(
            AssetNotFoundException ex) {

        return buildResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(
            EmailAlreadyExistsException ex) {

        return buildResponse(
                HttpStatus.CONFLICT,
                ex.getMessage()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error ->
                        error.getField() + ": " + error.getDefaultMessage())
                .distinct()
                .collect(Collectors.joining("; "));

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                message.isBlank() ? "Validation failed" : message
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequestBody(
            HttpMessageNotReadableException ex) {

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Request body is missing or contains invalid JSON"
        );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex) {

        return buildResponse(
                HttpStatus.METHOD_NOT_ALLOWED,
                "HTTP method not supported for this endpoint"
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(
            DataIntegrityViolationException ex) {

        log.warn("Database constraint violation", ex);

        return buildResponse(
                HttpStatus.CONFLICT,
                "The request conflicts with existing data"
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(
            Exception ex) {

        log.error("Unexpected application error", ex);

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. Please try again later."
        );
    }

    private ResponseEntity<ErrorResponse> buildResponse(
            HttpStatus status,
            String message) {

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message
        );

        return ResponseEntity.status(status).body(response);
    }
}
