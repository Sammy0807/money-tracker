package com.example.finance.analyticsservice.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TransactionEvent(
        UUID id,
        UUID userId,
        String category,
        BigDecimal amount,
        String currency,
        Instant occurredAt
) {}
