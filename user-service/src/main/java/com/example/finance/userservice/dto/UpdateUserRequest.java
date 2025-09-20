package com.example.finance.userservice.dto;

import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
  @Size(max=100) String name,
  @Size(max=10) String locale,
  @Size(max=10) String currency,
  @Size(max=64) String timezone,
  String featureFlagsJson
) { }
