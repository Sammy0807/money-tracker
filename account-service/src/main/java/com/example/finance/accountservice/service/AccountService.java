package com.example.finance.accountservice.service;

import com.example.finance.accountservice.domain.Account;
import com.example.finance.accountservice.dto.CreateAccountRequest;
import com.example.finance.accountservice.dto.UpdateAccountRequest;
import com.example.finance.accountservice.repo.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {
  private final AccountRepository repo;

  @Transactional(readOnly = true)
  public Page<Account> mine(UUID userId, Pageable pageable) {
    return repo.findAllByUserIdAndArchivedFalse(userId, pageable);
  }

  @Transactional(readOnly = true)
  public Optional<Account> findOwned(UUID userId, UUID accountId) {
    return repo.findByUserIdAndId(userId, accountId);
  }

  @Transactional
  public Account createOrRestore(UUID userId, CreateAccountRequest req) {
    // If externalRef present, use it as idempotency key
    if (req.externalRef() != null) {
      var existingByExt = repo.findByUserIdAndExternalRef(userId, req.externalRef());
      if (existingByExt.isPresent()) return existingByExt.get();
    }

    // If same name exists and archived=false => treat as conflict (or return it)
    if (repo.existsByUserIdAndNameAndArchivedFalse(userId, req.name())) {
      throw new IllegalStateException("account_name_exists");
    }

    var a = Account.builder()
        .userId(userId)
        .name(req.name())
        .institution(req.institution())
        .type(req.type())
        .currency(req.currency())
        .balance(req.openingBalance() == null ? BigDecimal.ZERO : req.openingBalance())
        .externalRef(req.externalRef())
        .archived(false)
        .build();
    return repo.save(a);
  }

  @Transactional
  public Optional<Account> update(UUID userId, UUID accountId, UpdateAccountRequest req) {
    return repo.findByUserIdAndId(userId, accountId).map(a -> {
      if (req.name() != null) a.setName(req.name());
      if (req.institution() != null) a.setInstitution(req.institution());
      if (req.type() != null) a.setType(req.type());
      if (req.currency() != null) a.setCurrency(req.currency());
      if (req.balance() != null) a.setBalance(req.balance());
      if (req.archived() != null) a.setArchived(req.archived());
      return repo.save(a);
    });
  }

  @Transactional
  public boolean softDelete(UUID userId, UUID accountId) {
    return repo.findByUserIdAndId(userId, accountId).map(a -> {
      a.setArchived(true);
      repo.save(a);
      return true;
    }).orElse(false);
  }
}
