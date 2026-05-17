# Passo 2: A Sintaxe do Schema ✍️

A Jorm utiliza uma linguagem declarativa própria, muito inspirada no Prisma, que serve como fonte de verdade para a base de dados e para as classes Java.

Abra o ficheiro `.jorm/schema.jorm` e vamos explorar os conceitos fundamentais.

## 1. O Bloco de Configuração

O bloco `config` define os detalhes da sua ligação e do código a gerar.

```jorm
config {
    database = "postgresql" // Opções: postgresql, mysql
    output = "src/main/java/generated"
    package = "com.omeuprojeto.db"
    framework = "spring" // Opcional: Adiciona anotações @Repository
}
```

## 2. Definir Enums

Os enumeradores são mapeados para `ENUM` no PostgreSQL ou `VARCHAR/ENUM` no MySQL e tornam-se `enum` no Java.

```jorm
enum Role {
    USER
    ADMIN
    MODERATOR
}
```

## 3. Definir Modelos (Tabelas)

A palavra-chave `model` representa uma tabela na base de dados e um `Record` no Java.

```jorm
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

### Tipos de Dados Suportados
* `Int`: Mapeia para `Integer` no Java e `INTEGER` (ou `SERIAL`) no SQL.
* `String`: Mapeia para `String` no Java e `VARCHAR(255)` no SQL.
* `Float`: Mapeia para `Float` no Java e `DOUBLE PRECISION` no SQL.
* `Boolean`: Mapeia para `Boolean` no Java e `BOOLEAN` no SQL.
* `DateTime`: Mapeia para `LocalDateTime` no Java e `TIMESTAMP` no SQL.

### Anotações Disponíveis
* `@id`: Define a Chave Primária.
* `@autoincrement`: Define que a coluna é gerada automaticamente.
* `@unique`: Adiciona a restrição `UNIQUE` à coluna.
* `@relation`: Define chaves estrangeiras entre modelos.

## 4. O que acontece a seguir?

Depois de escrever o schema, está pronto para gerar o código e criar as tabelas. Avance para o **Passo 3**.
