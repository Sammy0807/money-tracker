package com.example.finance.transactionservice.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/transaction")
public class HealthController {
  @GetMapping("/health")
  public Map<String, Object> health() {
    return Map.of("status", "UP", "service", "transaction-service");
  }
}
