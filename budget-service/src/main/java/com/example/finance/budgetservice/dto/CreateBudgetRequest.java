package com.example.finance.budgetservice.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CreateBudgetRequest(
  @NotNull UUID userId,
  UUID accountId,
  @NotBlank String category,
  @NotNull LocalDate periodStart,
  @NotNull LocalDate periodEnd,
  @NotNull @DecimalMin("0.00") BigDecimal amount,
  @NotBlank String currency
) {}
