---
name: "jorm-arquitetura"
description: "Valida as regras base do Jorm: Java 21+, imutabilidade e zero reflection. Invocar sempre que escrever código no core ou runtime."
---

# Jorm Architecture Guardian

Esta skill garante as boas práticas e as regras absolutas do projeto Jorm.

## Regras Absolutas
1. **Java 21+**: Utilizar `records`, `sealed classes` e *pattern matching*.
2. **Zero Reflection**: O módulo `jorm-runtime` não pode usar *reflection*. Isto é crítico para a compatibilidade com o GraalVM Native Image.
3. **Imutabilidade**: Garantir imutabilidade por defeito em modelos internos e gerados.
4. **Mínimo de Dependências**: Evitar bibliotecas externas pesadas que não sejam estritamente essenciais.