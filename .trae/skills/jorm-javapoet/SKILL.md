---
name: "jorm-javapoet"
description: "Gera código Java moderno usando JavaPoet. Invocar quando for necessário gerar código cliente ou entidades."
---

# Jorm JavaPoet Generator

Esta skill ajuda a usar a biblioteca JavaPoet no módulo `generator`.

## Regras
1. Utilizar sempre recursos modernos do Java 21+.
2. Os modelos gerados devem ser, preferencialmente, `records` imutáveis.
3. A API do cliente (`jorm-client`) gerada deve ser fluente e tipada.
4. O código gerado deve ser bem formatado para ser legível por humanos.