package com.example.finance.transactionservice.service;

import com.example.finance.transactionservice.domain.Transaction;
import com.example.finance.transactionservice.dto.CreateTransactionRequest;
import com.example.finance.transactionservice.dto.UpdateTransactionRequest;
import com.example.finance.transactionservice.mapper.TransactionMapper;
import com.example.finance.transactionservice.repo.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

  private final TransactionRepository repo;
  private final TransactionMapper mapper;

  @Transactional
  public Transaction create(CreateTransactionRequest req) {
    Transaction tx = mapper.toEntity(req);

    // ensure posted_at is not null (use occurredAt when available)
    // ensure occurredAt is set (fallback to now)
  if (tx.getOccurredAt() == null) {
    tx.setOccurredAt(LocalDateTime.now());
  }

    return repo.save(tx);
  }

  @Transactional(readOnly = true)
  public Optional<Transaction> get(UUID id) {
    return repo.findById(id);
  }

  @Transactional
  public Optional<Transaction> update(UUID id, UpdateTransactionRequest req) {
    return repo.findById(id).map(existing -> {
      mapper.update(existing, req);
      return repo.save(existing);
    });
  }

  @Transactional
  public boolean delete(UUID id) {
    if (repo.existsById(id)) {
      repo.deleteById(id);
      return true;
    }
    return false;
  }

  @Transactional(readOnly = true)
  public Page<Transaction> listByUser(UUID userId, int page, int size) {
    return repo.findByUserIdOrderByOccurredAtDesc(userId, PageRequest.of(page, Math.min(size, 200)));
  }

  @Transactional(readOnly = true)
  public Page<Transaction> listByAccount(UUID accountId, int page, int size) {
    return repo.findByAccountIdOrderByOccurredAtDesc(accountId, PageRequest.of(page, Math.min(size, 200)));
  }
}
