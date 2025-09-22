package com.example.finance.budgetservice.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "budgets", schema = "budget")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Budget {
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
  private UUID id;

  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(name = "account_id")
  private UUID accountId;

  @Column(nullable = false, length = 50)
  private String category;

  @Column(name = "period_start", nullable = false)
  private LocalDate periodStart;

  @Column(name = "period_end", nullable = false)
  private LocalDate periodEnd;

  @Column(nullable = false, precision = 19, scale = 4)
  private BigDecimal amount;

  @Column(nullable = false, length = 3)
  private String currency;

  @Column(name = "created_at", nullable = false, columnDefinition = "datetime2")
  private LocalDateTime createdAt;

  @Column(name = "updated_at", columnDefinition = "datetime2")
  private LocalDateTime updatedAt;

  @PrePersist
  void onCreate() { if (createdAt == null) createdAt = LocalDateTime.now(); }

  @PreUpdate
  void onUpdate() { updatedAt = LocalDateTime.now(); }
}
