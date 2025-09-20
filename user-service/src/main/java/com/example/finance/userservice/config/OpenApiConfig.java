package com.example.finance.userservice.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
  @Bean
  public OpenAPI financeOpenAPI() {
    return new OpenAPI()
        .info(new Info().title("User Service API").version("v1").description("User profile & settings"))
        .externalDocs(new ExternalDocumentation().description("Gateway").url("http://localhost:8080"));
  }
}
