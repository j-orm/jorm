<div align="center">
  <img src="https://raw.githubusercontent.com/j-orm/jorm/main/docs/assets/jorm-logo.png" alt="Jorm Logo" width="200" />
  
  # Welcome to Jorm! 🚀

  **The Modern, Schema-First, Reflection-Free ORM for Java 21+**

  [![Maven Central](https://img.shields.io/maven-central/v/pt.jorm/jorm-spring-boot-starter?color=blue&label=Maven%20Central)](https://central.sonatype.com/namespace/pt.jorm)
  [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
  [![Java 21+](https://img.shields.io/badge/Java-21%2B-ED8B00?logo=java)](https://adoptium.net/)

  [Documentation](https://github.com/j-orm/jorm/tree/main/docs) • [Getting Started](https://github.com/j-orm/jorm#quick-start) • [Spring Boot Guide](https://github.com/j-orm/jorm/blob/main/docs/04-integracao-spring-boot.md)
</div>

---

## 🌟 What is Jorm?

Jorm was born from a simple idea: **Java developers deserve the same elegant, type-safe, and schema-driven database experience as other modern ecosystems (like Prisma), but with zero runtime reflection overhead.**

By leveraging Java 21 Records and compile-time code generation, Jorm provides blazing-fast database access, strictly typed clients, and a powerful CLI to manage your schema and migrations.

### ✨ Core Philosophy

* 🏗️ **Schema-First:** Define your entire data model in a clean, readable `.jorm` file. Your schema is the single source of truth.
* ⚡ **Zero Reflection:** No more slow startup times or runtime proxy magic. Jorm generates pure, native Java code.
* 🛡️ **100% Type-Safe:** Catch database errors at compile-time. If your schema changes, your code breaks immediately—saving you from production bugs.
* 🍃 **Spring Boot Native:** First-class support for Spring Boot via our dedicated starter.

---

## 🛠️ The Jorm Ecosystem

This organization houses the core Jorm projects:

### 📦 [j-orm/jorm](https://github.com/j-orm/jorm)
The heart of the ecosystem. It contains:
- The **Jorm CLI** (`jorm init`, `jorm generate`, `jorm migrate`)
- The **ANTLR4 Parser** for the `.jorm` syntax
- The **Code Generator** (powered by JavaPoet)
- Native database adapters for **PostgreSQL** and **MySQL**
- The **Spring Boot Starter** (`jorm-spring-boot-starter`)

---

## 🚀 Quick Taste

**1. Define your schema (`schema.jorm`)**
```jorm
config {
    database.url = "jdbc:postgresql://localhost:5432/mydb"
    database.driver = "org.postgresql.Driver"
}

model User {
    @id
    id: UUID
    name: String
    email: String @unique
    createdAt: LocalDateTime
}
```

**2. Generate the Client**
```bash
jorm generate
```

**3. Use it in Spring Boot**
```java
@Service
public class UserService {
    private final UserClient userClient;

    public UserService(UserClient userClient) {
        this.userClient = userClient;
    }

    public void createUser() {
        User user = new User(UUID.randomNonNull(), "Alice", "alice@example.com", LocalDateTime.now());
        userClient.insert(user);
    }
}
```

---

## 🤝 Join the Community

We are always looking for contributors, feedback, and ideas! 
- Found a bug? [Open an issue](https://github.com/j-orm/jorm/issues).
- Want to contribute? Check out our [Development Guide](https://github.com/j-orm/jorm/blob/main/DEVELOPMENT.md).
- Have a feature request? Start a [Discussion](https://github.com/j-orm/jorm/discussions).

<div align="center">
  <i>Built with ❤️ for the Java community.</i>
</div>
