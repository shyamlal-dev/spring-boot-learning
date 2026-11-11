# Spring Boot Fundamentals

Concise revision notes for the foundational concepts used in the Student Management System.

---

## 1. Spring Framework

Spring is a Java framework for building modular, maintainable, and testable applications.

Core capabilities:

- Inversion of Control and Dependency Injection
- Web and REST API development
- Database and transaction support
- Security
- Testing support

## 2. Spring Boot

Spring Boot is built on top of the Spring Framework. It simplifies application setup through:

- Auto-configuration
- Starter dependencies
- Embedded servers such as Tomcat
- Sensible defaults
- Externalized configuration
- Production features such as Actuator

Spring Boot does not replace Spring. It simplifies Spring application development.

```java
@SpringBootApplication
public class StudentManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementSystemApplication.class, args);
    }
}
```

## 3. `@SpringBootApplication`

`@SpringBootApplication` combines:

```text
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
```

- `@SpringBootConfiguration`: marks the main configuration class.
- `@EnableAutoConfiguration`: configures the application based on dependencies, properties, and existing beans.
- `@ComponentScan`: scans the main package and its sub-packages for Spring components.

Keep the main class in the root package:

```text
com.sms.studentmanagement
├── StudentManagementSystemApplication.java
├── controller/
├── service/
├── repository/
├── entity/
├── dto/
└── exception/
```

## 4. Dependency

A dependency is an object required by another object to perform its work.

```java
public class StudentService {
    private final StudentRepository studentRepository;
}
```

`StudentRepository` is a dependency of `StudentService`.

## 5. Tight and Loose Coupling

### Tight coupling

A class directly creates a concrete dependency.

```java
public class Developer {
    private final Laptop laptop = new Laptop();
}
```

Problems:

- Implementation is difficult to replace.
- Unit testing is harder.
- Mock dependencies cannot be passed easily.
- Changes spread across dependent classes.

### Loose coupling

A class depends on an abstraction and receives its dependency externally.

```java
public interface Computer {
    void compile();
}

@Component
public class Laptop implements Computer {
    @Override
    public void compile() {
        System.out.println("Compiling using Laptop");
    }
}

@Component
public class Developer {
    private final Computer computer;

    public Developer(Computer computer) {
        this.computer = computer;
    }
}
```

Benefits:

- Implementations are replaceable.
- Unit testing and mocking are easier.
- Code follows the Dependency Inversion Principle.
- Classes are easier to maintain.

## 6. Inversion of Control

Inversion of Control, or IoC, transfers object creation and management from application code to the Spring container.

```text
Without Spring: application code creates and connects objects.
With Spring: the Spring container creates, connects, and manages objects.
```

IoC answers:

```text
Who creates and manages application objects?
```

Answer: the Spring IoC container.

## 7. Spring IoC Container

The IoC container:

1. Reads application configuration.
2. Scans packages.
3. Finds component classes.
4. Creates beans.
5. Resolves dependencies.
6. Injects dependencies.
7. Manages bean lifecycle and scope.

`ApplicationContext` is the main Spring IoC container interface.

```java
ApplicationContext context =
        SpringApplication.run(Application.class, args);
```

A bean can be retrieved programmatically:

```java
Developer developer = context.getBean(Developer.class);
```

Normal business classes should use Dependency Injection instead of repeatedly calling `getBean()`.

## 8. Spring Bean

A Spring Bean is an object created, configured, and managed by the Spring IoC container.

```java
@Component
public class Laptop {
}
```

Spring beans are singleton-scoped by default. This means one bean instance per `ApplicationContext`, not necessarily one object for the entire JVM.

## 9. Dependency Injection

Dependency Injection, or DI, supplies an object's dependencies from outside instead of letting the object create them.

IoC is the broader principle. DI is the main technique Spring uses to implement IoC.

### Constructor injection

```java
@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
}
```

Preferred for mandatory dependencies because it:

- Makes dependencies explicit.
- Supports `final` fields.
- Ensures complete initialization.
- Improves testability.
- Exposes circular dependencies early.

If a class has one constructor, `@Autowired` is not required.

### Setter injection

```java
@Autowired
public void setNotificationService(NotificationService service) {
    this.notificationService = service;
}
```

Use mainly for optional or replaceable dependencies.

### Field injection

```java
@Autowired
private StudentRepository studentRepository;
```

Avoid in production code because it hides dependencies, prevents proper immutability, and makes unit testing harder.

## 10. Multiple Bean Ambiguity

If multiple beans implement the same interface, Spring cannot decide which implementation to inject automatically.

```java
public interface NotificationService {
    String send();
}

@Component
public class EmailNotificationService implements NotificationService {
    public String send() {
        return "Email sent";
    }
}

@Component
public class SmsNotificationService implements NotificationService {
    public String send() {
        return "SMS sent";
    }
}
```

### `@Primary`

Marks the default implementation.

```java
@Component
@Primary
public class EmailNotificationService implements NotificationService {
}
```

### `@Qualifier`

Selects a specific bean at an injection point.

```java
public NotificationController(
        @Qualifier("smsNotificationService") NotificationService service) {
    this.service = service;
}
```

A matching `@Qualifier` takes priority over `@Primary`.

## 11. Spring Configuration

Spring supports:

1. Annotation-based configuration
2. Java-based configuration
3. Properties or YAML configuration
4. XML configuration in legacy applications

### Annotation configuration

```java
@Component
@Service
@Repository
@RestController
```

### Java configuration

```java
@Configuration
public class AppConfig {

    @Bean
    public ThirdPartyClient thirdPartyClient() {
        return new ThirdPartyClient();
    }
}
```

Use `@Bean` when a class cannot be annotated, custom creation logic is required, or multiple configured instances are needed.

### External configuration

```properties
spring.application.name=student-management-system
server.port=8080
```

`application.properties` and `application.yml` store configuration values. They do not normally define Java beans.

## 12. Layered Architecture

```text
Client
  ↓
Controller
  ↓
Service
  ↓
Repository
  ↓
Database
```

### Controller layer

Responsibilities:

- Receive HTTP requests.
- Read request data.
- Trigger request validation.
- Call the service layer.
- Return HTTP responses.

Controllers should remain thin and should not contain complex business logic.

### Service layer

Responsibilities:

- Implement business rules.
- Coordinate use cases and repositories.
- Validate business conditions.
- Define transaction boundaries.

### Repository layer

Responsibilities:

- Save, retrieve, update, and delete data.
- Execute database queries.

Repositories should not contain HTTP handling or unrelated business logic.

## 13. Core Stereotype Annotations

| Annotation | Level | Purpose |
|---|---|---|
| `@Component` | Class | General Spring-managed component |
| `@Service` | Class | Business-logic component |
| `@Repository` | Class | Data-access component and persistence exception translation |
| `@Controller` | Class | MVC controller that commonly returns views |
| `@RestController` | Class | REST controller that writes return values to the response body |

`@Service`, `@Repository`, `@Controller`, and `@RestController` are specialized component stereotypes.

## 14. Core Web Annotations

| Annotation | Level | Purpose |
|---|---|---|
| `@RequestMapping` | Class or method | Defines a base path or general request mapping |
| `@GetMapping` | Method | Handles GET requests |
| `@PostMapping` | Method | Handles POST requests |
| `@PutMapping` | Method | Handles PUT requests |
| `@PatchMapping` | Method | Handles PATCH requests |
| `@DeleteMapping` | Method | Handles DELETE requests |
| `@PathVariable` | Parameter | Reads a value from the URL path |
| `@RequestParam` | Parameter | Reads a query parameter |
| `@RequestBody` | Parameter | Converts the request body into a Java object |

Example:

```java
@RestController
@RequestMapping("/students")
public class StudentController {

    @GetMapping("/{id}")
    public StudentResponse getStudent(@PathVariable Long id) {
        return studentService.getStudent(id);
    }
}
```

`@RestController` is conceptually:

```text
@Controller + @ResponseBody
```

## 15. Dependency-Related Annotations

| Annotation | Level | Purpose |
|---|---|---|
| `@Autowired` | Constructor, method, or field | Requests dependency injection |
| `@Primary` | Class or `@Bean` method | Marks the default bean |
| `@Qualifier` | Parameter, field, method, or annotation type | Selects a specific bean |
| `@Configuration` | Class | Declares a Java configuration class |
| `@Bean` | Method | Registers the returned object as a bean |

Prefer constructor injection without `@Autowired` when the class has one constructor.

## 16. Embedded Tomcat

Tomcat is a web server and Servlet container. Spring Boot can package embedded Tomcat with the application, so an external server installation is not required.

```bash
./mvnw spring-boot:run
```

Spring Boot receives an HTTP request through the embedded server and passes it through Spring MVC to the matching controller.

## 17. JAR and WAR

### JAR

- Java Archive
- Preferred for modern Spring Boot applications
- Usually contains the embedded server
- Runs as a standalone application

```bash
./mvnw clean package
java -jar target/student-management-system.jar
```

### WAR

- Web Application Archive
- Usually deployed to an external servlet container
- Mainly used for legacy or organization-managed infrastructure

Use executable JAR packaging unless external deployment requirements specifically require WAR.

## 18. Request Flow

```text
HTTP Request
    ↓
Embedded Tomcat
    ↓
Spring MVC DispatcherServlet
    ↓
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
    ↓
HTTP Response
```

The controller handles HTTP concerns, the service handles business rules, and the repository handles persistence.

## 19. SDE-2 Guidelines

- Prefer constructor injection and `final` dependencies.
- Depend on interfaces where multiple implementations or clear boundaries are useful.
- Do not create unnecessary interfaces for every class.
- Keep controllers thin.
- Keep business logic in services.
- Keep data access in repositories.
- Avoid field injection.
- Avoid manual `getBean()` calls in business code.
- Place the main class in the root package.
- Externalize environment-specific configuration.
- Treat circular dependencies as a design problem.
- A class with many dependencies may violate the Single Responsibility Principle.
- Prefer executable JARs for Spring Boot services.

---

## Summary

```text
Spring Framework  -> Provides IoC, DI, MVC, transactions, and integration support
Spring Boot       -> Simplifies Spring using auto-configuration and starters
IoC               -> Spring controls object creation and lifecycle
DI                -> Dependencies are supplied from outside
Spring Bean       -> Object managed by the IoC container
Controller        -> Handles HTTP requests and responses
Service           -> Contains business logic and use cases
Repository        -> Performs data-access operations
@Primary          -> Defines a default bean
@Qualifier        -> Selects a specific bean
```

---

## Interview Questions

### 1. What is the difference between IoC and DI?

IoC is the principle of transferring object-management control to a container. DI is the technique of supplying dependencies to objects and is used by Spring to implement IoC.

### 2. Why is constructor injection preferred?

It makes dependencies explicit, supports `final` fields, ensures complete initialization, and allows unit tests without reflection or a Spring context.

### 3. Is `@Autowired` required on a constructor?

No. If a Spring bean has one constructor, Spring automatically uses it for injection.

### 4. What happens when multiple beans implement the same interface?

Spring reports bean ambiguity unless a bean is selected using `@Primary`, `@Qualifier`, or another explicit resolution mechanism.

### 5. What is the difference between `@Primary` and `@Qualifier`?

`@Primary` defines the general default. `@Qualifier` explicitly chooses a bean at a particular injection point and takes priority over `@Primary`.

### 6. What is component scanning?

Spring searches configured packages for component stereotypes and registers discovered classes as beans. By default, Spring Boot scans the main class package and its sub-packages.

### 7. What is auto-configuration?

Spring Boot configures common infrastructure based on classpath dependencies, existing beans, properties, and conditional configuration rules.

### 8. What is the difference between `@Component` and `@Service`?

Both register beans. `@Component` is general-purpose, while `@Service` communicates that a class contains business logic.

### 9. What is the difference between `@Controller` and `@RestController`?

`@Controller` commonly returns MVC views. `@RestController` writes returned values directly to the HTTP response body.

### 10. Are Spring beans singleton by default?

Yes. The default scope creates one bean instance per `ApplicationContext`.

### 11. Why should field injection be avoided?

It hides dependencies, prevents `final` fields, makes plain unit testing harder, and allows objects to be created in an incomplete state outside Spring.

### 12. What happens when a component is outside the scan path?

Spring does not register its bean, which can cause a missing-bean or dependency-injection failure.

### 13. What is the difference between JAR and WAR?

A Spring Boot JAR commonly runs standalone with an embedded server. A WAR is commonly deployed to an external servlet container.

### 14. Why are circular constructor dependencies a problem?

Neither object can be created first. Circular dependencies usually indicate responsibilities that should be redesigned or separated.

---

## Revision Checklist

- [ ] Spring Framework vs Spring Boot
- [ ] IoC and DI
- [ ] Spring Bean and `ApplicationContext`
- [ ] Constructor, setter, and field injection
- [ ] Tight coupling and loose coupling
- [ ] `@Primary` and `@Qualifier`
- [ ] Annotation and Java configuration
- [ ] Component scanning and auto-configuration
- [ ] Controller, service, and repository responsibilities
- [ ] Embedded Tomcat
- [ ] JAR vs WAR
- [ ] Core annotation application levels
