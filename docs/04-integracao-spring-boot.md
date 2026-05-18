
Passo 4: Integração com Spring Boot


A Jorm foi desenhada para ser independente de qualquer framework, mas integra-se de forma brilhante com o **Spring Boot**. Quando configuras `framework = "spring"` no teu `schema.jorm`, o cliente gerado inclui automaticamente a anotação `@Repository`, permitindo injeção de dependências nativa.

---

## 1. Adicionar a Dependência

Adiciona o starter oficial da Jorm ao teu `pom.xml`:

```xml
<dependency>
    <groupId>pt.jorm</groupId>
    <artifactId>jorm-spring-boot-starter</artifactId>
    <version>0.1.0</version>
</dependency>
```

> Verifica sempre a versão estável mais recente no repositório oficial.
> 

---

## 2. Configurar o [application.properties](http://application.properties)

O Spring Boot Starter da Jorm aproveita a configuração padrão de `DataSource` do Spring. Basta configurar a base de dados como farias normalmente.

[**application.properties](http://application.properties):**

```
spring.datasource.url=jdbc:postgresql://localhost:5432/jorm_db
spring.datasource.username=postgres
spring.datasource.password=a_tua_senha
spring.datasource.driver-class-name=org.postgresql.Driver
```

**application.yml (alternativa):**

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/jorm_db
    username: postgres
    password: a_tua_senha
    driver-class-name: org.postgresql.Driver
```

---

## 3. Injetar o Cliente Jorm

Com a anotação `@Repository` gerada automaticamente, podes injetar a classe `Jorm` nos teus serviços usando injeção por construtor (abordagem recomendada).

```java
import com.omeuprojeto.db.Jorm;
import com.omeuprojeto.db.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final Jorm jorm;

    public UserService(Jorm jorm) {
        this.jorm = jorm;
    }

    // ... métodos
}
```

---

## 4. Exemplos de Consultas com a Fluent API

O cliente gerado expe uma Fluent API que torna as consultas intuitivas e com autocomplete completo na IDE.

### Criar um utilizador

```java
public User createUser(String name, String email) {
    return jorm.user().create(data -> data
        .name(name)
        .email(email)
        .isActive(true)
    );
}
```

### Buscar múltiplos registos com filtro

```java
public List<User> getActiveUsers() {
    return jorm.user().findMany(where -> where
        .isActive().equals(true)
    );
}
```

### Buscar um único registo por ID

```java
public User getUserById(Integer userId) {
    return jorm.user().findById(userId);
}
```

### Carregar um utilizador com os seus posts (eager loading)

```java
public User getUserWithPosts(Integer userId) {
    return jorm.user().findById(userId, include -> include
        .posts(true)
    );
}
```

### Atualizar um registo

```java
public User deactivateUser(Integer userId) {
    return jorm.user().update(
        where -> where.id().equals(userId),
        data  -> data.isActive(false)
    );
}
```

### Apagar um registo

```java
public void deleteUser(Integer userId) {
    jorm.user().delete(where -> where
        .id().equals(userId)
    );
}
```

---

## 5. Exemplo Completo de um Service

```java
import com.omeuprojeto.db.Jorm;
import com.omeuprojeto.db.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final Jorm jorm;

    public UserService(Jorm jorm) {
        this.jorm = jorm;
    }

    public User createUser(String name, String email) {
        return jorm.user().create(data -> data
            .name(name)
            .email(email)
            .isActive(true)
        );
    }

    public List<User> getActiveUsers() {
        return jorm.user().findMany(where -> where
            .isActive().equals(true)
        );
    }

    public User getUserWithPosts(Integer userId) {
        return jorm.user().findById(userId, include -> include
            .posts(true)
        );
    }

    public void deleteUser(Integer userId) {
        jorm.user().delete(where -> where
            .id().equals(userId)
        );
    }
}
```

---

## Parabéns! 🎉

Concluíste o guia completo da Jorm. Tens agora uma aplicação Java limpa, sem o peso da reflection, com tipagem estática do início ao fim e integrada nativamente com o Spring Boot.