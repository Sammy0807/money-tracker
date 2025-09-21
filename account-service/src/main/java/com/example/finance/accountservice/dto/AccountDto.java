package com.example.finance.accountservice.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountDto(
  UUID id,
  String name,
  String institution,
  String type,
  String currency,
  BigDecimal balance,
  boolean archived,
  String externalRef
) {}
