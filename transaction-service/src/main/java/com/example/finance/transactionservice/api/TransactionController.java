package com.example.finance.transactionservice.api;

import com.example.finance.transactionservice.dto.CreateTransactionRequest;
import com.example.finance.transactionservice.dto.TransactionResponse;
import com.example.finance.transactionservice.dto.UpdateTransactionRequest;
import com.example.finance.transactionservice.mapper.TransactionMapper;
import com.example.finance.transactionservice.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

  private final TransactionService service;
  private final TransactionMapper mapper;

  @GetMapping("/health")
  public String health() { return "ok"; }

  @PostMapping
  public ResponseEntity<TransactionResponse> create(@RequestBody @Valid CreateTransactionRequest req) {
    var saved = service.create(req);
    return ResponseEntity
        .created(URI.create("/api/transactions/" + saved.getId()))
        .body(mapper.toResponse(saved));
  }

  @GetMapping("/{id}")
  public ResponseEntity<TransactionResponse> get(@PathVariable("id") UUID id) {
    return service.get(id)
        .map(mapper::toResponse)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PatchMapping("/{id}")
  public ResponseEntity<TransactionResponse> update(@PathVariable("id") UUID id,
                                                    @RequestBody @Valid UpdateTransactionRequest req) {
    return service.update(id, req)
        .map(mapper::toResponse)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
    return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }

  @GetMapping("/by-user/{userId}")
  public Page<TransactionResponse> byUser(@PathVariable UUID userId,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "20") int size) {
    return service.listByUser(userId, page, size).map(mapper::toResponse);
  }

  @GetMapping("/by-account/{accountId}")
  public Page<TransactionResponse> byAccount(@PathVariable("accountId") UUID accountId,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "20") int size) {
    return service.listByAccount(accountId, page, size).map(mapper::toResponse);
  }
}
