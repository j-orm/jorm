---
name: "jorm-antlr"
description: "Ajuda a escrever e testar gramáticas ANTLR4 para o ficheiro schema.jorm. Invocar ao alterar a sintaxe ou criar validadores de AST."
---

# Jorm ANTLR4 Expert

Esta skill foca-se na construção do parser para o Jorm.

## Regras
1. A gramática base deve ser mantida em `Jorm.g4`.
2. Assegurar que suporta os blocos `config`, `model` e `enum`.
3. Criar sempre classes para representar a Árvore de Sintaxe Abstrata (AST).
4. Escrever testes unitários rigorosos para todas as adições na gramática.