<div align="center">
  <h1>🚀 Jorm</h1>
  <p><strong>The Modern, Schema-First, and Reflection-Free ORM for the Java Ecosystem</strong></p>
  
  [![Status](https://img.shields.io/badge/status-BETA-orange.svg)]()
  [![Recommended (0.1.x)](https://img.shields.io/maven-central/v/pt.jorm/jorm-spring-boot-starter?color=blue&label=Recommended%20%280.1.x%29&versionPrefix=0.1)](https://central.sonatype.com/namespace/pt.jorm)
  [![Highest (all versions)](https://img.shields.io/maven-central/v/pt.jorm/jorm-spring-boot-starter?color=blue&label=Highest%20%28all%20versions%29)](https://central.sonatype.com/namespace/pt.jorm)
  [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
  [![Java 21](https://img.shields.io/badge/Java-21%2B-blue.svg)]()
</div>

<br/>

> ⚠️ **Warning:** Jorm is currently in **BETA**. The project is functional, but the API may undergo changes. We appreciate your feedback and contributions!

The Java ecosystem needed a breath of fresh air when interacting with databases. Inspired by the simplicity of Prisma (in the TypeScript world), **Jorm** is an ORM designed for the era of Java 21 and GraalVM. 

---

## 💡 What Does Jorm Solve?

For decades, the Java ecosystem has relied on heavy ORMs (like Hibernate or JPA) that require dozens of annotations, mutable classes, and rely heavily on *reflection*. This results in slow startup times, excessive memory usage, and difficulty compiling natively for GraalVM.

Jorm solves this problem by completely shifting the paradigm: **Schema-First and native code generation.**

### ❌ How it used to be (JPA / Hibernate)
To create a simple users table, the code was polluted with annotations and boilerplate:

```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    // + Getters, Setters, Empty Constructors, equals() and hashCode() ...
}
```

### ✅ How it is now (with Jorm)
Just write the structure of your database in a simple and elegant declarative file:

```jorm
model User {
    id    Int    @id @autoincrement
    email String @unique
    name  String
}
```

**The result?** Jorm reads this file and automatically generates a Java 21 **Record** (100% immutable) and a database client that maps the data manually. No annotations (`@Entity`, `@Column`), no *reflection*, with maximum performance and pure static typing!

---

## 🔄 How Do Migrations Work?

Instead of writing complex SQL scripts manually or using external tools (like Flyway or Liquibase), Jorm handles the entire lifecycle of your database.

When you run the development command:

```bash
jorm migrate dev
```

Jorm executes the following flow automatically and transparently:
1. **Reads** the `schema.jorm` file.
2. **Compares** your model with the current database state (using JDBC metadata).
3. **Generates** the necessary SQL automatically (`CREATE TABLE`, `ALTER TABLE`, etc.).
4. **Applies** the changes immediately to the connected database.
5. **Saves** the history in a local file (example: `migrations/001_add_user_table.sql`) for version control and future deployment to production.

---

## ✨ Current Features

The BETA version already includes the foundational pillars to start developing:

* 📝 **Schema-First:** A `.jorm` file as the single source of truth.
* ⚡ **Zero Reflection:** Client generated with `JavaPoet` and transparent manual mapping. Perfect for native compilation with GraalVM.
* 📦 **Java 21 Records:** All generated models use the native `Records` feature for immutability and conciseness.
* 🗄️ **Multi-Dialect Support:** Out-of-the-box integration with **PostgreSQL** and **MySQL**.
* 🛠️ **Powerful CLI:** Commands to initialize, generate code, and run migrations autonomously.
* 🌱 **Spring Boot Starter:** Automatic configuration and dependency injection for Spring projects.
* 🔍 **Fluent API:** Intuitive queries with autocomplete in your IDE, no need to write manual SQL or JPQL.

---

## 🔮 What's Next (Roadmap)

We have ambitious plans to make Jorm the go-to ORM. Here are the features planned for upcoming versions:

* 🔄 **Complex Relations:** Full bidirectional support for One-to-One, One-to-Many, and Many-to-Many relationships.
* 🍃 **Quarkus & Micronaut Support:** Native starters for reactive frameworks.
* 📊 **Jorm Studio:** A graphical browser interface to view and edit data directly from the schema file.
* 🐘 **MongoDB / NoSQL:** Parser extension to support non-relational databases.
* 🚀 **Advanced Migrations:** Automatic schema diffs to generate precise rollback SQL scripts.

---

## 🚀 Quick Installation

You can install the Jorm CLI using your preferred package manager:

**macOS / Linux (Homebrew)**
```bash
brew tap j-orm/jorm
brew install jorm
```

**Windows (Scoop)**
```powershell
scoop bucket add jorm https://github.com/j-orm/jorm
scoop install jorm
```

**Universal Installation**

**On macOS or Linux (Bash/Zsh):**
```bash
curl -sSL https://raw.githubusercontent.com/j-orm/jorm/main/install.sh | bash
```

**On Windows (PowerShell):**
```powershell
Invoke-WebRequest -Uri "https://raw.githubusercontent.com/j-orm/jorm/main/install.ps1" -OutFile "install.ps1"; .\install.ps1; Remove-Item install.ps1
```

**On Windows (Command Prompt / CMD):**
```cmd
powershell -Command "Invoke-WebRequest -Uri 'https://raw.githubusercontent.com/j-orm/jorm/main/install.ps1' -OutFile 'install.ps1'; .\install.ps1; Remove-Item install.ps1"
```

### ⚙️ Configure PATH

If you used the **Universal Installation**, you need to add the Jorm folder to your system's environment variables so the `jorm` command is recognized in any terminal.

**On macOS or Linux (Bash/Zsh):**
```bash
export PATH="$HOME/.jorm/bin:$PATH"
```

**On Windows (PowerShell):**
```powershell
$env:PATH += ";$HOME\.jorm\bin"
```

**On Windows (Command Prompt / CMD):**
```cmd
set PATH=%PATH%;%USERPROFILE%\.jorm\bin
```

> 💡 **Tip:** Package managers like **Homebrew** and **Scoop** already handle this configuration automatically for you!

---

## 📚 Comprehensive Documentation

We have prepared a detailed step-by-step guide in the `docs/` folder to help you get the most out of Jorm. Explore the chapters:

1. [Installation and Initialization](docs/01-instalacao.md)
2. [Schema Syntax](docs/02-sintaxe-do-schema.md)
3. [CLI Commands and Migrations](docs/03-comandos-cli.md)
4. [Spring Boot Integration](docs/04-integracao-spring-boot.md)

## Examples

- [Spring Boot + PostgreSQL CRUD](examples/spring-boot-postgresql-crud/README.md)

---

## 🤝 How to Contribute

Being an open-source project, all help is welcome! If you found a bug, have an idea for a feature, or want to fix documentation:

1. Fork the repository.
2. Create a branch for your feature (`git checkout -b feature/my-idea`).
3. Follow the **Semantic Commits** convention (e.g., `feat: add support for uuid types`).
4. Open a Pull Request.

## 📄 License

This project is licensed under the **MIT** license. See the [LICENSE](LICENSE) file for more details.
