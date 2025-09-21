package com.example.finance.accountservice.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateAccountRequest(
  @NotBlank String name,
  String institution,
  @NotBlank String type,
  @NotBlank String currency,
  @PositiveOrZero BigDecimal openingBalance,
  String externalRef
) {}
