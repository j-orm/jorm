# Passo 4: Integração com Spring Boot 🌱

A Jorm foi desenhada para ser independente, mas funciona de forma brilhante com o Spring Boot. Se configurar o seu `schema.jorm` com `framework = "spring"`, o cliente gerado já inclui a anotação `@Repository`.

## 1. Adicionar a Dependência

Adicione o starter oficial da Jorm ao seu `pom.xml`.

```xml
<dependency>
    <groupId>dev.jorm</groupId>
    <artifactId>jorm-spring-boot-starter</artifactId>
    <version>0.1.0</version>
</dependency>
```

> **Nota:** Se estiver a utilizar Maven Central, verifique a versão estável mais recente.

## 2. Configurar o application.properties

A Jorm Spring Boot Starter aproveita a configuração padrão de `DataSource` do Spring. Basta configurar a base de dados como faria normalmente no ficheiro `application.properties` (ou `application.yml`).

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/jorm_db
spring.datasource.username=postgres
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=org.postgresql.Driver
```

## 3. Injetar e Utilizar o Cliente

Graças à anotação `@Repository` gerada no cliente, pode injetar a classe `Jorm` diretamente nos seus serviços usando o `@Autowired` ou a injeção por construtor.

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
        // Exemplo de eager loading de relacionamentos
        return jorm.user().findById(userId, include -> include
            .posts(true)
        );
    }
}
```

## Parabéns! 🎉

Concluiu o guia da Jorm. Agora tem uma aplicação Java limpa, sem o peso da reflection e com tipagem estática do início ao fim!
