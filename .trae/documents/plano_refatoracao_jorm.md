# Plano de Refatoração Completa do Jorm

## 1. Sumário
O objetivo deste plano é realizar uma refatoração completa da aplicação Jorm, módulo por módulo, começando pelo módulo `cli`. O foco é garantir que todo o código e mensagens estejam em Inglês, seguir a arquitetura definida no `README.md` (Java 21+, Imutabilidade, Zero Reflection), limpar lixo acumulado e deixar a ferramenta pronta para produção hoje mesmo. A refatoração não envolverá testes locais ou pedidos de clonagem por parte do utilizador.

## 2. Análise do Estado Atual
Após exploração do repositório, identificou-se o seguinte:
- **Lixo Acumulado**: Existem múltiplos diretórios `target/` gerados por builds antigos e ficheiros do IDE (`.idea/`) que não são estritamente necessários para o código-fonte.
- **Módulo `cli`**:
  - Mistura de idiomas (ex: `MigrateCommand.java` tem descrições em Português).
  - O output da CLI usa `System.out.println` e `e.printStackTrace()`, violando as boas práticas definidas no README para um terminal limpo e colorido.
  - Faltam comandos cruciais detalhados no README (`ValidateCommand`, `FormatCommand`, `MigrateDeployCommand`, `MigrateStatusCommand`, `MigrateResetCommand`).
  - O pacote `dev.jorm.cli.output` não existe.
- **Módulo `core`**: O módulo existe no `pom.xml`, mas não possui a pasta `src/main/java`. Modelos que deveriam estar no `core` (ex: `SchemaModel`) encontram-se atualmente no módulo `parser`.
- **Regras de Arquitetura**: É necessário confirmar a ausência de *reflection* e a utilização de Java 21+ (`records`, `sealed classes`) em todos os módulos.

## 3. Alterações Propostas

### Fase 1: Limpeza Geral (Garbage Collection)
- **Ação**: Eliminar todos os diretórios `target/` de todos os módulos (`cli`, `client`, `core`, `db`, `generator`, `parser`).
- **Ação**: Limpar ficheiros de configuração de IDE obsoletos que possam ser considerados lixo (opcional, foco em `target/`).

### Fase 2: Refatoração do Módulo `cli` (Ponto de Partida)
- **Tradução**: Traduzir todas as strings, descrições do Picocli e comentários em `MigrateCommand.java` e restantes comandos para Inglês.
- **Sistema de Output (UX)**:
  - Criar o pacote `dev.jorm.cli.output`.
  - Implementar `Printer.java` (para formatação colorida usando Picocli ANSI) e `Spinner.java` (para feedback visual de tarefas demoradas).
  - Refatorar `InitCommand`, `GenerateCommand`, `MigrateCommand` e `MigrateDevCommand` para usarem o `Printer` em vez de `System.out` / `System.err` e remover `e.printStackTrace()`.
- **Novos Comandos**:
  - Criar os stubs/implementações iniciais de `ValidateCommand.java` e `FormatCommand.java` e registá-los no `JormCli`.
  - Criar `MigrateDeployCommand.java`, `MigrateStatusCommand.java` e `MigrateResetCommand.java` e registá-los como subcomandos no `MigrateCommand`.

### Fase 3: Refatoração do Módulo `core` e Reorganização do `parser`
- **Ação**: Criar a estrutura `src/main/java/dev/jorm/core/model` e `dev.jorm.core.config` no módulo `core`.
- **Ação**: Mover a classe `SchemaModel.java` do `parser` para o `core`.
- **Ação**: Atualizar as importações nos módulos dependentes (`cli`, `generator`, `db`, `parser`).
- **Ação**: Garantir que todo o código no `parser` está em Inglês.

### Fase 4: Refatoração dos Módulos `generator` e `client`
- **Ação**: Traduzir variáveis e templates no `generator` para Inglês.
- **Ação**: Garantir que o `JavaGenerator` e o `ClientGenerator` produzem código final em conformidade com Java 21+ e *zero reflection*.
- **Ação**: Refatorar o `ModelClient.java` (no módulo `client`) para Inglês.

### Fase 5: Refatoração do Módulo `db` (Runtime)
- **Ação**: Traduzir classes (`ConnectionManager`, `MigrationRunner`, `QueryExecutor`) para Inglês.
- **Ação**: Garantir a estrita conformidade com as regras de imutabilidade e *zero reflection*.

## 4. Premissas e Decisões
- **Inglês Obrigatório**: O código e os outputs serão 100% em Inglês, seguindo a diretriz do utilizador.
- **Semantic Commits**: Após cada fase ou bloco lógico, o código será validado e os commits seguirão a convenção semântica (`feat:`, `refactor:`, `fix:`, `chore:`).
- **Sem Testes Locais**: Não executaremos `mvn test` nem pediremos clonagem. Garantiremos a exatidão via análise de código estática rigorosa e tipagem forte do Java 21.
- **Pronto para Produção**: O código gerado pela CLI terá um aspeto profissional e os comandos descritos na documentação estarão pelo menos estruturalmente presentes e com tratamento de erros limpo.

## 5. Passos de Verificação
1. Validar se não existe código em Português nos módulos.
2. Validar se a estrutura reflete o `README.md` (especialmente `core` e pastas de `output` da CLI).
3. Confirmar a ausência de exceções desformatadas (sem `e.printStackTrace()`).
4. Confirmar a ausência da pasta `target/` e outros ficheiros lixo no diretório de trabalho.