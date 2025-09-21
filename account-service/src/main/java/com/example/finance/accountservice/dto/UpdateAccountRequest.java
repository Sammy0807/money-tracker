package com.example.finance.accountservice.dto;

import java.math.BigDecimal;

public record UpdateAccountRequest(
  String name,
  String institution,
  String type,
  String currency,
  BigDecimal balance,
  Boolean archived
) {}
