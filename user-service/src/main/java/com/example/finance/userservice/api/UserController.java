package com.example.finance.userservice.api;

import com.example.finance.userservice.dto.CreateUserRequest;
import com.example.finance.userservice.dto.UpdateUserRequest;
import com.example.finance.userservice.dto.UserDto;
import com.example.finance.userservice.mapper.UserMapper;
import com.example.finance.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService service;
  private final UserMapper mapper;

  @GetMapping("/health")
  public Map<String, Object> health() {
    return Map.of("status","UP","service","user-service");
  }

  @GetMapping("/me")
  public ResponseEntity<UserDto> me(@AuthenticationPrincipal Jwt jwt) {
    UUID id = UUID.fromString(jwt.getClaim("sub"));
    return service.find(id).map(u -> ResponseEntity.ok(mapper.toDto(u)))
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<UserDto> create(@RequestParam(required=false) UUID id,
                                        @Validated @RequestBody CreateUserRequest req) {
    UUID uid = id != null ? id : UUID.randomUUID();
    return ResponseEntity.ok(mapper.toDto(service.create(uid, req)));
  }

  @PatchMapping("/me")
  public ResponseEntity<UserDto> updateMe(@AuthenticationPrincipal Jwt jwt,
                                          @Validated @RequestBody UpdateUserRequest req) {
    UUID id = UUID.fromString(jwt.getClaim("sub"));
    return service.update(id, req)
        .map(u -> ResponseEntity.ok(mapper.toDto(u)))
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/admin/{id}")
  public ResponseEntity<UserDto> getById(@PathVariable UUID id) {
    return service.find(id).map(u -> ResponseEntity.ok(mapper.toDto(u)))
        .orElse(ResponseEntity.notFound().build());
  }
}
