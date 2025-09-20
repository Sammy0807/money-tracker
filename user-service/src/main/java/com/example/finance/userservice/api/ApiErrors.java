package com.example.finance.userservice.api;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice // global for all @RestController in this app
public class ApiErrors {

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Map<String, String>> handleUnique(DataIntegrityViolationException ex) {
    // Most common case here: duplicate email unique index
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(Map.of("error", "email_already_exists"));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleBodyValidation(MethodArgumentNotValidException ex) {
    var fieldErrors = ex.getBindingResult().getFieldErrors().stream()
        .collect(Collectors.toMap(fe -> fe.getField(), fe -> fe.getDefaultMessage(), (a,b)->a));
    return ResponseEntity.badRequest().body(Map.of(
        "error", "validation_failed",
        "fields", fieldErrors
    ));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map<String, Object>> handleParamValidation(ConstraintViolationException ex) {
    var violations = ex.getConstraintViolations().stream()
        .collect(Collectors.toMap(v -> v.getPropertyPath().toString(), v -> v.getMessage(), (a,b)->a));
    return ResponseEntity.badRequest().body(Map.of(
        "error", "validation_failed",
        "fields", violations
    ));
  }

  // default fallback (keeps stack traces out of API responses)
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, String>> handleOther(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(Map.of("error", "internal_error"));
  }
}
