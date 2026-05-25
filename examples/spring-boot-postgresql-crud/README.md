# Jorm Example: Spring Boot + PostgreSQL CRUD

This example is a minimal Spring Boot REST API that uses Jorm for database access and schema migrations.

## Prerequisites

- Java 21+
- Docker + Docker Compose
- Maven
- Jorm CLI installed and available in your PATH

## 1. Start PostgreSQL

From this folder:

```bash
docker compose up -d
```

## 2. Apply migrations with Jorm

Set `DATABASE_URL` and apply the migration scripts in `.jorm/migrations`.

macOS/Linux:

```bash
export DATABASE_URL="postgresql://jorm:jorm@localhost:5432/jorm_example"
jorm migrate deploy
```

Windows (PowerShell):

```powershell
$env:DATABASE_URL = "postgresql://jorm:jorm@localhost:5432/jorm_example"
jorm migrate deploy
```

## 3. Run the application

```bash
mvn spring-boot:run
```

The API is available at `http://localhost:8080`.

## API endpoints

- `GET /api/users`
- `GET /api/users/{id}`
- `POST /api/users`
- `PUT /api/users/{id}`
- `DELETE /api/users/{id}`

Example request body for `POST /api/users`:

```json
{
  "name": "Alice",
  "email": "alice@example.com"
}
```

## Jorm schema

The schema lives in `.jorm/schema.jorm` and generates Java code into `src/main/java/generated`.
