package com.example.finance.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class SecurityConfig {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable());
    http.authorizeHttpRequests(auth -> auth
        .requestMatchers("/api/users/health", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
        .requestMatchers("/api/users/admin/**").hasRole("admin")
        .anyRequest().authenticated()
    );
    http.oauth2ResourceServer(oauth -> oauth.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter())));
    return http.build();
  }

  @Bean
  public JwtAuthenticationConverter jwtAuthConverter() {
    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(this::extractRealmRoles);
    return converter;
  }

  private java.util.Collection<GrantedAuthority> extractRealmRoles(Jwt jwt) {
    Map<String, Object> realmAccess = jwt.getClaim("realm_access");
    java.util.List<String> roles = realmAccess != null ? (java.util.List<String>) realmAccess.getOrDefault("roles", java.util.List.of()) : java.util.List.of();
  return roles.stream()
    .map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r)
    .map(r -> (GrantedAuthority) new SimpleGrantedAuthority(r))
    .collect(Collectors.toList());
  }
}
