# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Run the application
./mvnw spring-boot:run

# Build (skip tests)
./mvnw package -DskipTests

# Run all tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=InventoryApplicationTests

# Clean build
./mvnw clean package
```

## Architecture

Spring Boot 3 REST API using Java 21, backed by an in-memory H2 database via Spring Data JPA.

- **Entry point**: `src/main/java/com/example/inventory/InventoryApplication.java`
- **Config**: `src/main/resources/application.properties`
- **H2 console** (dev only): `http://localhost:8080/h2-console` — JDBC URL `jdbc:h2:mem:inventorydb`

### Conventions

- Package root: `com.example.inventory`
- Layers go in sub-packages: `.model`, `.repository`, `.service`, `.controller`
- JPA schema is managed by Hibernate (`ddl-auto=create-drop`); add `schema.sql` / `data.sql` under `src/main/resources` for seeding
