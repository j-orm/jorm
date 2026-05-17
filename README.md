# Jorm: Um ORM Moderno para Java

## 1. O que é o Jorm?

Jorm é um Object-Relational Mapper (ORM) moderno e eficiente para o ecossistema Java, projetado para simplificar a interação com bases de dados. Ele adota uma abordagem *schema-first*, onde a definição do seu modelo de dados é a fonte única de verdade, a partir da qual todo o código necessário para a gestão da base de dados e acesso aos dados é gerado automaticamente. O objetivo principal do Jorm é eliminar o *boilerplate* e a complexidade associada ao desenvolvimento de aplicações Java persistentes, proporcionando uma experiência de desenvolvimento (DX) superior.

### Filosofia Central

> O schema é a fonte da verdade. Tudo deriva dele.

Esta filosofia garante consistência, reduz erros e permite que os desenvolvedores se concentrem na lógica de negócio, em vez de detalhes de infraestrutura de dados.

## 2. Problema que Resolve

No desenvolvimento Java tradicional, a modelagem de entidades e a interação com bases de dados frequentemente envolvem uma quantidade significativa de código repetitivo e configurações complexas. Para uma entidade simples, como um `User`, um desenvolvedor pode precisar de:

- Uma classe de entidade com múltiplas anotações (`@Entity`, `@Table`, `@Id`, `@Column`, `@GeneratedValue`).
- Anotações de bibliotecas como Lombok (`@Data`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`) para reduzir o *boilerplate* da própria classe.
- Uma interface de repositório (`UserRepository`) estendendo interfaces de frameworks de persistência (`JpaRepository`).
- Objetos de Transferência de Dados (DTOs) e mappers para converter entre entidades e DTOs.
- Scripts SQL de migration geridos manualmente ou através de ferramentas de migration configuradas separadamente.

Todo este esforço é necessário antes mesmo de se começar a escrever a lógica de negócio. O Jorm resolve este problema ao permitir que o desenvolvedor defina o modelo de dados de forma concisa num arquivo `.jorm`, e então gera automaticamente todo o código Java e scripts SQL necessários, simplificando drasticamente o processo.

## 3. Arquitetura do Produto

A arquitetura do Jorm é composta por três componentes principais que trabalham em conjunto para fornecer uma solução completa de acesso a dados:

```jsx
+------------------+       +----------------------+
|   jorm CLI       |  -->  |  schema.jorm         |
|  (terminal)      |       |  (fonte da verdade)  |
+------------------+       +----------------------+
         |                          |
         v                          v
+------------------+       +----------------------+
|  jorm generate   |  -->  |  User.java           |
|  jorm migrate    |       |  UserClient.java     |
+------------------+       |  Migration_001.sql   |
                           +----------------------+
                                    |
                        +-----------+-----------+
                        |                       |
                        v                       v
               +-----------------+   +--------------------+
               |  jorm-client    |   |  jorm-runtime      |
               |  API fluente    |   |  conexão com banco |
               |  create, find,  |   |  execução de       |
               |  update, delete |   |  queries e migr.   |
               +-----------------+   +--------------------+
```

- **jorm CLI**: A ferramenta de linha de comando utilizada pelo desenvolvedor no terminal. É responsável por inicializar projetos, gerar código, gerenciar migrations e outras tarefas administrativas. A CLI opera fora da aplicação em tempo de execução.
- **jorm-client**: Uma API fluente e fortemente tipada, gerada automaticamente a partir do schema `.jorm`. É a interface que o desenvolvedor utiliza diretamente no código Java para realizar operações CRUD (Create, Read, Update, Delete). Cada modelo definido no schema obtém o seu próprio cliente com métodos totalmente tipados, eliminando a necessidade de "strings mágicas" ou *casting*.
- **jorm-runtime**: Uma biblioteca leve que reside dentro do projeto Java do utilizador e fornece a infraestrutura de tempo de execução para o `jorm-client`. É responsável por mapear modelos para a base de dados, executar queries, gerenciar conexões e aplicar migrations de forma eficiente.

É importante notar que a `jorm CLI` não faz parte da aplicação final em produção. Apenas o `jorm-client`, o `jorm-runtime` e o código gerado são incluídos no *build* da aplicação.

## 4. Fluxo de Uso

O fluxo de trabalho com o Jorm é projetado para ser intuitivo e eficiente:

### 4.1. Instalação

O Jorm CLI pode ser instalado de várias maneiras, dependendo do sistema operativo e das preferências do desenvolvedor:

```bash
# Mac e Linux
brew install jorm

# Windows
scoop install jorm

# Desenvolvedores Java com SDKMAN
sdk install jorm

# Instalação Universal
curl -sSL https://jorm.dev/install.sh | sh
```

*(Nota: Como o Jorm ainda está em desenvolvimento inicial, pode testar o executável localmente fazendo clone do repositório, executando `mvn clean package` e usando o ficheiro `cli/target/jorm-cli-standalone.jar` gerado.)*

### 4.2. Inicialização do Projeto

Para começar um novo projeto Jorm, basta executar o comando `init`:

```bash
jorm init
```

Este comando cria a estrutura inicial de diretórios e arquivos necessários:

```
meu-projeto/
└── .jorm/
    ├── schema.jorm
    └── migrations/
```

### 4.3. Definição do Schema

O desenvolvedor define os modelos de dados no arquivo `.jorm/schema.jorm` usando uma linguagem declarativa e concisa:

```
config {
  database = "postgresql"
  output   = "src/main/java/generated"
  package  = "com.myapp.db"
}

model User {
  id        Int      @id @autoincrement
  name      String
  email     String   @unique
  createdAt DateTime @default(now)
  role      Role     @default(USER)
  posts     Post[]
}

model Post {
  id      Int     @id @autoincrement
  title   String
  content String?
  author  User    @relation(userId)
  userId  Int
}

enum Role {
  USER
  ADMIN
  MODERATOR
}
```

### 4.4. Geração de Código

Após definir ou modificar o schema, o comando `generate` é usado para criar o código Java correspondente:

```bash
jorm generate
```

Este comando gera as classes de modelo Java, enums e o `jorm-client` tipado com todos os métodos de acesso a dados.

### 4.5. Migrations da Base de Dados

O Jorm oferece um sistema robusto para gerenciar as alterações no schema da base de dados:

```bash
# Cria e aplica uma nova migration no ambiente de desenvolvimento
jorm migrate dev

# Verifica o estado atual das migrations
jorm migrate status

# Aplica migrations pendentes em produção
jorm migrate deploy
```

## 5. Funcionalidades Detalhadas

### 5.1. Jorm CLI

A ferramenta de linha de comando do Jorm oferece os seguintes comandos:

- `jorm init`: Inicializa um novo projeto Jorm no diretório atual, criando a estrutura `.jorm/`.
- `jorm generate`: Lê o arquivo `schema.jorm` e gera os arquivos Java correspondentes (modelos, enums, `jorm-client`).
- `jorm migrate dev`: Gera um novo script de migration SQL com base nas diferenças entre o schema atual e o estado da base de dados, e aplica-o imediatamente no ambiente de desenvolvimento.
- `jorm migrate deploy`: Aplica todas as migrations pendentes na base de dados de produção, garantindo que o schema da base de dados esteja sincronizado com o `schema.jorm`.
- `jorm migrate status`: Exibe o estado atual das migrations, indicando quais foram aplicadas e quais estão pendentes.
- `jorm migrate reset`: Reverte todas as migrations aplicadas, limpando a base de dados (destinado apenas a ambientes de desenvolvimento).
- `jorm validate`: Valida a sintaxe e a semântica do `schema.jorm` sem gerar código.
- `jorm format`: Formata o arquivo `schema.jorm` para garantir consistência e legibilidade.
- `jorm version`: Exibe a versão instalada do Jorm CLI.

### 5.2. Schema `.jorm`

A linguagem de definição de schema do Jorm é declarativa e suporta:

- **Bloco** **`config`**: Para configurações gerais do projeto, como o tipo de base de dados (`database`), diretório de saída para o código gerado (`output`) e o pacote Java (`package`).
- **Bloco** **`model`**: Define as entidades da aplicação, com campos e seus tipos.
- **Bloco** **`enum`**: Define tipos enumerados que podem ser usados nos modelos.
- **Tipos Nativos**: Suporte a `String`, `Int`, `Float`, `Boolean`, `DateTime`, `Json`.
- **Campos Opcionais**: Indicados pelo sufixo `?` (e.g., `content String?`).
- **Anotações**: `@id`, `@unique`, `@default`, `@autoincrement`, `@relation`, `@ignore` para adicionar metadados e comportamentos específicos aos campos.
- **Relacionamentos**: Suporte a relacionamentos um para um, um para muitos e muitos para muitos entre modelos.

### 5.3. Geração de Código

O comando `jorm generate` produz:

- **Records Java Imutáveis**: Para cada modelo definido no `schema.jorm`, uma classe Java `record` é gerada, garantindo imutabilidade por padrão.
- **Enums Java**: Para cada `enum` definido no `schema.jorm`, um `enum` Java correspondente é gerado.
- **Jorm-Client Tipado**: Uma API de cliente específica para cada modelo, com métodos para operações CRUD e consultas, totalmente tipada para segurança e autocompletar.
- **SQL de Migration Versionado**: Scripts SQL para aplicar as alterações do schema, com versionamento e *timestamps*.
- **Código Legível**: O código gerado é formatado e projetado para ser legível por humanos, não apenas por máquinas, facilitando a depuração e a compreensão.
- **Zero Reflection em Runtime**: O `jorm-runtime` não utiliza *reflection*, resultando em melhor performance e compatibilidade com ambientes como GraalVM Native Image.

### 5.4. Jorm-Client

O `jorm-client` é a API que o desenvolvedor utiliza no código Java para interagir com os dados. É gerado automaticamente a partir do schema e oferece uma experiência de desenvolvimento segura e eficiente:

```java
// List all users
List<User> users = jorm.user().findMany();

// Find user by ID
User user = jorm.user().findById(1);

// Find users with filters
List<User> admins = jorm.user().findMany(where -> where
    .role(Role.ADMIN)
    .active(true)
);

// Create a new user
User created = jorm.user().create(data -> data
    .name("João")
    .email("joao@empresa.com")
    .role(Role.ADMIN)
);

// Update an existing user
User updated = jorm.user().update(1, data -> data
    .name("João Silva")
);

// Delete a user
jorm.user().delete(1);

// Find user with related posts included
User userWithPosts = jorm.user().findById(1, include -> include
    .posts(true)
);

// Combined filters and includes
List<User> users = jorm.user().findMany(
    where -> where.role(Role.ADMIN),
    include -> include.posts(true)
);
```

#### Consultas Avançadas e Flexibilidade

Para além das operações básicas, o `jorm-client` suporta:

- **Operadores de Consulta Ricos**: Inclui operadores para `AND`, `OR`, `NOT`, `contains`, `startsWith`, `endsWith`, `gt` (greater than), `lt` (less than), `gte` (greater than or equal), `lte` (less than or equal), `in`, `notIn`, entre outros, permitindo a construção de queries complexas de forma tipada.
- **Paginação e Ordenação**: Métodos como `skip()`, `take()`, e `orderBy()` para controlar a paginação e a ordem dos resultados.
- **Agregações**: Funções como `count()`, `sum()`, `avg()`, `min()`, `max()` diretamente na API do cliente para realizar cálculos agregados.
- **SQL Nativo**: Uma interface segura para executar SQL nativo quando a API fluente não for suficiente, com a capacidade de mapear os resultados para os modelos gerados pelo Jorm.

### 5.5. Output do Terminal

O Jorm CLI é projetado para ter um output limpo, colorido e informativo. Mensagens de erro são claras, indicando o problema e sugerindo soluções. Não há *stack traces* desnecessários ou *warnings* irrelevantes, focando apenas na informação essencial para o desenvolvedor.

## 6. Tecnologias Utilizadas

O Jorm é construído com um conjunto de tecnologias modernas e robustas:

| Tecnologia           | Parte do Jorm        | Função                                                                                                                              |
| :------------------- | :------------------- | :---------------------------------------------------------------------------------------------------------------------------------- |
| Java 21+             | CLI, Client, Runtime | Linguagem principal de todo o projeto, aproveitando recursos modernos como records e sealed classes.                                |
| Picocli              | CLI                  | Framework para construção de CLIs robustas, com output colorido, autocompletar e geração automática de help.                        |
| ANTLR4               | CLI                  | Gerador de parsers para a linguagem de definição do `schema.jorm`.                                                                  |
| JavaPoet             | CLI                  | Biblioteca para geração programática de código Java, utilizada para criar os arquivos `.java` do `jorm-client` e dos modelos.       |
| JDBC Puro            | CLI, Runtime         | Utilizado para conexão com a base de dados, execução de queries e aplicação de migrations, garantindo controle total e performance. |
| GraalVM Native Image | CLI                  | Compilação da CLI para binário nativo, eliminando a dependência da JVM e melhorando a performance e a experiência de instalação.    |
| JUnit 5              | Todos os Módulos     | Framework de testes unitários e de integração.                                                                                      |
| Testcontainers       | Todos os Módulos     | Ferramenta para testes de integração com bases de dados reais em containers Docker, garantindo ambientes de teste consistentes.     |

## 7. Bancos de Dados Suportados

### 7.1. MVP (Minimum Viable Product)

- PostgreSQL
- MySQL

### 7.2. Futuro

- SQLite
- MariaDB
- Oracle
- SQL Server

## 8. Estrutura do Repositório

A estrutura do repositório do Jorm é modular, facilitando o desenvolvimento, a manutenção e a gestão de dependências:

```
jorm/
├── cli/                        # Módulo da CLI (Command Line Interface)
│   ├── src/main/java/dev/jorm/cli/
│   │   ├── JormCli.java        # Entry point principal da CLI (usando Picocli)
│   │   ├── commands/           # Implementação de cada comando da CLI
│   │   │   ├── InitCommand.java
│   │   │   ├── GenerateCommand.java
│   │   │   ├── MigrateCommand.java
│   │   │   ├── ValidateCommand.java
│   │   │   └── FormatCommand.java
│   │   └── output/             # Utilitários para formatação e output colorido no terminal
│   │       ├── Printer.java
│   │       └── Spinner.java
│
├── parser/                     # Módulo responsável pelo parsing do schema .jorm
│   ├── src/main/
│   │   ├── antlr4/             # Gramática ANTLR4 para a linguagem .jorm
│   │   │   └── Jorm.g4
│   │   └── java/dev/jorm/parser/
│   │       ├── JormParser.java
│   │       ├── SchemaModel.java # Representação da Abstract Syntax Tree (AST) do schema
│   │       └── validators/     # Implementação de validações semânticas do schema
│
├── generator/                  # Módulo de geração de código Java e SQL
│   └── src/main/java/dev/jorm/generator/
│       ├── JavaGenerator.java  # Gera records e enums Java a partir do schema
│       ├── ClientGenerator.java # Gera o jorm-client tipado para cada modelo
│       ├── SqlGenerator.java   # Gera scripts SQL de migration
│       └── templates/          # Templates de código para a geração (e.g., para o jorm-client base)
│
├── client/                     # Módulo do jorm-client (código base e interfaces)
│   └── src/main/java/dev/jorm/client/
│       ├── JormClient.java     # Entry point principal para interação com o jorm-client
│       ├── ModelClient.java    # Interface base para os clientes gerados de cada modelo
│       ├── QueryBuilder.java   # Implementação de um builder fluente para construção de queries
│       └── WhereBuilder.java   # Implementação de um builder fluente para construção de cláusulas WHERE
│
├── db/                         # Módulo de conexão e gestão da base de dados
│   └── src/main/java/dev/jorm/db/
│       ├── ConnectionManager.java # Gerencia as conexões com a base de dados
│       ├── MigrationRunner.java # Executa e gerencia a aplicação de migrations
│       └── adapters/           # Adapters específicos para cada tipo de base de dados
│           ├── PostgresAdapter.java
│           └── MysqlAdapter.java
│
├── core/                       # Módulo compartilhado com tipos e utilitários comuns
│   └── src/main/java/dev/jorm/core/
│       ├── config/             # Classes para leitura e interpretação do bloco 'config' do .jorm
│       └── model/              # Tipos de dados e modelos compartilhados entre os módulos internos do Jorm
│
├── tests/
│   ├── unit/                   # Testes unitários para cada módulo
│   └── integration/            # Testes de integração usando Testcontainers para bases de dados reais
│
├── docs/                       # Documentação adicional e guias
├── install.sh                  # Script de instalação universal para a CLI
└── README.md                   # Arquivo README principal do projeto
```

## 9. Boas Práticas do Projeto

### 9.1. Código Limpo e Moderno

- **Java 21+**: Utilização dos recursos mais recentes da linguagem, como *records*, *sealed classes* e *pattern matching*, para escrever código conciso, seguro e expressivo.
- **Imutabilidade por Padrão**: Todos os modelos internos e gerados são imutáveis por padrão, promovendo a segurança de threads e a previsibilidade do estado.
- **Mínimo de Dependências**: Cada módulo do Jorm é projetado para ter apenas as dependências estritamente necessárias, mantendo o projeto leve e focado.
- **Código Gerado Legível**: O código Java gerado pelo Jorm é formatado e estruturado para ser facilmente compreendido por humanos, facilitando a depuração e a auditoria.
- **Zero Reflection em Runtime**: O `jorm-runtime` evita o uso de *reflection*, o que contribui para uma melhor performance e compatibilidade com GraalVM Native Image.
- **Comentários em Inglês**: Todos os comentários no código-fonte são escritos em inglês para garantir a acessibilidade e a colaboração internacional.

### 9.2. CLI e Experiência do Utilizador (UX)

- **Mensagens Claras**: Todas as mensagens de erro da CLI são projetadas para indicar claramente o problema e sugerir uma solução.
- **Output Colorido**: O output do terminal é colorido para melhorar a legibilidade e a identificação de informações importantes, com um *fallback* para terminais sem suporte a cores.
- **Confirmação para Ações Destrutivas**: Comandos que realizam operações destrutivas (e.g., `migrate reset`, `drop database`) exigem confirmação explícita do utilizador.
- **Help Automático**: Todos os comandos da CLI fornecem ajuda automática detalhada, incluindo exemplos de uso.

### 9.3. Migrations

- **Imutabilidade de Migrations**: Uma migration já aplicada nunca deve ser alterada, garantindo a rastreabilidade e a consistência do histórico da base de dados.
- **Nomes Descritivos**: Os nomes dos arquivos de migration incluem um *timestamp* e uma descrição legível, facilitando a compreensão do propósito de cada alteração.
- **Histórico no Banco de Dados**: O histórico de migrations aplicadas é armazenado numa tabela dedicada (`_jorm_migrations`) na base de dados.
- **SQL Reversível**: Sempre que possível, o Jorm gera SQL reversível para permitir o *rollback* de migrations de forma segura.

### 9.4. Contribuição

- **Issues Focados**: Cada nova funcionalidade ou correção de bug começa com um *issue* detalhado que descreve o problema ou a proposta.
- **Pull Requests Pequenos**: *Pull Requests* (PRs) são mantidos pequenos e focados numa única alteração para facilitar a revisão.
- **Testes Obrigatórios**: Testes unitários e de integração são obrigatórios para qualquer nova funcionalidade, especialmente para geração de código ou lógica de migration.
- **Changelog Atualizado**: O arquivo `CHANGELOG.md` é atualizado a cada *release* para documentar todas as alterações.

## 10. Integração com o Ecossistema Java

O Jorm é projetado para se integrar perfeitamente com o ecossistema Java existente, incluindo ferramentas de *build* e *frameworks* populares.

### 10.1. Ferramentas de Build (Maven e Gradle)

Maven e Gradle são ferramentas indispensáveis para gerenciar projetos Java. O Jorm não os substitui, mas se integra a eles de forma complementar:

- **Gestão de Dependências**: O `jorm-runtime` e as bibliotecas base do `jorm-client` serão publicadas em repositórios como o Maven Central, permitindo que os projetos Java as incluam facilmente como dependências via Maven ou Gradle.
- **Plugins de Build**: O Jorm fornecerá *plugins* dedicados para Maven e Gradle. Estes *plugins* permitirão automatizar a execução dos comandos `jorm generate` e `jorm migrate` como parte do ciclo de *build* do projeto. Por exemplo, o `jorm generate` pode ser configurado para ser executado antes da fase de `compile`, garantindo que o código Java gerado esteja sempre atualizado.
- **Publicação**: O próprio Jorm será construído e publicado usando Maven/Gradle, garantindo um processo de *release* padronizado.

### 10.2. Integração com Frameworks (Spring Boot, Quarkus, Micronaut)

Para facilitar a adoção em aplicações modernas, o Jorm terá módulos de integração para *frameworks* populares:

- **Injeção de Dependência**: Serão fornecidos *starters* ou módulos de integração (e.g., `jorm-spring-boot-starter`, `jorm-quarkus-extension`) que configurarão automaticamente o `JormClient` como um *bean* ou componente injetável. Isso permitirá que os desenvolvedores injetem o `JormClient` diretamente nos seus serviços e controladores.
- **Gestão de Transações**: Os módulos de integração também lidarão com a gestão de transações, integrando-se com os mecanismos transacionais dos *frameworks* (e.g., `@Transactional` do Spring), garantindo que as operações do Jorm participem do contexto transacional da aplicação.
- **Configuração Simplificada**: A configuração do Jorm (e.g., detalhes da base de dados) será integrada aos sistemas de configuração dos *frameworks* (e.g., `application.properties`/`application.yml` no Spring Boot).

## 11. Fases de Desenvolvimento (Roadmap)

O desenvolvimento do Jorm seguirá um roadmap faseado, com foco na entrega incremental de valor e na construção de uma base sólida.

### 11.1. v0.1.0 — MVP (Geração Core e CLI)

- **Parser do** **`.jorm`**: Suporte completo para `model`, `enum` e `config` no schema.
- **Geração de Records e Enums Java**: Criação de classes Java imutáveis e enums a partir do schema.
- **Geração de SQL de Migration**: Capacidade de gerar scripts SQL para criar e alterar tabelas.
- **CLI Essencial**: Implementação dos comandos `jorm init`, `jorm generate`, `jorm migrate dev`.
- **Suporte a Bases de Dados**: Conectividade e geração de SQL para PostgreSQL e MySQL.
- **Binário Nativo**: Compilação da CLI via GraalVM Native Image para distribuição eficiente.
- **Plugin Maven/Gradle Básico**: Um plugin inicial para automatizar a execução de `jorm generate` durante o *build*.

### 11.2. v0.2.0 — API do Cliente e Migrations Robustas

- **Jorm-Client Completo**: Implementação da API `jorm-client` com operações CRUD, filtros ricos, paginação e ordenação.
- **Gestão de Transações**: Adição de uma API clara e idiomática para gestão de transações no `jorm-client`.
- **Comandos de Migration Avançados**: Implementação de `jorm migrate status` e `jorm migrate deploy`.
- **Suporte a SQLite**: Expansão do suporte a bases de dados para incluir SQLite.
- **Validação e Formatação do Schema**: Implementação dos comandos `jorm validate` e `jorm format` na CLI.

### 11.3. v0.3.0 — Extensibilidade e Experiência Avançada

- **Jorm Studio**: Desenvolvimento de uma interface de utilizador web local para explorar dados e modelos da base de dados.
- **Relacionamentos Muitos para Muitos**: Suporte completo para a definição e gestão de relacionamentos muitos para muitos no schema e no `jorm-client`.
- **Geração de DTOs Opcionais**: Capacidade de gerar DTOs (Data Transfer Objects) a partir dos modelos, com opções de personalização.
- **Plugin para IDEs**: Desenvolvimento de um plugin para IntelliJ IDEA (e potencialmente outras IDEs) para *syntax highlighting* do `.jorm` e outras funcionalidades de produtividade.
- **Tipos Personalizados**: Mecanismo para mapear tipos Java personalizados para colunas da base de dados (custom scalars/adapters).
- **Integração com Frameworks**: Lançamento dos primeiros módulos de integração para *frameworks* como Spring Boot.

### 11.4. v1.0.0 — Estabilidade e Ecossistema Completo

- **API Estável e Documentada**: A API do Jorm será considerada estável, com uma documentação abrangente e de alta qualidade.
- **Suporte Completo a Bases de Dados**: Suporte a todos os bancos de dados principais (Oracle, SQL Server, etc.).
- **Site Oficial**: Lançamento do site `jorm.dev` com documentação completa, guias, exemplos e blog.
- **Publicação em Repositórios**: Disponibilização da CLI em Homebrew, Scoop, SDKMAN e das bibliotecas Java no Maven Central.
- **Comunidade e Suporte**: Estabelecimento de canais de comunicação e suporte para a comunidade de utilizadores.

## 12. Referências

- [Picocli](https://picocli.info/)
- [ANTLR4](https://www.antlr.org/)
- [JavaPoet](https://github.com/square/javapoet)
- [GraalVM Native Image](https://www.graalvm.org/reference-manual/native-image/)
- [JUnit 5](https://junit.org/junit5/)
- [Testcontainers](https://testcontainers.org/)

