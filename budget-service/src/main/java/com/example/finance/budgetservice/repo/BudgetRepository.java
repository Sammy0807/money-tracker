package com.example.finance.budgetservice.repo;

import com.example.finance.budgetservice.domain.Budget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.UUID;

public interface BudgetRepository extends JpaRepository<Budget, UUID> {
  Page<Budget> findByUserIdOrderByPeriodStartDesc(UUID userId, Pageable pageable);

  Page<Budget> findByUserIdAndCategoryIgnoreCaseOrderByPeriodStartDesc(
      UUID userId, String category, Pageable pageable);

  Page<Budget> findByAccountIdOrderByPeriodStartDesc(UUID accountId, Pageable pageable);

  boolean existsByUserIdAndCategoryIgnoreCaseAndPeriodStartLessThanEqualAndPeriodEndGreaterThanEqual(
      UUID userId, String category, LocalDate end, LocalDate start);
}
