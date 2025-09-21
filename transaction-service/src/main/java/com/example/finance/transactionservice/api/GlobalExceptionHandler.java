package com.example.finance.transactionservice.api;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
    var msg = ex.getBindingResult().getAllErrors().stream()
        .findFirst().map(e -> e.getDefaultMessage()).orElse("Validation error");
    var body = new ApiError("VALIDATION_ERROR", msg, HttpStatus.BAD_REQUEST.value(), Instant.now());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ApiError> handleConstraint(DataIntegrityViolationException ex) {
    var body = new ApiError("CONSTRAINT_VIOLATION", ex.getMostSpecificCause().getMessage(),
        HttpStatus.CONFLICT.value(), Instant.now());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleGeneric(Exception ex) {
    var body = new ApiError("INTERNAL_ERROR", ex.getMessage(),
        HttpStatus.INTERNAL_SERVER_ERROR.value(), Instant.now());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
  }
}
