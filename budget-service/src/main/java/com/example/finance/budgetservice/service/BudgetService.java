package com.example.finance.budgetservice.service;

import com.example.finance.budgetservice.domain.Budget;
import com.example.finance.budgetservice.dto.*;
import com.example.finance.budgetservice.mapper.BudgetMapper;
import com.example.finance.budgetservice.repo.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BudgetService {
  private final BudgetRepository repo;
  private final BudgetMapper mapper;

  @Transactional
  public Budget create(CreateBudgetRequest req) {
    // (optional) prevent duplicate overlapping budgets for same category
    // boolean overlap = repo.existsByUserIdAndCategoryIgnoreCaseAndPeriodStartLessThanEqualAndPeriodEndGreaterThanEqual(
    //   req.userId(), req.category(), req.periodEnd(), req.periodStart());
    // if (overlap) throw new IllegalArgumentException("Overlapping budget exists");

    return repo.save(mapper.toEntity(req));
  }

  @Transactional(readOnly = true)
  public Optional<Budget> get(UUID id) { return repo.findById(id); }

  @Transactional
  public Optional<Budget> update(UUID id, UpdateBudgetRequest req) {
    return repo.findById(id).map(b -> { mapper.update(b, req); return repo.save(b); });
  }

  @Transactional
  public boolean delete(UUID id) {
    if (repo.existsById(id)) { repo.deleteById(id); return true; }
    return false;
  }

  @Transactional(readOnly = true)
  public Page<Budget> byUser(UUID userId, int page, int size) {
    return repo.findByUserIdOrderByPeriodStartDesc(userId, PageRequest.of(page, Math.min(size, 200)));
  }

  @Transactional(readOnly = true)
  public Page<Budget> byAccount(UUID accountId, int page, int size) {
    return repo.findByAccountIdOrderByPeriodStartDesc(accountId, PageRequest.of(page, Math.min(size, 200)));
  }

  @Transactional(readOnly = true)
  public Page<Budget> byUserAndCategory(UUID userId, String category, int page, int size) {
    return repo.findByUserIdAndCategoryIgnoreCaseOrderByPeriodStartDesc(
        userId, category, PageRequest.of(page, Math.min(size, 200)));
  }
}
