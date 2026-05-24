# Step 4: Spring Boot Integration

Jorm was designed to be framework-agnostic, but it integrates brilliantly with **Spring Boot**. When you set `framework = "spring"` in your `schema.jorm`, the generated client automatically includes the `@Repository` annotation, allowing for native dependency injection.

---

## 1. Adding the Dependency

Add the official Jorm starter to your `pom.xml`:

```xml
<dependency>
    <groupId>pt.jorm</groupId>
    <artifactId>jorm-spring-boot-starter</artifactId>
    <version>0.1.1</version>
</dependency>
```

> Tip: Maven Central badges often show the highest version across all releases. If you want the latest 0.1.x line, use the recommended badge with a 0.1 prefix filter.

---

## 2. Configuring application.properties

Jorm's Spring Boot Starter leverages Spring's default `DataSource` configuration. Simply configure the database as you normally would.

**application.properties:**

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/jorm_db
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver
```

**application.yml (alternative):**

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/jorm_db
    username: postgres
    password: your_password
    driver-class-name: org.postgresql.Driver
```

---

## 3. Injecting the Jorm Client

With the auto-generated `@Repository` annotation, you can inject the `Jorm` class into your services using constructor injection (recommended approach).

```java
import com.myproject.db.Jorm;
import com.myproject.db.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final Jorm jorm;

    public UserService(Jorm jorm) {
        this.jorm = jorm;
    }

    // ... methods
}
```

---

## 4. Query Examples with the Fluent API

The generated client exposes a Fluent API that makes queries intuitive and provides full autocomplete in the IDE.

### Creating a user

```java
public User createUser(String name, String email) {
    return jorm.user().create(data -> data
        .name(name)
        .email(email)
        .isActive(true)
    );
}
```

### Fetching multiple records with a filter

```java
public List<User> getActiveUsers() {
    return jorm.user().findMany(where -> where
        .isActive().equals(true)
    );
}
```

### Fetching a single record by ID

```java
public User getUserById(Integer userId) {
    return jorm.user().findById(userId);
}
```

### Loading a user with their posts (eager loading)

```java
public User getUserWithPosts(Integer userId) {
    return jorm.user().findById(userId, include -> include
        .posts(true)
    );
}
```

### Updating a record

```java
public User deactivateUser(Integer userId) {
    return jorm.user().update(
        where -> where.id().equals(userId),
        data  -> data.isActive(false)
    );
}
```

### Deleting a record

```java
public void deleteUser(Integer userId) {
    jorm.user().delete(where -> where
        .id().equals(userId)
    );
}
```

---

## 5. Complete Service Example

```java
import com.myproject.db.Jorm;
import com.myproject.db.User;
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

## Congratulations! 🎉

You have completed the full Jorm guide. You now have a clean Java application, without the overhead of reflection, with static typing from start to finish, and natively integrated with Spring Boot.
