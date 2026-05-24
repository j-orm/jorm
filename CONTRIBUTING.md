# Contribuir para o Jorm

Obrigado por ajudares a melhorar o Jorm.

## Onde pedir ajuda

- Perguntas e discussão de ideias: https://github.com/j-orm/jorm/discussions
- Bugs e tarefas: Issues do repositório

Se a página de Discussions ainda não estiver activa, um maintainer pode activar em:
Settings -> General -> Features -> Discussions.

## Reportar bugs

Antes de abrir um issue:

- Confirma se já existe um issue aberto.
- Inclui versão do Jorm, sistema operativo e passos para reproduzir.
- Anexa logs relevantes (sem segredos).

## Propor uma funcionalidade

- Descreve o problema que queres resolver.
- Sugere uma API e exemplos de uso.
- Se possível, explica impacto em compatibilidade e migrações.

## Workflow de desenvolvimento

1. Cria um branch a partir de `main`.
2. Faz alterações pequenas e focadas.
3. Adiciona ou ajusta testes quando fizer sentido.
4. Garante que o build passa localmente.
5. Abre um Pull Request.

## Convenções

- Código e identificadores em inglês.
- Sem reflexão no runtime.
- Preferir imutabilidade.

## Commits

Usar Conventional Commits (mensagens em inglês):

- `feat: add ...`
- `fix: ...`
- `docs: ...`
- `refactor: ...`
- `test: ...`
- `chore: ...`

## Pull Requests

- Descreve claramente o que muda e porquê.
- Mantém o PR pequeno (uma mudança lógica).
- Liga issues relacionados (ex: `Closes #123`).
