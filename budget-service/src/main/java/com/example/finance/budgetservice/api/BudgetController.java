package com.example.finance.budgetservice.api;

import com.example.finance.budgetservice.dto.*;
import com.example.finance.budgetservice.mapper.BudgetMapper;
import com.example.finance.budgetservice.service.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {
  private final BudgetService service;
  private final BudgetMapper mapper;

  @GetMapping("/health") public String health() { return "ok"; }

  @PostMapping
  public ResponseEntity<BudgetResponse> create(@RequestBody @Valid CreateBudgetRequest req) {
    var saved = service.create(req);
    return ResponseEntity.created(URI.create("/api/budgets/" + saved.getId()))
        .body(mapper.toResponse(saved));
  }

  @GetMapping("/{id}")
  public ResponseEntity<BudgetResponse> get(@PathVariable UUID id) {
    return service.get(id).map(mapper::toResponse).map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PatchMapping("/{id}")
  public ResponseEntity<BudgetResponse> update(@PathVariable UUID id,
                                               @RequestBody @Valid UpdateBudgetRequest req) {
    return service.update(id, req).map(mapper::toResponse).map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }

  @GetMapping("/by-user/{userId}")
  public Page<BudgetResponse> byUser(@PathVariable UUID userId,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "20") int size) {
    return service.byUser(userId, page, size).map(mapper::toResponse);
  }

  @GetMapping("/by-account/{accountId}")
  public Page<BudgetResponse> byAccount(@PathVariable UUID accountId,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "20") int size) {
    return service.byAccount(accountId, page, size).map(mapper::toResponse);
  }

  @GetMapping("/by-user/{userId}/category/{category}")
  public Page<BudgetResponse> byUserAndCategory(@PathVariable UUID userId,
                                                @PathVariable String category,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "20") int size) {
    return service.byUserAndCategory(userId, category, page, size).map(mapper::toResponse);
  }
}
