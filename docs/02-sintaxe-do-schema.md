# Passo 2: A Sintaxe do Schema ✍️

A Jorm utiliza uma linguagem declarativa própria (ficheiros `.jorm`) que serve como **fonte única de verdade** para a base de dados e para as classes Java geradas. Neste guia vais aprender todos os conceitos necessários para escrever schemas completos.

Abre o ficheiro `.jorm/schema.jorm` e vamos explorar.

---

## 1. O Bloco de Configuração

O bloco `config` define as opções globais do teu projeto: qual a base de dados a usar, onde gerar o código e que framework usar.

```
config {
    database  = "postgresql"  // Ou: mysql
    output    = "src/main/java/generated"
    package   = "com.omeuprojeto.db"
    framework = "spring"      // Opcional: adiciona @Repository ao cliente gerado
}
```

| Chave | Obrigatório | Descrição |
| --- | --- | --- |
| `database` | Sim | Dialeto SQL. Valores: `postgresql` ou `mysql` |
| `output` | Sim | Caminho para a pasta onde o código será gerado |
| `package` | Sim | Package Java dos ficheiros gerados |
| `framework` | Não | Se `spring`, adiciona `@Repository` ao cliente |

---

## 2. Definir Enums

Os enumeradores são mapeados para o tipo `ENUM` no PostgreSQL (ou `VARCHAR` no MySQL) e tornam-se um `enum` nativo no Java.

```
enum Role {
    USER
    ADMIN
    MODERATOR
}
```

O enum gerado em Java será:

```java
public enum Role {
    USER, ADMIN, MODERATOR
}
```

---

## 3. Definir Modelos (Tabelas)

A palavra-chave `model` representa uma tabela na base de dados e um `Record` imutável no Java.

```
model User {
    id        Int      @id @autoincrement
    email     String   @unique
    name      String
    role      Role
    isActive  Boolean
    createdAt DateTime
    posts     [Post]   @relation(fields: [id], references: [authorId])
}

model Post {
    id        Int      @id @autoincrement
    title     String
    content   String
    authorId  Int
    author    User     @relation(fields: [authorId], references: [id])
}
```

---

## 4. Tipos de Dados Suportados

| Tipo Jorm | Tipo Java | Tipo SQL (PostgreSQL) | Tipo SQL (MySQL) |
| --- | --- | --- | --- |
| `Int` | `Integer` | `INTEGER` / `SERIAL` | `INT` / `AUTO_INCREMENT` |
| `String` | `String` | `VARCHAR(255)` | `VARCHAR(255)` |
| `Float` | `Float` | `DOUBLE PRECISION` | `DOUBLE` |
| `Boolean` | `Boolean` | `BOOLEAN` | `TINYINT(1)` |
| `DateTime` | `LocalDateTime` | `TIMESTAMP` | `DATETIME` |

---

## 5. Anotações Disponíveis

| Anotação | Aplica-se a | Descrição |
| --- | --- | --- |
| `@id` | Campo | Define a chave primária da tabela |
| `@autoincrement` | Campo `Int` | A coluna é gerada automaticamente pela BD |
| `@unique` | Campo | Adiciona a restrição `UNIQUE` à coluna |
| `@relation(...)` | Campo de modelo | Define uma chave estrangeira entre modelos |

---

## 6. Relações entre Modelos

As relações são definidas usando `@relation`. O campo que armazena a chave estrangeira usa `fields` e `references` para indicar qual coluna local aponta para qual coluna remota.

```
model Post {
    id       Int    @id @autoincrement
    authorId Int
    author   User   @relation(fields: [authorId], references: [id])
}
```

Neste exemplo, `authorId` é a coluna da chave estrangeira e `id` é a coluna referenciada na tabela `User`.

> **Nota:** O suporte completo a relações bidirecionais e complexas faz parte do roadmap da Jorm.
> 

---

## 7. Exemplo Completo de Schema

Um schema real com config, enum, e dois modelos relacionados:

```
config {
    database  = "postgresql"
    output    = "src/main/java/generated"
    package   = "com.meuprojeto.db"
    framework = "spring"
}

enum Role {
    USER
    ADMIN
}

model User {
    id        Int      @id @autoincrement
    email     String   @unique
    name      String
    role      Role
    isActive  Boolean
    createdAt DateTime
    posts     [Post]   @relation(fields: [id], references: [authorId])
}

model Post {
    id        Int    @id @autoincrement
    title     String
    content   String
    authorId  Int
    author    User   @relation(fields: [authorId], references: [id])
}
```

---

## Próximos Passos

Agora que o teu schema está escrito, avança para o **Passo 3** para gerar o código Java e criar as tabelas na base de dados.