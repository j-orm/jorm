# Passo 3: Comandos CLI e Migrações 🛠️

A Jorm CLI fornece as ferramentas necessárias para sincronizar o seu `schema.jorm` com o código Java e com a base de dados.

## 1. Gerar Código Java

Sempre que alterar o ficheiro `schema.jorm`, deve regenerar o cliente Java.

```bash
jorm generate
```

Este comando lê o seu schema e cria os `Records` e a classe cliente principal (`Jorm.java`) na pasta especificada na configuração (por padrão `src/main/java/generated`).

## 2. Migrações de Base de Dados

As migrações permitem traduzir os seus modelos para código SQL real. Para testar e implementar na base de dados, a Jorm utiliza a variável de ambiente `DATABASE_URL`.

**Formato da DATABASE_URL:**
```bash
export DATABASE_URL="postgresql://usuario:senha@localhost:5432/nome_da_base"
# ou
export DATABASE_URL="mysql://usuario:senha@localhost:3306/nome_da_base"
```

### 2.1. Criar uma Migração (Modo de Desenvolvimento)

Para ler o schema, gerar o script `.sql` e aplicá-lo à sua base de dados, execute:

```bash
jorm migrate dev
```
Os ficheiros SQL gerados são guardados na pasta `.jorm/migrations/` com a data atual.

### 2.2. Verificar o Estado

Para ver quais as migrações que já foram aplicadas e quais as que estão pendentes:

```bash
jorm migrate status
```

### 2.3. Aplicar em Produção

Num ambiente de produção, onde os ficheiros `.sql` já foram gerados noutra máquina ou pipeline, basta aplicar as migrações pendentes:

```bash
jorm migrate deploy
```

### 2.4. Limpar a Base de Dados (Apenas para Desenvolvimento)

Se precisar de limpar todas as tabelas e começar de novo:

```bash
jorm migrate reset
```
> **Aviso:** Este comando é destrutivo. Vai apagar todos os dados.

## 3. Integração com Frameworks

Com o código gerado e a base de dados pronta, está na hora de usar a Jorm na sua aplicação. Avance para o **Passo 4** se utiliza o Spring Boot.
