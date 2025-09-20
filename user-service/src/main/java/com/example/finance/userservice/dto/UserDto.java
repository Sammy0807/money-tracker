package com.example.finance.userservice.dto;

import jakarta.validation.constraints.Email;
import java.util.UUID;

public record UserDto(
  UUID id,
  @Email String email,
  String name,
  String locale,
  String currency,
  String timezone,
  String featureFlagsJson
) { }
