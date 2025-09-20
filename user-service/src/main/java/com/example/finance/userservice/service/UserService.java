package com.example.finance.userservice.service;

import com.example.finance.userservice.domain.User;
import com.example.finance.userservice.dto.CreateUserRequest;
import com.example.finance.userservice.dto.UpdateUserRequest;
import com.example.finance.userservice.repo.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository repo;

  public Optional<User> find(UUID id) { return repo.findById(id); }

  @Transactional
  public User upsertFromAuth(UUID id, String email, String name) {
    return repo.findById(id).map(u -> {
      if (email != null) u.setEmail(email);
      if (name != null) u.setName(name);
      return repo.save(u);
    }).orElseGet(() -> repo.save(User.builder().id(id).email(email).name(name).build()));
  }

  @Transactional
  public User create(UUID id, CreateUserRequest req) {
    User u = User.builder()
      .id(id)
      .email(req.email())
      .name(req.name())
      .locale(req.locale())
      .currency(req.currency() == null ? "USD" : req.currency())
      .timezone(req.timezone())
      .featureFlagsJson(req.featureFlagsJson())
      .build();
    return repo.save(u);
  }

  @Transactional
  public Optional<User> update(UUID id, UpdateUserRequest req) {
    return repo.findById(id).map(u -> {
      if (req.name() != null) u.setName(req.name());
      if (req.locale() != null) u.setLocale(req.locale());
      if (req.currency() != null) u.setCurrency(req.currency());
      if (req.timezone() != null) u.setTimezone(req.timezone());
      if (req.featureFlagsJson() != null) u.setFeatureFlagsJson(req.featureFlagsJson());
      return repo.save(u);
    });
  }
}
