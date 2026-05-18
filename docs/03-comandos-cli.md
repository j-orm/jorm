A Jorm CLI fornece todos os comandos necessários para sincronizar o teu `schema.jorm` com o código Java e com a base de dados. Neste guia vais aprender cada comando em detalhe.

---

## 1. Gerar Código Java

Sempre que alterares o ficheiro `schema.jorm`, tens de regenerar o cliente Java:

```bash
jorm generate
```

Este comando lê o teu schema e cria os seguintes ficheiros na pasta definida em `output` (por padrão `src/main/java/generated`):

- **Records Java 21** para cada `model` definido (ex: `User.java`, `Post.java`)
- **Enums Java** para cada `enum` definido (ex: `Role.java`)
- **Classe `Jorm.java`:** o cliente principal com a Fluent API para realizar consultas

Exemplo de Record gerado para o modelo `User`:

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

## 2. Migrações de Base de Dados

As migrações traduzem os teus modelos para SQL real e sincronizam a base de dados. Todos os comandos de migração utilizam a variável de ambiente `DATABASE_URL` para se conectar.

### Configurar a DATABASE_URL

Define a variável de ambiente antes de executar qualquer comando de migração:

**PostgreSQL:**

```bash
export DATABASE_URL="postgresql://utilizador:senha@localhost:5432/nome_da_base"
```

**MySQL:**

```bash
export DATABASE_URL="mysql://utilizador:senha@localhost:3306/nome_da_base"
```

---

### 2.1. Migração em Desenvolvimento

Para criar o script SQL e aplicá-lo imediatamente à base de dados:

```bash
jorm migrate dev
```

O que este comando faz:

1. Lê o `schema.jorm`
2. Compara com o estado atual da base de dados via JDBC metadata
3. Gera o SQL necessário (`CREATE TABLE`, `ALTER TABLE`, etc.)
4. Aplica as alterações na base de dados
5. Guarda o ficheiro SQL em `.jorm/migrations/` com a data atual (ex: `001_20240518_add_user_table.sql`)

---

### 2.2. Ver o Estado das Migrações

Para ver quais as migrações já aplicadas e quais as que estão pendentes:

```bash
jorm migrate status
```

O output indica:

- As migrações já aplicadas (marcadas como `applied`)
- As migrações pendentes (marcadas como `pending`)

---

### 2.3. Aplicar em Produção

Num ambiente de produção, onde os ficheiros `.sql` já foram gerados noutra máquina ou pipeline de CI/CD, usa este comando para aplicar apenas as migrações pendentes:

```bash
jorm migrate deploy
```

Este comando nunca gera SQL novo. Apenas aplica os ficheiros `.sql` que ainda não foram executados.

---

### 2.4. Limpar a Base de Dados (só para desenvolvimento)

Para apagar todas as tabelas e começar do zero:

```bash
jorm migrate reset
```

> **Aviso:** Este comando é destrutivo e apaga todos os dados. Use apenas em ambientes de desenvolvimento.
> 

---

## 3. Resumo dos Comandos

| Comando | Descrição |
| --- | --- |
| `jorm init` | Inicializa um novo projeto Jorm na pasta atual |
| `jorm generate` | Gera os Records e o cliente Java a partir do schema |
| `jorm migrate dev` | Gera SQL e aplica migrações (ambiente de desenvolvimento) |
| `jorm migrate status` | Mostra o estado de cada migração (aplicada / pendente) |
| `jorm migrate deploy` | Aplica apenas migrações pendentes (ambiente de produção) |
| `jorm migrate reset` | Apaga todas as tabelas e dados (apenas desenvolvimento) |

---

## Próximos Passos

Com o código gerado e a base de dados pronta, avança para o **Passo 4** se utilizas o Spring Boot.