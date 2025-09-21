package com.example.finance.accountservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable());
    http.authorizeHttpRequests(auth -> auth
        .requestMatchers("/api/accounts/health", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/accounts/**").authenticated()
        .requestMatchers(HttpMethod.POST, "/api/accounts/**").authenticated()
        .requestMatchers(HttpMethod.PATCH, "/api/accounts/**").authenticated()
        .requestMatchers(HttpMethod.DELETE, "/api/accounts/**").authenticated()
        .anyRequest().authenticated()
    );
    http.oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()));
    return http.build();
  }
}
