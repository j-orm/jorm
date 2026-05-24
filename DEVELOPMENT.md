# Development Guide

This document explains how to develop and contribute to Jorm in the main repository.

## Requirements

- Java 21 (JDK)
- Git
- Maven 3.9+ (recommended), or the Maven bundled in the repository (`apache-maven-3.9.6`)
- Docker (optional, useful for database tests)

## Repository structure

This is a Maven multi-module repository:

- `core`: shared models and types
- `parser`: ANTLR grammar and schema parser
- `generator`: Java and SQL generation
- `client`: client runtime
- `db`: database adapters and migration runner
- `cli`: CLI (also builds the standalone jar)
- `jorm-spring-boot-starter`: Spring Boot integration

## Local setup

```bash
git clone https://github.com/j-orm/jorm.git
cd jorm
```

### Windows Java note

If Maven fails with `JAVA_HOME not found`, set `JAVA_HOME` to your JDK.

## Build

### Full build

```bash
mvn -DskipTests package
```

### Build the standalone CLI

```bash
mvn -pl cli -am -DskipTests package
```

The generated jar is located at:

- `cli/target/jorm-cli-standalone.jar`

## Run the CLI locally

```bash
java -jar cli/target/jorm-cli-standalone.jar --help
```

## Tests

```bash
mvn test
```

## Working on schema and generation

To test code generation, use the example project in `src/main/java/generated` or create a local test project with:

```bash
jorm init
jorm validate
jorm generate
```

## Code conventions

- Code and identifiers in English.
- Avoid reflection at runtime.
- Prefer immutability.
- Keep PRs small and focused.
- Commit messages in English using Conventional Commits.

## Releases

The release process is automated via GitHub Actions:

1. Make sure `main` is green.
2. Create an annotated tag `vX.Y.Z` on the commit you want to release, then push the tag.
3. The workflow `.github/workflows/release.yml` creates the GitHub Release and uploads the jar.
4. The workflow `.github/workflows/publish-maven.yml` publishes to Maven Central when the release is published.

Note: Maven Central is immutable. For fixes, always publish a new version.
