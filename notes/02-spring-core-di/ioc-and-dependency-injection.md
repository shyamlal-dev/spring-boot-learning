# Day 1: IoC and Dependency Injection

## 1. Tight Coupling

Tight coupling happens when one class directly creates the object of another class.

Problems:

- Classes strongly depend on each other.
- Implementation is difficult to replace.
- Unit testing becomes difficult.
- Code becomes harder to maintain.

## 2. Loose Coupling

Loose coupling means a class depends on an abstraction or receives its dependency from outside.

Benefits:

- Easy to change implementation.
- Easy to test.
- Better maintainability.
- Classes have fewer direct dependencies.

## 3. Inversion of Control (IoC)

IoC means the control of object creation and dependency management is transferred from the application code to the Spring container.

The Spring container:

- Creates objects.
- Manages objects.
- Injects dependencies.
- Maintains the bean lifecycle.

## 4. Dependency Injection (DI)

Dependency Injection is a technique where an object's required dependencies are provided from outside instead of being created inside the class.

Common DI types:

- Constructor Injection
- Setter Injection
- Field Injection

Constructor injection is generally preferred because dependencies are explicit and easier to test.

## 5. Spring Bean

A Spring Bean is an object created and managed by the Spring IoC container.

Classes marked with Spring stereotype annotations can be detected and registered as beans.

## 6. Important Annotations

### @Component

Marks a class as a Spring-managed component.

### @RestController

Marks a class as a REST controller.

It combines:

- `@Controller`
- `@ResponseBody`

### @RequestMapping

Maps HTTP requests to a controller or controller method.

It can be used at:

- Class level
- Method level

### @Autowired

Automatically injects a required dependency.

Constructor injection can work without `@Autowired` when the class has only one constructor.

### @Primary

Marks one bean as the default choice when multiple beans of the same type exist.

### @Qualifier

Specifies exactly which bean should be injected when multiple beans of the same type are available.

## 7. @Primary vs @Qualifier

- `@Primary` provides the default bean.
- `@Qualifier` selects a specific bean.
- `@Qualifier` takes priority over `@Primary`.
