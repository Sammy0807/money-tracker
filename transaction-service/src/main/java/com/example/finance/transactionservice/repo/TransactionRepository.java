package com.example.finance.transactionservice.repo;

import com.example.finance.transactionservice.domain.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
  Page<Transaction> findByUserIdOrderByOccurredAtDesc(UUID userId, Pageable pageable);
  Page<Transaction> findByAccountIdOrderByOccurredAtDesc(UUID accountId, Pageable pageable);
}
