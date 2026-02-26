# Inventory API

A lightweight RESTful API for managing product inventory, built with Spring Boot 3 and backed by an in-memory H2 database.

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.2 |
| Persistence | Spring Data JPA + Hibernate |
| Database | H2 (in-memory) |
| Build | Maven |

## Getting Started

### Prerequisites

- Java 21+
- Maven (or use the included `./mvnw` wrapper)

### Run the application

```bash
./mvnw spring-boot:run
```

The server starts on `http://localhost:8080`.

### H2 Console

An in-browser SQL console is available at `http://localhost:8080/h2-console` while the app is running.

| Field | Value |
|---|---|
| JDBC URL | `jdbc:h2:mem:inventorydb` |
| Username | `sa` |
| Password | _(leave blank)_ |

## API Reference

### Create a product

```bash
curl -s -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{"name": "Widget", "price": 9.99, "quantity": 100}'
```

**Response — `201 Created`**
```json
{
  "id": 1,
  "name": "Widget",
  "price": 9.99,
  "quantity": 100
}
```

### Delete a product

```bash
curl -s -o /dev/null -w "%{http_code}" \
  -X DELETE http://localhost:8080/api/products/1
```

**Response — `204 No Content`**

If the product does not exist, returns `404 Not Found`.

## Project Structure

```
src/
├── main/
│   ├── java/com/example/inventory/
│   │   ├── InventoryApplication.java   # Entry point
│   │   ├── controller/
│   │   │   └── ProductController.java  # REST endpoints
│   │   ├── model/
│   │   │   └── Product.java            # JPA entity
│   │   └── repository/
│   │       └── ProductRepository.java  # Spring Data repository
│   └── resources/
│       └── application.properties      # App + datasource config
└── test/
    └── java/com/example/inventory/
        └── InventoryApplicationTests.java
```
