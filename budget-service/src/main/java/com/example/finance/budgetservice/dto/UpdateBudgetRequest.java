package com.example.finance.budgetservice.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record UpdateBudgetRequest(
  UUID accountId,
  String category,
  LocalDate periodStart,
  LocalDate periodEnd,
  BigDecimal amount,
  String currency
) {}
