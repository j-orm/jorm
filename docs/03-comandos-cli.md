The Jorm CLI provides all the necessary commands to synchronize your `schema.jorm` with the Java code and the database. In this guide, you will learn about each command in detail.

---

## 1. Generating Java Code

Whenever you change the `schema.jorm` file, you must regenerate the Java client:

```bash
jorm generate
```

This command reads your schema and creates the following files in the folder defined in `output` (by default `src/main/java/generated`):

- **Java 21 Records** for each defined `model` (e.g., `User.java`, `Post.java`)
- **Java Enums** for each defined `enum` (e.g., `Role.java`)
- **`Jorm.java` Class:** the main client with the Fluent API to perform queries

Example of a generated Record for the `User` model:

```java
public record User(
    Integer id,
    String email,
    String name,
    Role role,
    Boolean isActive,
    LocalDateTime createdAt
) {}
```

---

## 2. Database Migrations

Migrations translate your models into actual SQL and synchronize the database. All migration commands use the `DATABASE_URL` environment variable to connect.

### Configuring DATABASE_URL

Set the environment variable before running any migration command:

**PostgreSQL:**

```bash
export DATABASE_URL="postgresql://user:password@localhost:5432/database_name"
```

**MySQL:**

```bash
export DATABASE_URL="mysql://user:password@localhost:3306/database_name"
```

---

### 2.1. Development Migration

To create the SQL script and apply it immediately to the database:

```bash
jorm migrate dev
```

What this command does:

1. Reads `schema.jorm`
2. Compares it with the current database state via JDBC metadata
3. Generates the necessary SQL (`CREATE TABLE`, `ALTER TABLE`, etc.)
4. Applies the changes to the database
5. Saves the SQL file in `.jorm/migrations/` with the current date (e.g., `001_20240518_add_user_table.sql`)

---

### 2.2. Checking Migration Status

To see which migrations have been applied and which are pending:

```bash
jorm migrate status
```

The output indicates:

- Applied migrations (marked as `applied`)
- Pending migrations (marked as `pending`)

---

### 2.3. Applying in Production

In a production environment, where `.sql` files have already been generated on another machine or CI/CD pipeline, use this command to apply only the pending migrations:

```bash
jorm migrate deploy
```

This command never generates new SQL. It only applies the `.sql` files that haven't been executed yet.

---

### 2.4. Resetting the Database (Development Only)

To drop all tables and start from scratch:

```bash
jorm migrate reset
```

> **Warning:** This command is destructive and deletes all data. Use it only in development environments.

---

## 3. Commands Summary

| Command | Description |
| --- | --- |
| `jorm init` | Initializes a new Jorm project in the current folder |
| `jorm generate` | Generates the Records and the Java client from the schema |
| `jorm migrate dev` | Generates SQL and applies migrations (development environment) |
| `jorm migrate status` | Shows the status of each migration (applied / pending) |
| `jorm migrate deploy` | Applies only pending migrations (production environment) |
| `jorm migrate reset` | Drops all tables and data (development only) |

---

## Next Steps

With the code generated and the database ready, proceed to **Step 4** if you are using Spring Boot.