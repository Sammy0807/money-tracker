package com.example.finance.accountservice.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ApiErrors {
  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<Map<String,String>> conflict(IllegalStateException ex) {
    if ("account_name_exists".equals(ex.getMessage())) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error","account_name_exists"));
    }
    return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error","conflict"));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String,String>> fallback(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(Map.of("error","internal_error"));
  }
}
