# finance-microservices-starter

Spring Boot microservices starter for a **Personal Finance Dashboard** (interview-ready).

## Modules
- **discovery** — Eureka server (8761)
- **gateway** — Spring Cloud Gateway (8080)
- **common** — shared DTOs (events)
- **user-service** (9001)
- **account-service** (9002)
- **transaction-service** (9003)
- **budget-service** (9004)
- **rules-service** (9005)
- **import-service** (9006)
- **analytics-service** (9007)
- **notification-service** (9008)

## Infra (docker-compose)
- Kafka/ZooKeeper, Postgres, Redis, MinIO

```bash
# 1) Start infra
docker compose up -d

# 2) Build everything
mvn -q -T 1C clean install

# 3) Run discovery (Eureka)
mvn -pl discovery spring-boot:run

# 4) In new terminals, run gateway + any services you want
mvn -pl gateway spring-boot:run
mvn -pl user-service spring-boot:run
mvn -pl account-service spring-boot:run
# ... etc
```

## Test a service via gateway
- Gateway: http://localhost:8080
- Discovery UI: http://localhost:8761

```bash
curl http://localhost:8080/api/users/health
curl http://localhost:8080/api/accounts/health
curl http://localhost:8080/api/transactions/health
```

## Next steps
- Wire OAuth2 (Keycloak) to gateway + services (resource servers).
- Add Postgres/Flyway per service (own schemas/tables).
- Add Kafka producers/consumers (`common` event models).
- Implement domain endpoints (CRUD, pagination) and analytics.
