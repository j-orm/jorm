# Guia de Desenvolvimento

Este documento descreve como desenvolver e contribuir para o Jorm no repositório principal.

## Requisitos

- Java 21 (JDK)
- Git
- Maven 3.9+ (recomendado) ou o Maven incluído no repositório (`apache-maven-3.9.6`)
- Docker (opcional, útil para testes com bases de dados)

## Estrutura do repositório

O repositório é um multi-módulo Maven:

- `core`: modelos e tipos partilhados
- `parser`: gramática ANTLR e parser do schema
- `generator`: geração de Java e SQL
- `client`: runtime do cliente
- `db`: adapters e runner de migrations
- `cli`: CLI (gera também o jar standalone)
- `jorm-spring-boot-starter`: integração Spring Boot

## Setup local

```bash
git clone https://github.com/j-orm/jorm.git
cd jorm
```

### Java no Windows (nota)

Se o Maven falhar com `JAVA_HOME not found`, define o `JAVA_HOME` para o teu JDK.

## Build

### Build completo (tudo)

```bash
mvn -DskipTests package
```

### Build da CLI standalone

```bash
mvn -pl cli -am -DskipTests package
```

O jar gerado fica em:

- `cli/target/jorm-cli-standalone.jar`

## Executar a CLI localmente

```bash
java -jar cli/target/jorm-cli-standalone.jar --help
```

## Testes

```bash
mvn test
```

## Trabalhar no schema e geração

Para testar a geração de código, usa o projecto de exemplo em `src/main/java/generated` ou cria um projecto de teste local com:

```bash
jorm init
jorm validate
jorm generate
```

## Convenções de código

- Código e identificadores em inglês.
- Evitar reflexão no runtime.
- Preferir imutabilidade.
- Alterações pequenas e focadas por PR.
- Mensagens de commit em inglês e no formato Conventional Commits.

## Releases

O processo de release é automatizado por GitHub Actions:

1. Garantir que `main` está verde.
2. Criar tag anotada `vX.Y.Z` no commit a lançar e fazer push da tag.
3. O workflow [release.yml](file:///c:/Projects/jorm-project/jorm/.github/workflows/release.yml) cria a GitHub Release e faz upload do jar.
4. O workflow [publish-maven.yml](file:///c:/Projects/jorm-project/jorm/.github/workflows/publish-maven.yml) publica no Maven Central quando a release é publicada.

Nota: o Maven Central é imutável. Para correcções, publica sempre uma versão nova.
