# Spring Boot Basics: IoC, DI, Layers and Core Annotations

## 1. Spring Framework

Spring is a Java framework used to build enterprise and web applications.

Spring provides:

- Inversion of Control
- Dependency Injection
- Web and REST API development
- Database integration
- Transaction management
- Security
- Testing support

Spring helps us build applications that are:

- Loosely coupled
- Maintainable
- Testable
- Modular
- Scalable

---

## 2. Problem Without Spring

Consider a `Developer` class that directly creates a `Laptop` object:

```java
public class Developer {

    private final Laptop laptop = new Laptop();

    public void buildProject() {
        laptop.compile();
    }
}
```

Problems:

- `Developer` directly depends on `Laptop`.
- Implementation cannot be replaced easily.
- Unit testing becomes difficult.
- Mock dependencies cannot be passed easily.
- Code becomes tightly coupled.

Spring solves this problem using IoC and Dependency Injection.

---

## 3. Dependency

A dependency is an object required by another object to perform its work.

```java
public class Developer {

    private Laptop laptop;
}
```

Here:

```text
Developer depends on Laptop.
Laptop is a dependency of Developer.
```

Another example:

```java
public class OrderService {

    private PaymentService paymentService;
}
```

Here, `PaymentService` is a dependency of `OrderService`.

---

## 4. Inversion of Control

Inversion of Control, or IoC, means transferring the responsibility of creating and managing objects from application code to a framework or container.

Without Spring:

```java
Laptop laptop = new Laptop();
Developer developer = new Developer(laptop);
```

The programmer creates and connects objects.

With Spring:

```java
Developer developer = context.getBean(Developer.class);
```

Spring creates and connects objects.

```text
Without Spring:
Programmer controls object creation.

With Spring:
Spring IoC container controls object creation.
```

This transfer of control is called Inversion of Control.

---

## 5. Spring IoC Container

The Spring IoC container creates and manages application objects.

The container performs these operations:

1. Reads application configuration.
2. Scans application packages.
3. Identifies Spring-managed classes.
4. Creates objects.
5. Resolves their dependencies.
6. Injects required dependencies.
7. Stores and manages the objects.
8. Manages their lifecycle.
9. Provides objects when required.

Objects managed by Spring are called Spring Beans.

---

## 6. Spring Bean

A Spring Bean is an object created, configured and managed by the Spring IoC container.

```java
import org.springframework.stereotype.Component;

@Component
public class Laptop {

    public void compile() {
        System.out.println("Compiling using Laptop");
    }
}
```

`@Component` tells Spring to create and manage a `Laptop` object.

Conceptually, Spring performs:

```java
Laptop laptop = new Laptop();
```

However, the object is created and managed internally by Spring.

---

## 7. ApplicationContext

`ApplicationContext` represents the Spring IoC container.

```java
ApplicationContext context =
        SpringApplication.run(Application.class, args);
```

A bean can be retrieved using:

```java
Laptop laptop = context.getBean(Laptop.class);
```

Complete example:

```java
@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        ApplicationContext context =
                SpringApplication.run(Application.class, args);

        Laptop laptop = context.getBean(Laptop.class);
        laptop.compile();
    }
}
```

---

## 8. `getBean()`

`getBean()` asks the IoC container to provide a Spring-managed object.

By type:

```java
Laptop laptop = context.getBean(Laptop.class);
```

By bean name:

```java
Laptop laptop =
        (Laptop) context.getBean("laptop");
```

Meaning:

```text
Give me the Spring Bean of type Laptop.
```

Use cases:

- Understanding the IoC container
- Programmatic bean lookup
- Framework or infrastructure code
- Dynamic dependency lookup

In normal business classes, prefer Dependency Injection instead of repeatedly calling `getBean()`.

---

## 9. Dependency Injection

Dependency Injection, or DI, means providing the required dependency to an object from outside instead of allowing the object to create it.

Without DI:

```java
public class Developer {

    private final Laptop laptop = new Laptop();
}
```

With DI:

```java
public class Developer {

    private final Laptop laptop;

    public Developer(Laptop laptop) {
        this.laptop = laptop;
    }
}
```

Now, `Developer` does not create `Laptop`.

In a Spring application, the IoC container:

1. Creates the `Laptop` bean.
2. Creates the `Developer` bean.
3. Injects `Laptop` into `Developer`.

---

## 10. IoC vs Dependency Injection

### IoC

IoC answers:

```text
Who creates and manages application objects?
```

Answer:

```text
The Spring IoC container.
```

### Dependency Injection

DI answers:

```text
How does an object receive its dependencies?
```

Answer:

```text
Spring injects the required dependencies.
```

### Relationship

```text
IoC is the broader principle.
Dependency Injection is a technique used to achieve IoC.
```

---

## 11. Types of Dependency Injection

Spring commonly supports:

1. Constructor injection
2. Setter injection
3. Field injection

Important correction:

> Constructor-based and setter-based injection are the two major DI variants. Field injection is supported using annotations, but constructor injection is generally preferred for production code.

---

## 12. Constructor Injection

Constructor injection provides dependencies through a constructor.

```java
@Component
public class Laptop {

    public void compile() {
        System.out.println("Compiling using Laptop");
    }
}
```

```java
@Component
public class Developer {

    private final Laptop laptop;

    public Developer(Laptop laptop) {
        this.laptop = laptop;
    }

    public void buildProject() {
        laptop.compile();
    }
}
```

Spring performs:

```text
Create Laptop bean
        ↓
Find Developer class
        ↓
Detect Laptop dependency
        ↓
Pass Laptop to constructor
        ↓
Create Developer bean
```

### Is `@Autowired` required?

If the class has only one constructor, `@Autowired` is not required.

Preferred:

```java
@Component
public class Developer {

    private final Laptop laptop;

    public Developer(Laptop laptop) {
        this.laptop = laptop;
    }
}
```

This also works:

```java
@Component
public class Developer {

    private final Laptop laptop;

    @Autowired
    public Developer(Laptop laptop) {
        this.laptop = laptop;
    }
}
```

### Why constructor injection is preferred

- Required dependencies are clearly visible.
- Dependencies can be declared `final`.
- Object cannot be created without dependencies.
- Unit testing becomes easier.
- It supports immutability.
- It avoids hidden dependencies.
- It exposes circular dependencies early.
- The object remains completely initialized.

Use constructor injection for mandatory dependencies.

---

## 13. Setter Injection

Setter injection provides a dependency through a setter method.

```java
@Component
public class Developer {

    private Laptop laptop;

    @Autowired
    public void setLaptop(Laptop laptop) {
        this.laptop = laptop;
    }

    public void buildProject() {
        laptop.compile();
    }
}
```

Spring performs:

```text
Create Developer object
        ↓
Create Laptop object
        ↓
Call setLaptop()
        ↓
Inject Laptop into Developer
```

Use setter injection when:

- Dependency is optional.
- Dependency may need to be changed.
- Reconfiguration is required.

Disadvantages:

- Object may temporarily exist without its dependency.
- Required dependencies are not obvious.
- Field cannot remain `final`.
- The object may be incompletely initialized.

---

## 14. Field Injection

Field injection places `@Autowired` directly on a field.

```java
@Component
public class Developer {

    @Autowired
    private Laptop laptop;

    public void buildProject() {
        laptop.compile();
    }
}
```

Spring uses reflection to inject the dependency.

Advantages:

- Requires less code.
- Useful for quick demonstrations.

Disadvantages:

- Dependencies remain hidden.
- Field cannot be `final`.
- Unit testing becomes difficult.
- Object may be incomplete outside Spring.
- Class becomes strongly dependent on the Spring container.
- Too many fields can hide poor class design.

Recommendation:

> Avoid field injection in production code. Prefer constructor injection.

---

## 15. Constructor vs Setter vs Field Injection

### Constructor injection

```java
public Developer(Laptop laptop) {
    this.laptop = laptop;
}
```

Use for:

- Mandatory dependencies
- Immutable classes
- Production code

### Setter injection

```java
public void setLaptop(Laptop laptop) {
    this.laptop = laptop;
}
```

Use for:

- Optional dependencies
- Replaceable dependencies

### Field injection

```java
@Autowired
private Laptop laptop;
```

Use mainly for:

- Simple examples
- Legacy code

Avoid it in production code when possible.

---

## 16. Tight Coupling

Tight coupling occurs when one class directly creates or depends on a concrete implementation.

```java
public class Developer {

    private final Laptop laptop;

    public Developer() {
        this.laptop = new Laptop();
    }
}
```

Problems:

- Cannot easily replace `Laptop`.
- Difficult to pass a mock dependency.
- Changes in `Laptop` may affect `Developer`.
- Testing and maintenance become difficult.

---

## 17. Loose Coupling

Loose coupling means depending on an abstraction instead of a concrete implementation.

### Interface

```java
public interface Computer {

    void compile();
}
```

### Laptop implementation

```java
@Component
public class Laptop implements Computer {

    @Override
    public void compile() {
        System.out.println("Compiling using Laptop");
    }
}
```

### Developer

```java
@Component
public class Developer {

    private final Computer computer;

    public Developer(Computer computer) {
        this.computer = computer;
    }

    public void buildProject() {
        computer.compile();
    }
}
```

Now, `Developer` depends on `Computer`, not directly on `Laptop`.

Benefits:

- Implementation can be replaced.
- Mock dependencies can be used.
- Unit testing becomes easier.
- Classes become maintainable.
- Code follows the Dependency Inversion Principle.

---

## 18. Spring Configuration

Configuration tells Spring:

- Which objects should be created
- Which classes are Spring Beans
- How dependencies should be injected
- Which implementation should be selected
- Which application properties should be used

Spring configuration can be provided using:

1. XML configuration
2. Annotation-based configuration
3. Java-based configuration
4. Properties or YAML configuration

---

## 19. XML Configuration

Traditional Spring applications commonly used XML to define beans.

```xml
<beans>
    <bean id="laptop"
          class="com.ram.learning.Laptop" />

    <bean id="developer"
          class="com.ram.learning.Developer">
        <constructor-arg ref="laptop" />
    </bean>
</beans>
```

Meaning:

```text
laptop bean → Create Laptop object
developer bean → Create Developer object
constructor-arg → Inject Laptop into Developer
```

Advantages:

- Configuration is outside Java code.
- Bean relationships are explicit.

Disadvantages:

- Becomes verbose.
- Difficult to maintain in large applications.
- Less commonly used in modern Spring Boot projects.

---

## 20. Annotation-Based Configuration

Annotations tell Spring which classes should be managed.

```java
@Component
public class Laptop {
}
```

```java
@Component
public class Developer {

    private final Laptop laptop;

    public Developer(Laptop laptop) {
        this.laptop = laptop;
    }
}
```

Common annotations:

```text
@Component
@Service
@Repository
@Controller
@RestController
@Autowired
@Primary
@Qualifier
```

Advantages:

- Less configuration
- Easy to understand
- Configuration remains close to the class
- Common in modern Spring Boot applications

---

## 21. Java-Based Configuration

Beans can be defined explicitly using Java methods.

```java
@Configuration
public class AppConfig {

    @Bean
    public Laptop laptop() {
        return new Laptop();
    }

    @Bean
    public Developer developer(Laptop laptop) {
        return new Developer(laptop);
    }
}
```

### `@Configuration`

Marks a class as a source of bean definitions.

### `@Bean`

Registers the object returned by a method as a Spring Bean.

Use Java configuration when:

- A third-party class cannot be annotated.
- Custom bean-creation logic is required.
- Multiple differently configured beans are required.
- Bean creation should be explicit.

---

## 22. Properties Configuration

Spring Boot commonly uses:

```text
application.properties
```

or:

```text
application.yml
```

Example:

```properties
spring.application.name=hello-spring-boot
server.port=8080
```

Change the server port:

```properties
server.port=9090
```

Common uses:

- Server port
- Database URL
- Database credentials
- Logging configuration
- Active profiles
- Application name
- Security properties
- Custom configuration values

Important correction:

> `application.properties` stores application configuration values. Beans are normally defined using annotations, Java configuration or XML.

---

## 23. Application Layers

A Spring Boot web application commonly follows:

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

This provides separation of concerns.

---

## 24. Controller Layer

The controller handles HTTP requests and responses.

```java
@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentService.getStudent(id);
    }
}
```

Responsibilities:

- Receive HTTP requests
- Read path variables
- Read query parameters
- Read request bodies
- Perform request-level validation
- Call the service layer
- Return HTTP responses

A controller should not contain complex business logic.

---

## 25. Service Layer

The service layer contains business logic.

```java
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student getStudent(Long id) {
        return studentRepository.findById(id);
    }
}
```

Responsibilities:

- Implement business rules
- Validate business conditions
- Perform calculations
- Coordinate repository operations
- Define application use cases
- Manage transactions when required

---

## 26. Repository Layer

The repository layer handles data-access operations.

```java
@Repository
public class StudentRepository {

    public Student findById(Long id) {
        return new Student(id, "Ram");
    }
}
```

Responsibilities:

- Save data
- Retrieve data
- Update data
- Delete data
- Execute database queries
- Convert database results into application objects

The repository should not contain HTTP handling or complex business logic.

---

## 27. Separation of Concerns

Each layer has one main responsibility:

```text
Controller → HTTP request and response handling
Service    → Business logic
Repository → Database operations
```

Benefits:

- Easier maintenance
- Better testability
- Smaller classes
- Reusable business logic
- Independent changes
- Clear application structure

---

## 28. Web Application

A web application communicates with clients using HTTP.

Examples:

- E-commerce application
- Banking portal
- Tax management system
- Employee management system
- REST API

Basic flow:

```text
Browser or Client
       ↓ HTTP Request
Web Server
       ↓
Application
       ↓
Database
       ↓ HTTP Response
Browser or Client
```

Spring Boot web applications commonly use Spring MVC to handle requests.

---

## 29. Apache Tomcat

Apache Tomcat is a web server and Servlet container used to run Java web applications.

Tomcat handles:

- HTTP connections
- HTTP requests and responses
- Servlet execution
- Web application deployment
- Request routing

Traditional Spring applications may require an externally installed Tomcat server.

Spring Boot can include embedded Tomcat.

This allows the application to run directly:

```bash
java -jar application.jar
```

Important correction:

> Tomcat is primarily a Servlet container and web server. It is not a complete Jakarta EE application server.

---

## 30. JAR File

JAR full form:

```text
Java Archive
```

A JAR file packages:

- Compiled Java classes
- Resources
- Configuration files
- Metadata
- Application dependencies in an executable Spring Boot JAR

Example:

```text
hello-spring-boot.jar
```

Build:

```bash
./mvnw clean package
```

Generated file:

```text
target/hello-spring-boot.jar
```

Run:

```bash
java -jar target/hello-spring-boot.jar
```

JAR is commonly used for:

- Standalone Spring Boot applications
- REST APIs
- Microservices
- Container deployments
- Applications using embedded Tomcat

---

## 31. WAR File

WAR full form:

```text
Web Application Archive
```

A WAR packages a Java web application for deployment to an external web server or application server.

Example:

```text
employee-management.war
```

WAR packaging may be used when:

- The organization provides an external server.
- Legacy infrastructure requires WAR files.
- Multiple applications are deployed to a managed server.
- External Tomcat deployment is mandatory.

---

## 32. JAR vs WAR

### JAR

```text
Full form: Java Archive
Usage: Standalone application
Server: Usually embedded
Run: java -jar application.jar
Preferred for: Modern Spring Boot APIs and microservices
```

### WAR

```text
Full form: Web Application Archive
Usage: Traditional web application deployment
Server: Usually external
Deploy: Upload the WAR to the server
Preferred for: Legacy or externally managed server environments
```

---

## 33. Spring Framework vs Spring Boot

### Spring Framework

Provides:

- IoC container
- Dependency Injection
- Spring MVC
- Transaction management
- Database integration
- Testing support
- Security integration

Spring Framework may require more manual configuration.

### Spring Boot

Spring Boot is built on top of Spring Framework.

It provides:

- Auto-configuration
- Starter dependencies
- Embedded server
- Sensible defaults
- Production-ready features
- Faster project setup
- Standalone execution

Important:

> Spring Boot does not replace Spring Framework. It simplifies Spring Framework development and configuration.

---

## 34. What is Spring Boot?

Spring Boot helps developers create standalone, production-ready Spring applications quickly.

It reduces:

- Manual configuration
- XML configuration
- Dependency setup
- External server setup
- Project setup time

Basic application:

```java
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

Run:

```bash
./mvnw spring-boot:run
```

Package and run:

```bash
./mvnw clean package
java -jar target/application.jar
```

---

## 35. `@SpringBootApplication`

`@SpringBootApplication` marks the main Spring Boot class.

```java
@SpringBootApplication
public class Application {
}
```

It combines:

```java
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
```

### `@SpringBootConfiguration`

Marks the class as a Spring Boot configuration class.

### `@EnableAutoConfiguration`

Allows Spring Boot to configure the application based on:

- Classpath dependencies
- Existing beans
- Application properties
- Framework defaults

### `@ComponentScan`

Scans the main class package and sub-packages for Spring components.

Recommended structure:

```text
com.ram.learning
├── Application.java
├── controller/
├── service/
├── repository/
├── model/
└── configuration/
```

Place the main class in the root package so that component scanning can find all sub-packages.

---

## 36. `@Component`

`@Component` marks a class as a general Spring-managed component.

```java
@Component
public class Laptop {

    public void compile() {
        System.out.println("Compiling...");
    }
}
```

Spring finds the class during component scanning and creates its bean.

Use cases:

- General utility components
- Notification processors
- Email senders
- Classes that do not belong to a specialized layer

---

## 37. Component Stereotype Annotations

The following are specialized forms of `@Component`:

```text
@Component
├── @Controller
├── @RestController
├── @Service
└── @Repository
```

### `@Component`

Used for general Spring-managed classes.

### `@Controller`

Used for MVC controllers that generally return views.

### `@RestController`

Used for REST API controllers.

### `@Service`

Used for business-logic classes.

### `@Repository`

Used for data-access classes.

These annotations improve code readability by communicating the responsibility of each class.

---

## 38. `@Autowired`

### Purpose

`@Autowired` asks Spring to automatically find and inject a matching bean.

Spring generally resolves the dependency by type.

### Field injection

```java
@Autowired
private Laptop laptop;
```

### Setter injection

```java
@Autowired
public void setLaptop(Laptop laptop) {
    this.laptop = laptop;
}
```

### Constructor injection

```java
@Autowired
public Developer(Laptop laptop) {
    this.laptop = laptop;
}
```

If the class has only one constructor, `@Autowired` is optional:

```java
@Component
public class Developer {

    private final Laptop laptop;

    public Developer(Laptop laptop) {
        this.laptop = laptop;
    }
}
```

Preferred practice:

- Use constructor injection.
- Avoid field injection.
- Use setter injection mainly for optional dependencies.

---

## 39. `@RestController`

`@RestController` marks a class as a REST API controller.

```java
@RestController
public class HelloController {

    @RequestMapping("/")
    public String home() {
        return "Application is running";
    }
}
```

It tells Spring to:

- Create a bean for the class.
- Treat the class as a web controller.
- Write returned values directly into the HTTP response body.

Conceptually:

```text
@RestController = @Controller + @ResponseBody
```

Use it for:

- REST APIs
- JSON-based backend services
- Microservices
- Web-service endpoints

---

## 40. `@RequestMapping`

`@RequestMapping` maps HTTP requests to controller classes or methods.

### Method-level mapping

```java
@RequestMapping("/")
public String home() {
    return "Application is running";
}
```

### Class-level mapping

```java
@RestController
@RequestMapping("/students")
public class StudentController {
}
```

### Combined mapping

```java
@RestController
@RequestMapping("/students")
public class StudentController {

    @RequestMapping("/all")
    public String getStudents() {
        return "Students";
    }
}
```

Final URL:

```text
/students/all
```

It can also define an HTTP method:

```java
@RequestMapping(
        value = "/students",
        method = RequestMethod.GET
)
```

For better readability, prefer:

```text
@GetMapping
@PostMapping
@PutMapping
@DeleteMapping
@PatchMapping
```

Example:

```java
@GetMapping("/students")
public String getStudents() {
    return "Students";
}
```

---

## 41. Multiple Bean Ambiguity

Suppose an interface has two implementations.

```java
public interface Computer {

    void compile();
}
```

```java
@Component
public class Laptop implements Computer {

    @Override
    public void compile() {
        System.out.println("Compiling using Laptop");
    }
}
```

```java
@Component
public class Desktop implements Computer {

    @Override
    public void compile() {
        System.out.println("Compiling using Desktop");
    }
}
```

Now `Developer` requires `Computer`:

```java
@Component
public class Developer {

    private final Computer computer;

    public Developer(Computer computer) {
        this.computer = computer;
    }
}
```

Spring finds two matching beans:

```text
Laptop
Desktop
```

Spring cannot automatically decide which bean should be injected.

This is called bean ambiguity.

It can be resolved using:

- `@Primary`
- `@Qualifier`

---

## 42. `@Primary`

`@Primary` marks one bean as the default choice.

```java
@Component
@Primary
public class Laptop implements Computer {

    @Override
    public void compile() {
        System.out.println("Compiling using Laptop");
    }
}
```

Now Spring selects `Laptop` by default:

```java
@Component
public class Developer {

    private final Computer computer;

    public Developer(Computer computer) {
        this.computer = computer;
    }
}
```

Use `@Primary` when one implementation should usually be selected throughout the application.

Correct:

```java
@Primary
```

Incorrect:

```java
@primary
```

Java annotations are case-sensitive.

---

## 43. `@Qualifier`

`@Qualifier` explicitly selects a particular bean.

```java
@Component
public class Developer {

    private final Computer computer;

    public Developer(
            @Qualifier("laptop") Computer computer
    ) {
        this.computer = computer;
    }
}
```

Default bean names generally use the class name with the first letter in lowercase:

```text
Laptop  → laptop
Desktop → desktop
```

Select Desktop:

```java
public Developer(
        @Qualifier("desktop") Computer computer
) {
    this.computer = computer;
}
```

Use `@Qualifier` when a particular class requires a specific implementation.

---

## 44. `@Primary` vs `@Qualifier`

### `@Primary`

Defines the default bean.

```java
@Component
@Primary
public class Laptop implements Computer {
}
```

Use when:

- One implementation is generally preferred.
- Most injection points should use the same implementation.

### `@Qualifier`

Selects a specific bean at the injection point.

```java
public Developer(
        @Qualifier("desktop") Computer computer
) {
    this.computer = computer;
}
```

Use when:

- A particular class requires a specific implementation.
- Different classes need different implementations.
- Bean selection should be explicit.

If both are present, a matching `@Qualifier` selects the specified bean.

---

## 45. Complete DI Example

### Computer interface

```java
package com.ram.learning.hello;

public interface Computer {

    void compile();
}
```

### Laptop

```java
package com.ram.learning.hello;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class Laptop implements Computer {

    @Override
    public void compile() {
        System.out.println("Compiling using Laptop");
    }
}
```

### Desktop

```java
package com.ram.learning.hello;

import org.springframework.stereotype.Component;

@Component
public class Desktop implements Computer {

    @Override
    public void compile() {
        System.out.println("Compiling using Desktop");
    }
}
```

### Developer

```java
package com.ram.learning.hello;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Developer {

    private final Computer computer;

    public Developer(
            @Qualifier("laptop") Computer computer
    ) {
        this.computer = computer;
    }

    public void buildProject() {
        computer.compile();
    }
}
```

### Main application

```java
package com.ram.learning.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class HelloSpringBootApplication {

    public static void main(String[] args) {

        ApplicationContext context =
                SpringApplication.run(
                        HelloSpringBootApplication.class,
                        args
                );

        Developer developer =
                context.getBean(Developer.class);

        developer.buildProject();
    }
}
```

Output:

```text
Compiling using Laptop
```

---

## 46. Bean Creation Flow

For the previous example:

```text
Spring Boot application starts
            ↓
Component scanning begins
            ↓
Spring finds Laptop
            ↓
Spring creates Laptop bean
            ↓
Spring finds Desktop
            ↓
Spring creates Desktop bean
            ↓
Spring finds Developer
            ↓
Spring detects Computer dependency
            ↓
Spring reads @Qualifier("laptop")
            ↓
Spring injects Laptop
            ↓
Developer bean is created
```

---

## 47. Important Corrections

### Correction 1: DI types

Constructor and setter injection are the two major DI variants.

Field injection is supported but is generally not preferred.

### Correction 2: Constructor injection

`@Autowired` is not required when the class has only one constructor.

### Correction 3: Annotation case

Correct:

```java
@Primary
```

Incorrect:

```java
@primary
```

### Correction 4: Properties files

`application.properties` contains application configuration values.

It does not normally define Java beans.

### Correction 5: Tomcat

Tomcat is primarily a web server and Servlet container.

It is not a complete Jakarta EE application server.

### Correction 6: Spring Boot

Spring Boot does not replace Spring Framework.

It simplifies Spring Framework configuration and application startup.

### Correction 7: `getBean()`

Application classes should normally receive dependencies using DI.

Frequent manual `getBean()` calls can hide dependencies and behave like the Service Locator pattern.

### Correction 8: Repository layer

The repository layer should contain data-access operations, not business logic.

### Correction 9: Controller layer

The controller should handle requests and responses but should not contain complex business logic.

---

## 48. SDE-2 Important Points

- Prefer constructor injection for mandatory dependencies.
- Use `final` for injected mandatory dependencies.
- Program to interfaces, not concrete classes.
- Avoid field injection in production code.
- Keep controllers thin.
- Place business logic in the service layer.
- Place database operations in the repository layer.
- Use `@Primary` for a general default implementation.
- Use `@Qualifier` for explicit bean selection.
- Place the main class in the root package.
- Use executable JAR packaging for most Spring Boot services.
- Use WAR only when external deployment infrastructure requires it.
- Avoid calling `getBean()` from normal business classes.
- Externalize settings using properties, YAML and environment variables.
- Follow separation of concerns.
- Depend on abstractions to achieve loose coupling.
- A class with many dependencies may be violating the Single Responsibility Principle.
- Constructor injection makes unit testing easier.
- Spring Beans are singleton-scoped by default.
- Spring singleton means one bean per `ApplicationContext`, not one object for the entire JVM.

---

# Summary

## Spring Framework

- Provides IoC, DI, MVC, database and transaction infrastructure.
- Helps build maintainable enterprise applications.

## Spring Boot

- Built on top of Spring Framework.
- Provides auto-configuration and starter dependencies.
- Supports embedded servers.
- Reduces manual setup.

## IoC

- Spring controls object creation.
- Spring manages bean lifecycle.
- `ApplicationContext` represents the IoC container.

## Dependency Injection

- An object's dependencies are provided from outside.
- Constructor injection is preferred.
- Setter injection is useful for optional dependencies.
- Field injection should generally be avoided.

## Loose Coupling

- Depend on interfaces instead of concrete classes.
- Makes implementation replacement and testing easier.

## Layers

```text
Controller → Handles HTTP requests and responses
Service    → Contains business logic
Repository → Handles data access
```

## Core Annotations

```text
@Component
→ General Spring-managed component

@Service
→ Business-logic component

@Repository
→ Data-access component

@RestController
→ REST API controller

@RequestMapping
→ Maps HTTP requests to a class or method

@Autowired
→ Requests automatic dependency injection

@Primary
→ Selects the default bean

@Qualifier
→ Explicitly selects a particular bean

@Configuration
→ Defines a configuration class

@Bean
→ Registers a method's returned object as a bean
```

## Packaging

```text
JAR
→ Java Archive
→ Usually contains embedded server
→ Preferred for modern Spring Boot applications

WAR
→ Web Application Archive
→ Usually deployed to an external server
```

---

# Interview Questions and Answers

## 1. What is Inversion of Control?

IoC is a design principle in which object-creation and object-management responsibility is transferred from application code to a framework or container.

In Spring, the IoC container creates and manages Spring Beans.

---

## 2. What is Dependency Injection?

Dependency Injection is a technique in which an object's required dependencies are provided from outside instead of being created by the object itself.

---

## 3. What is the difference between IoC and DI?

IoC is the broader principle of transferring object-management control to a container.

DI is a technique used to implement IoC by injecting dependencies into objects.

---

## 4. What is a Spring Bean?

A Spring Bean is an object created, configured and managed by the Spring IoC container.

---

## 5. What is `ApplicationContext`?

`ApplicationContext` is a Spring IoC container interface that provides:

- Bean creation
- Dependency injection
- Bean lifecycle management
- Configuration management
- Event support

---

## 6. What does `getBean()` do?

`getBean()` retrieves a Spring-managed object from the IoC container.

```java
Developer developer =
        context.getBean(Developer.class);
```

---

## 7. Which dependency injection type is preferred?

Constructor injection is preferred for mandatory dependencies because it:

- Makes dependencies explicit
- Supports `final` fields
- Improves testability
- Supports immutability
- Ensures complete object initialization

---

## 8. Is `@Autowired` mandatory on a constructor?

No.

If a class has only one constructor, Spring automatically uses that constructor for dependency injection.

---

## 9. Why should field injection be avoided?

Field injection:

- Hides dependencies
- Makes testing difficult
- Does not support proper immutability
- Couples the class to the Spring container
- Allows incomplete object creation outside Spring

---

## 10. What is loose coupling?

Loose coupling means depending on abstractions such as interfaces instead of concrete implementations.

It allows the implementation to be replaced without changing the dependent class.

---

## 11. What happens when multiple beans implement the same interface?

Spring finds multiple matching beans and cannot automatically decide which bean should be injected.

This produces a bean ambiguity error.

It can be resolved using:

- `@Primary`
- `@Qualifier`

---

## 12. What is the difference between `@Primary` and `@Qualifier`?

`@Primary` marks one bean as the default implementation.

`@Qualifier` explicitly selects a particular bean at an injection point.

---

## 13. Which has priority: `@Primary` or `@Qualifier`?

A matching `@Qualifier` has priority because it explicitly identifies the required bean.

---

## 14. What is the difference between `@Component` and `@Service`?

Both register Spring Beans.

`@Component` is general-purpose.

`@Service` is a specialized stereotype used for business-logic classes.

---

## 15. What is the difference between `@Controller` and `@RestController`?

`@Controller` is generally used for MVC controllers that return views.

`@RestController` returns data directly in the HTTP response body.

Conceptually:

```text
@RestController = @Controller + @ResponseBody
```

---

## 16. What does `@RequestMapping` do?

`@RequestMapping` maps an HTTP request path to a controller class or method.

It may also specify:

- HTTP method
- Headers
- Parameters
- Consumed content type
- Produced content type

---

## 17. What is the role of the controller layer?

The controller layer:

- Receives HTTP requests
- Reads request data
- Calls the service layer
- Returns HTTP responses

It should not contain complex business logic.

---

## 18. What is the role of the service layer?

The service layer contains:

- Business rules
- Application use cases
- Data processing
- Coordination between repositories
- Transaction boundaries

---

## 19. What is the role of the repository layer?

The repository layer handles:

- Saving data
- Retrieving data
- Updating data
- Deleting data
- Executing database queries

---

## 20. What is separation of concerns?

Separation of concerns means assigning different responsibilities to different classes or layers.

```text
Controller → HTTP handling
Service    → Business logic
Repository → Data access
```

---

## 21. What is the difference between Spring and Spring Boot?

Spring provides core features such as IoC, DI, MVC and transaction management.

Spring Boot simplifies Spring development using:

- Auto-configuration
- Starter dependencies
- Embedded servers
- Sensible defaults

---

## 22. Does Spring Boot replace Spring Framework?

No.

Spring Boot is built on top of Spring Framework and simplifies its configuration and usage.

---

## 23. What is embedded Tomcat?

Embedded Tomcat is a Tomcat server packaged inside the Spring Boot application.

It allows the application to run without manually installing and configuring an external server.

---

## 24. What is the difference between JAR and WAR?

JAR:

- Java Archive
- Used for standalone applications
- Commonly includes embedded Tomcat
- Runs using `java -jar`

WAR:

- Web Application Archive
- Deployed to an external server
- Common in legacy or managed environments

---

## 25. Why is constructor injection better for testing?

Dependencies can be passed directly:

```java
Computer mockComputer = mock(Computer.class);
Developer developer = new Developer(mockComputer);
```

The test does not require Spring or reflection-based field injection.

---

## 26. What is component scanning?

Component scanning is the process through which Spring searches packages for classes annotated with:

```text
@Component
@Service
@Repository
@Controller
@RestController
```

Spring creates beans for the detected classes.

---

## 27. What is auto-configuration?

Auto-configuration allows Spring Boot to configure an application automatically based on:

- Dependencies on the classpath
- Existing beans
- Application properties
- Framework defaults

---

## 28. What happens if a component is outside the component-scan package?

Spring may not detect the component, and its bean will not be created.

This can result in a missing-bean or dependency-injection error.

Place the main application class in the root package or configure component scanning explicitly.

---

## 29. Are Spring Beans singleton by default?

Yes.

By default, one bean instance is created per `ApplicationContext`.

This is different from the classic Singleton design pattern, which usually means one instance per JVM or class loader.

---

## 30. Can constructor injection create a circular dependency?

Yes.

Example:

```text
ServiceA requires ServiceB
ServiceB requires ServiceA
```

Spring generally cannot resolve constructor-based circular dependencies.

The correct solution is usually to redesign the classes and remove the circular dependency.

---

# Practice Tasks

1. Create a `Computer` interface.
2. Create `Laptop` and `Desktop` implementations.
3. Mark both implementations as Spring Beans.
4. Create a `Developer` component.
5. Inject `Computer` using constructor injection.
6. Observe the multiple-bean ambiguity.
7. Resolve ambiguity using `@Primary`.
8. Replace `@Primary` with `@Qualifier`.
9. Implement setter injection for comparison.
10. Implement field injection only for comparison.
11. Create `/` and `/hello` endpoints.
12. Create a service class.
13. Move response-generation logic from controller to service.
14. Retrieve a bean using `ApplicationContext`.
15. Build the application as a JAR.
16. Run the JAR using `java -jar`.
17. Change the server port using `application.properties`.
18. Explain controller, service and repository flow without notes.

---

# Revision Checklist

Before marking this topic complete, verify that you can explain:

- Spring Framework
- Spring Boot
- IoC
- Dependency Injection
- Dependency
- Spring Bean
- IoC container
- ApplicationContext
- `getBean()`
- Constructor injection
- Setter injection
- Field injection
- Tight coupling
- Loose coupling
- XML configuration
- Annotation configuration
- Java configuration
- Properties configuration
- Controller layer
- Service layer
- Repository layer
- Separation of concerns
- Web application
- Apache Tomcat
- Embedded Tomcat
- JAR
- WAR
- `@SpringBootApplication`
- `@Component`
- `@Service`
- `@Repository`
- `@RestController`
- `@RequestMapping`
- `@Autowired`
- `@Primary`
- `@Qualifier`
- Multiple-bean ambiguity
- Component scanning
- Auto-configuration