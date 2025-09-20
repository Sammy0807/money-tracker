package com.example.finance.notificationservice.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/notification")
public class HealthController {
  @GetMapping("/health")
  public Map<String, Object> health() {
    return Map.of("status", "UP", "service", "notification-service");
  }
}
