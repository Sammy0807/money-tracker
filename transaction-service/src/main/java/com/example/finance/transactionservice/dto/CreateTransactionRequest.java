package com.example.finance.transactionservice.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record CreateTransactionRequest(
    @NotNull UUID userId,
    @NotNull UUID accountId,
    @NotNull @DecimalMin("0.00") BigDecimal amount,
    @NotBlank String currency,
    @NotBlank String type,          // e.g. "DEBIT", "CREDIT", "TRANSFER"
    String category,
    String description,
    @NotNull LocalDateTime occurredAt
) {}
