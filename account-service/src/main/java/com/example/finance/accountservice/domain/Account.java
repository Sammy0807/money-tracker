package com.example.finance.accountservice.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "accounts",
  uniqueConstraints = {
    @UniqueConstraint(name="UX_accounts_user_name", columnNames={"user_id","name"})
  })
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Account {

  @Id
  private UUID id;

  @Column(name="user_id", nullable=false)
  private UUID userId;

  @Column(nullable=false, length=100)
  private String name;

  @Column(length=120)
  private String institution;

  @Column(nullable=false, length=40)
  private String type; // enum later

  @Column(nullable=false, length=10)
  private String currency;

  @Column(nullable=false, precision=18, scale=2)
  private BigDecimal balance;

  @Column(name="external_ref", length=200)
  private String externalRef;

  @Column(name="is_archived", nullable=false)
  private boolean archived;

  @Column(name="created_at", nullable=false, updatable=false)
  private Instant createdAt;

  @UpdateTimestamp
  @Column(name="updated_at", nullable=false)
  private Instant updatedAt;

  @PrePersist
  void prePersist() {
    if (id == null) id = UUID.randomUUID();
    if (createdAt == null) createdAt = Instant.now();
    if (balance == null) balance = BigDecimal.ZERO;
    if (currency == null) currency = "USD";
  }
}
