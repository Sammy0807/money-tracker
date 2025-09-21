package com.example.finance.accountservice.api;

import com.example.finance.accountservice.dto.*;
import com.example.finance.accountservice.mapper.AccountMapper;
import com.example.finance.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
  private final AccountService service;
  private final AccountMapper mapper;

  @GetMapping("/health")
  public String health() { return "ok"; }

  @GetMapping
  public ResponseEntity<?> list(@AuthenticationPrincipal Jwt jwt,
                                @RequestParam(defaultValue="0") int page,
                                @RequestParam(defaultValue="20") int size) {
    var userId = UUID.fromString(jwt.getClaim("sub"));
    var res = service.mine(userId, PageRequest.of(page, size)).map(mapper::toDto);
    return ResponseEntity.ok(res);
  }

  @PostMapping
  public ResponseEntity<?> create(@AuthenticationPrincipal Jwt jwt,
                                  @Valid @RequestBody CreateAccountRequest req) {
    var userId = UUID.fromString(jwt.getClaim("sub"));
    var saved = service.createOrRestore(userId, req);
    return ResponseEntity.ok(mapper.toDto(saved));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> get(@AuthenticationPrincipal Jwt jwt, @PathVariable UUID id) {
    var userId = UUID.fromString(jwt.getClaim("sub"));
    return service.findOwned(userId, id)
        .map(a -> ResponseEntity.ok(mapper.toDto(a)))
        .orElse(ResponseEntity.notFound().build());
  }

  @PatchMapping("/{id}")
  public ResponseEntity<?> update(@AuthenticationPrincipal Jwt jwt,
                                  @PathVariable UUID id,
                                  @RequestBody UpdateAccountRequest req) {
    var userId = UUID.fromString(jwt.getClaim("sub"));
    return service.update(userId, id, req)
        .map(a -> ResponseEntity.ok(mapper.toDto(a)))
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@AuthenticationPrincipal Jwt jwt, @PathVariable UUID id) {
    var userId = UUID.fromString(jwt.getClaim("sub"));
    return service.softDelete(userId, id) ? ResponseEntity.noContent().build()
                                          : ResponseEntity.notFound().build();
  }
}
