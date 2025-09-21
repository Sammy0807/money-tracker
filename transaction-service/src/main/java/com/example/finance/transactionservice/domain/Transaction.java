package com.example.finance.transactionservice.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions", indexes = {
    @Index(name = "ix_trx_user", columnList = "user_id, occurred_at"),
    @Index(name = "ix_trx_account", columnList = "account_id, occurred_at")
})
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Transaction {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
  private UUID id;

  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(name = "account_id", nullable = false)
  private UUID accountId;

  @Column(nullable = false, precision = 19, scale = 4)
  private BigDecimal amount;

  @Column(nullable = false, length = 3)
  private String currency;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private TransactionType type;

  @Column(length = 50)
  private String category;

  @Column(length = 400)
  private String description;

  // When the user says it happened (business timestamp)
  @Column(name = "occurred_at", nullable = false, columnDefinition = "datetime2")
  private LocalDateTime occurredAt;

  // Audit timestamps (store as datetime2 to avoid offset mismatch in SQL Server)
  @Column(name = "created_at", nullable = false, columnDefinition = "datetime2")
  private LocalDateTime createdAt;

  @Column(name = "updated_at", columnDefinition = "datetime2")
  private LocalDateTime updatedAt;

  @PrePersist
  void onCreate() {
    if (createdAt == null) createdAt = LocalDateTime.now();
  }

  @PreUpdate
  void onUpdate() {
    updatedAt = LocalDateTime.now();
  }
}
