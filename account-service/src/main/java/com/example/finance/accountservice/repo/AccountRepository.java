package com.example.finance.accountservice.repo;

import com.example.finance.accountservice.domain.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
  Page<Account> findAllByUserIdAndArchivedFalse(UUID userId, Pageable pageable);
  Optional<Account> findByUserIdAndId(UUID userId, UUID id);
  Optional<Account> findByUserIdAndExternalRef(UUID userId, String externalRef);
  boolean existsByUserIdAndNameAndArchivedFalse(UUID userId, String name);
}
