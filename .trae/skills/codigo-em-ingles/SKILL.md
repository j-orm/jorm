***

name: "codigo-em-ingles"
description: "Garante que todo o código fonte escrito é exclusivamente em inglês. Invocar sempre que gerar, refatorar ou modificar código, comentários ou documentação técnica."
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# Regra Absoluta: Código 100% em Inglês

É estritamente obrigatório que todo o interior do software seja desenvolvido na língua inglesa. Esta regra não tem exceções e aplica-se a todas as partes do código.

## O que deve estar obrigatoriamente em Inglês:

1. **Estruturas de Código:**
   - Nomes de classes, interfaces, records, enums e pacotes.
   - Nomes de métodos, funções, variáveis e constantes.
2. **Documentação Interna e Comentários:**
   - Comentários inline (ex: `// fetch user data`).
   - Blocos de comentários (ex: `/* block */`).
   - JavaDoc e qualquer documentação de métodos/classes.
3. **Mensagens do Sistema:**
   - Mensagens de exceções (ex: `throw new IllegalArgumentException("Invalid email format")`).
   - Mensagens de log (ex: `log.info("User successfully authenticated")`).
   - Chaves e identificadores em ficheiros de configuração.

## O que pode estar em Português:

- Apenas a comunicação direta no chat com o utilizador.
- Ficheiros de planeamento ou documentação de alto nível (como o README.md) se o utilizador o solicitar explicitamente nessa língua.

**Atenção:** Nunca mistures português e inglês no mesmo ficheiro de código. O código fonte é sempre universal e deve estar preparado para ser lido por programadores de qualquer parte do mundo.
