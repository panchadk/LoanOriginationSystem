package com.adminplatform.los_auth.authorize.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Object> handleInvalidToken(InvalidTokenException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<Object> handleExpiredToken(ExpiredTokenException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.GONE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        return buildErrorResponse("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> buildErrorResponse(String message, HttpStatus status) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);

        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", ex.getStatusCode().value(),
                        "error", ex.getReason()
                ));
    }

}
