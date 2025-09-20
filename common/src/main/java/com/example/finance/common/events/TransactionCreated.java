package com.example.finance.common.events;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record TransactionCreated(
  UUID id,
  UUID accountId,
  UUID userId,
  Instant postedAt,
  int amountCents,
  String merchant,
  String category,
  Set<String> tags
) {}
