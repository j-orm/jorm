# Plano de Desenvolvimento Jorm

## 1. Resumo
O Jorm é um ORM moderno para o ecossistema Java com uma abordagem *schema-first*. O seu principal objetivo é resolver o problema do código repetitivo no desenvolvimento Java, eliminando a necessidade de criar manualmente entidades, repositórios, DTOs e scripts de migração. O Jorm centraliza a definição de dados num ficheiro `.jorm` e gera automaticamente o código cliente e os scripts SQL necessários.

## 2. Análise do Estado Atual
Atualmente, o projeto possui apenas a documentação inicial (`README.md`). Esta documentação define de forma robusta a arquitetura do sistema, dividida em três componentes principais:
*   **jorm CLI**: Ferramenta de terminal para gerir o projeto.
*   **jorm-client**: API fluente gerada para operações com a base de dados.
*   **jorm-runtime**: Biblioteca leve para execução em tempo real sem o uso de *reflection*.

As tecnologias base estão bem definidas: Java 21+, Picocli, ANTLR4, JavaPoet e JDBC puro.

## 3. Mudanças Propostas e Passos de Implementação

Para atingir a visão do Jorm de ponta a ponta, o desenvolvimento será estruturado da seguinte forma:

### Passo 1: Configuração do Projeto Multi-módulo
*   Criar o projeto base (Maven ou Gradle) com a estrutura de módulos: `cli`, `parser`, `generator`, `client`, `db` e `core`.
*   Configurar o suporte para Java 21+ em todos os módulos.

### Passo 2: Implementação do Parser (Módulo `parser`)
*   Definir a gramática ANTLR4 (`Jorm.g4`) para suportar os blocos `config`, `model` e `enum`.
*   Criar as classes para a representação da Árvore de Sintaxe Abstrata (AST).
*   Implementar os validadores semânticos.

### Passo 3: Implementação do Gerador (Módulo `generator`)
*   Utilizar o JavaPoet para gerar *records* Java imutáveis e *enums*.
*   Criar a lógica de geração do `jorm-client` tipado.
*   Implementar a geração de ficheiros SQL para migrações (focando inicialmente em PostgreSQL e MySQL).

### Passo 4: Implementação do Runtime e Base de Dados (Módulos `client` e `db`)
*   Criar o `JormClient` base e o gestor de conexões JDBC.
*   Implementar a execução de consultas e o sistema de aplicação de migrações (`MigrationRunner`).

### Passo 5: Implementação da CLI (Módulo `cli`)
*   Usar o Picocli para implementar os comandos `init`, `generate` e `migrate dev`.
*   Configurar a compilação nativa com o GraalVM.

### Passo 6: Criação das Skills do Trae (Próximo passo imediato)
Para acelerar o desenvolvimento futuro, serão criadas três skills específicas para este projeto:
1.  **jorm-antlr**: Uma skill para ajudar a escrever e depurar a gramática ANTLR4 do Jorm, garantindo que o *parsing* do ficheiro `.jorm` seja eficiente.
2.  **jorm-javapoet**: Uma skill focada na geração de código Java moderno (usando *Records*, construtores e métodos fluentes) através da biblioteca JavaPoet.
3.  **jorm-arquitetura**: Uma skill para validar o código novo, garantindo que este cumpre os requisitos rigorosos do Jorm: uso de Java 21+, imutabilidade por defeito e zero *reflection* em tempo de execução.

## 4. Premissas e Decisões
*   **Comunicação em pt-PT**: A documentação e as interações seguirão o português de Portugal, sem o uso de travessões longos.
*   **Sem Reflection**: O runtime não usará *reflection*, visando garantir a máxima performance e total compatibilidade com o GraalVM Native Image.
*   **Imutabilidade**: Os modelos gerados serão *records* imutáveis do Java.

## 5. Passos de Verificação
*   **Testes do Parser**: Garantir que o ANTLR4 consegue analisar com sucesso um ficheiro `.jorm` complexo.
*   **Testes do Gerador**: Compilar o código gerado pelo JavaPoet em memória para validar a sua correção sintática.
*   **Testes de Integração**: Usar o Testcontainers para correr migrações numa base de dados real (PostgreSQL/MySQL) e realizar operações CRUD.
