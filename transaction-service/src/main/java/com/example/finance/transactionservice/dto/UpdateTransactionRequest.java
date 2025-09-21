package com.example.finance.transactionservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UpdateTransactionRequest(
    BigDecimal amount,
    String currency,
    String type,
    String category,
    String description,
    LocalDateTime occurredAt
) {}
