<div align="center">
  <h1>🚀 Jorm</h1>
  <p><strong>O ORM Moderno, Schema-First e sem Reflection para o Ecossistema Java</strong></p>
  
  [![Status](https://img.shields.io/badge/status-BETA-orange.svg)]()
  [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
  [![Java 21](https://img.shields.io/badge/Java-21%2B-blue.svg)]()
</div>

<br/>

> ⚠️ **Aviso:** A Jorm encontra-se atualmente em fase **BETA**. O projeto já é funcional, mas a API pode sofrer alterações. Agradecemos o seu feedback e contribuições!

O ecossistema Java precisava de uma lufada de ar fresco na interação com bases de dados. Inspirado na simplicidade do Prisma (no mundo TypeScript), a **Jorm** é um ORM desenhado para a era do Java 21 e do GraalVM. 

---

## 💡 O Que a Jorm Vem Resolver?

Durante décadas, o ecossistema Java confiou em ORMs pesados (como o Hibernate ou JPA) que exigem dezenas de anotações, classes mutáveis e dependem fortemente de *reflection*. Isso resulta em tempos de arranque lentos, uso excessivo de memória e dificuldade em compilar nativamente para GraalVM.

A Jorm resolve este problema mudando completamente o paradigma: **Schema-First e geração de código nativo.**

### ❌ Como era antes (JPA / Hibernate)
Para criar uma simples tabela de utilizadores, o código ficava poluído com anotações e código repetitivo (*boilerplate*):

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

    // + Getters, Setters, Construtores vazios, equals() e hashCode() ...
}
```

### ✅ Como é agora (com a Jorm)
Escreve apenas a estrutura da sua base de dados num ficheiro declarativo simples e elegante:

```jorm
model User {
    id    Int    @id @autoincrement
    email String @unique
    name  String
}
```

**O resultado?** A Jorm lê este ficheiro e gera automaticamente um **Record** do Java 21 (100% imutável) e um cliente de base de dados que faz o mapeamento manualmente. Sem anotações (`@Entity`, `@Column`), sem *reflection*, com máxima performance e tipagem estática pura!

---

## 🔄 Como Funcionam as Migrações?

Em vez de escrever scripts SQL complexos manualmente ou usar ferramentas externas (como o Flyway ou Liquibase), a Jorm cuida de todo o ciclo de vida da sua base de dados.

Quando executa o comando de desenvolvimento:

```bash
jorm migrate dev
```

A Jorm executa o seguinte fluxo automaticamente de forma transparente:
1. **Lê** o ficheiro `schema.jorm`.
2. **Compara** o seu modelo com o estado atual da base de dados (usando JDBC metadata).
3. **Gera** o SQL necessário de forma automática (`CREATE TABLE`, `ALTER TABLE`, etc.).
4. **Aplica** as alterações de imediato na base de dados conectada.
5. **Guarda** o histórico num ficheiro local (exemplo: `migrations/001_add_user_table.sql`) para controlo de versão e futura implementação em produção.

---

## ✨ Funcionalidades Atuais

A versão BETA já inclui os pilares fundamentais para começar a desenvolver:

* 📝 **Schema-First:** Um ficheiro `.jorm` como fonte única da verdade.
* ⚡ **Zero Reflection:** Cliente gerado com `JavaPoet` e mapeamento manual transparente. Perfeito para compilação nativa com GraalVM.
* 📦 **Records do Java 21:** Todos os modelos gerados utilizam a funcionalidade nativa de `Records` para imutabilidade e concisão.
* 🗄️ **Suporte a Múltiplos Dialetos:** Integração pronta a usar com **PostgreSQL** e **MySQL**.
* 🛠️ **CLI Poderosa:** Comandos para inicializar, gerar código e correr migrações de forma autónoma.
* 🌱 **Spring Boot Starter:** Configuração automática e injeção de dependências para projetos Spring.
* 🔍 **Fluent API:** Consultas intuitivas com autocomplete na sua IDE, sem necessidade de escrever SQL ou JPQL manualmente.

---

## 🔮 O Que Aí Vem (Roadmap)

Temos planos ambiciosos para tornar a Jorm no ORM de referência. Eis as funcionalidades planeadas para as próximas versões:

* 🔄 **Relações Complexas:** Suporte completo e bidirecional para relações One-to-One, One-to-Many e Many-to-Many.
* 🍃 **Suporte Quarkus & Micronaut:** Starters nativos para frameworks reativos.
* 📊 **Jorm Studio:** Uma interface gráfica no browser para visualizar e editar os dados diretamente a partir do ficheiro de schema.
* 🐘 **MongoDB / NoSQL:** Extensão do parser para suportar bases de dados não relacionais.
* 🚀 **Migrações Avançadas:** Diffs automáticos de schema para gerar scripts SQL de *rollback* precisos.

---

## 🚀 Instalação Rápida

Pode instalar a CLI da Jorm utilizando o seu gestor de pacotes preferido:

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

**Instalação Universal**

**No macOS ou Linux (Bash/Zsh):**
```bash
curl -sSL https://raw.githubusercontent.com/j-orm/jorm/master/install.sh | bash
```

**No Windows (PowerShell):**
```powershell
Invoke-WebRequest -Uri "https://raw.githubusercontent.com/j-orm/jorm/master/install.ps1" -OutFile "install.ps1"; .\install.ps1; Remove-Item install.ps1
```

---

## 📚 Documentação Completa

Preparámos um guia passo a passo detalhado na pasta `docs/` para o ajudar a tirar o máximo partido da Jorm. Explore os capítulos:

1. [Instalação e Inicialização](docs/01-instalacao.md)
2. [A Sintaxe do Schema](docs/02-sintaxe-do-schema.md)
3. [Comandos CLI e Migrações](docs/03-comandos-cli.md)
4. [Integração com Spring Boot](docs/04-integracao-spring-boot.md)

*(Para informações sobre como configurar o ambiente de desenvolvimento e compilar o próprio repositório da Jorm, consulte o ficheiro [DEVELOPMENT.md](DEVELOPMENT.md))*

---

## 🤝 Como Contribuir

Sendo um projeto de código aberto, toda a ajuda é bem-vinda! Se encontrou um erro, tem uma ideia para uma funcionalidade ou quer corrigir documentação:

1. Faça um Fork do repositório.
2. Crie uma branch com a sua funcionalidade (`git checkout -b feature/a-minha-ideia`).
3. Siga a convenção de **Commits Semânticos** (ex: `feat: add support for uuid types`).
4. Abra um Pull Request.

## 📄 Licença

Este projeto está licenciado sob a licença **MIT**. Veja o ficheiro [LICENSE](LICENSE) para mais detalhes.
