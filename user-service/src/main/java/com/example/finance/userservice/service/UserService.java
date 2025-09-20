package com.example.finance.userservice.service;

import com.example.finance.userservice.domain.User;
import com.example.finance.userservice.dto.CreateUserRequest;
import com.example.finance.userservice.dto.UpdateUserRequest;
import com.example.finance.userservice.repo.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repo;

  @Transactional
  public User create(UUID id, CreateUserRequest req) {
    return repo.findByEmail(req.email())
        .map(u -> { // update existing instead of inserting duplicate
          if (req.name() != null)
            u.setName(req.name());
          if (req.locale() != null)
            u.setLocale(req.locale());
          if (req.currency() != null)
            u.setCurrency(req.currency());
          if (req.timezone() != null)
            u.setTimezone(req.timezone());
          if (req.featureFlagsJson() != null)
            u.setFeatureFlagsJson(req.featureFlagsJson());
          return repo.save(u);
        })
        .orElseGet(() -> repo.save(User.builder()
            .id(id)
            .email(req.email())
            .name(req.name())
            .locale(req.locale())
            .currency(req.currency() == null ? "USD" : req.currency())
            .timezone(req.timezone())
            .featureFlagsJson(req.featureFlagsJson())
            .build()));
  }

  @Transactional
  public User upsertFromAuth(UUID id, String email, String name) {
    // 1) If a user exists with this id, return it
    Optional<User> byId = repo.findById(id);
    if (byId.isPresent()) {
      return byId.get();
    }

    // 2) If a user exists with this email, return it (avoid duplicate-insert)
    Optional<User> byEmail = repo.findByEmail(email);
    if (byEmail.isPresent()) {
      return byEmail.get();
    }

    // 3) Otherwise create a new user with the JWT subject id
    User user = User.builder()
        .id(id)
        .email(email)
        .name(name)
        .currency("USD")
        .build();

    try {
      return repo.save(user);
    } catch (DataIntegrityViolationException ex) {
      // Race: someone inserted the same email concurrently — return that user
      return repo.findByEmail(email).orElseThrow(() -> ex);
    }
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

  @Transactional(readOnly = true)
  public Optional<User> find(UUID id) {
    return repo.findById(id);
  }
}
