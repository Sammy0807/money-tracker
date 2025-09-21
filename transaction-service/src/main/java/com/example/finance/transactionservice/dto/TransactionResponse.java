package com.example.finance.transactionservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponse(
    UUID id,
    UUID userId,
    UUID accountId,
    BigDecimal amount,
    String currency,
    String type,
    String category,
    String description,
    LocalDateTime occurredAt,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
