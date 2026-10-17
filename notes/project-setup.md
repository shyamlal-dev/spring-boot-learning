# Spring Boot Project Setup Using GitHub Codespaces

## 1. Create GitHub Repository

Create a new GitHub repository with the following configuration:

```text
Repository Name: spring-boot-learning
Visibility: Public
README: Yes
.gitignore: Java
License: None
```

Open the repository and create a Codespace:

```text
Code → Codespaces → Create codespace on main
```

---

## 2. Verify Repository Location

Check the current working directory:

```bash
pwd
```

Expected path:

```text
/workspaces/spring-boot-learning
```

To return to the repository root at any time:

```bash
cd /workspaces/spring-boot-learning
```

---

## 3. Create Learning Folders

Run these commands from the repository root:

```bash
mkdir -p 01-first-spring-boot-app
mkdir -p 02-rest-api
mkdir -p 03-layered-architecture
mkdir -p 04-spring-data-jpa
mkdir -p 05-validation-exception
mkdir -p 06-testing
mkdir -p 07-spring-security
mkdir -p notes
```

Repository structure:

```text
spring-boot-learning/
├── 01-first-spring-boot-app/
├── 02-rest-api/
├── 03-layered-architecture/
├── 04-spring-data-jpa/
├── 05-validation-exception/
├── 06-testing/
├── 07-spring-security/
├── notes/
├── .gitignore
└── README.md
```

---

## 4. Keep Empty Folders Visible in Git

Git does not track empty folders.

Create a `.gitkeep` file inside each future learning folder:

```bash
touch 02-rest-api/.gitkeep
touch 03-layered-architecture/.gitkeep
touch 04-spring-data-jpa/.gitkeep
touch 05-validation-exception/.gitkeep
touch 06-testing/.gitkeep
touch 07-spring-security/.gitkeep
touch notes/.gitkeep
```

When an actual project or note is added to a folder, remove its `.gitkeep` file.

Example:

```bash
rm notes/.gitkeep
```

---

## 5. Generate the First Spring Boot Project

Open Spring Initializr and use the following configuration:

```text
Project: Maven
Language: Java
Spring Boot: Stable version
Group: com.ram.learning
Artifact: hello-spring-boot
Name: hello-spring-boot
Package Name: com.ram.learning.hello
Packaging: Jar
Configuration: Properties
Java: 21
```

Add dependency:

```text
Spring Web
```

Click **Generate** to download:

```text
hello-spring-boot.zip
```

---

## 6. Upload the ZIP File to Codespace

In the Codespace Explorer:

```text
01-first-spring-boot-app
→ Right-click
→ Upload
→ Select hello-spring-boot.zip
```

The uploaded ZIP should be available at:

```text
01-first-spring-boot-app/hello-spring-boot.zip
```

---

## 7. Extract the Spring Boot Project

Return to the repository root:

```bash
cd /workspaces/spring-boot-learning
```

Create the destination folder:

```bash
mkdir -p 01-first-spring-boot-app/hello-spring-boot
```

Extract the ZIP:

```bash
unzip 01-first-spring-boot-app/hello-spring-boot.zip \
-d 01-first-spring-boot-app/hello-spring-boot
```

Delete the ZIP after extraction:

```bash
rm 01-first-spring-boot-app/hello-spring-boot.zip
```

---

## 8. Verify Project Structure

Check the extracted project:

```bash
ls -la 01-first-spring-boot-app/hello-spring-boot
```

Expected structure:

```text
hello-spring-boot/
├── .mvn/
├── src/
├── .gitattributes
├── .gitignore
├── HELP.md
├── mvnw
├── mvnw.cmd
└── pom.xml
```

The `pom.xml` file must be directly available at:

```text
01-first-spring-boot-app/hello-spring-boot/pom.xml
```

---

## 9. Fix Extra Nested Folder If Required

Sometimes extraction may create an extra nested folder:

```text
hello-spring-boot/
└── hello-spring-boot/
    ├── pom.xml
    └── src/
```

Move all project files one level up:

```bash
rsync -a \
01-first-spring-boot-app/hello-spring-boot/hello-spring-boot/ \
01-first-spring-boot-app/hello-spring-boot/
```

Delete the extra folder:

```bash
rm -rf 01-first-spring-boot-app/hello-spring-boot/hello-spring-boot
```

Verify again:

```bash
ls -la 01-first-spring-boot-app/hello-spring-boot
```

> Run these commands only when an extra nested folder exists.

---

## 10. Open the Project Directory

Move inside the Spring Boot project:

```bash
cd /workspaces/spring-boot-learning/01-first-spring-boot-app/hello-spring-boot
```

Verify Java:

```bash
java -version
```

Verify Maven Wrapper:

```bash
./mvnw -version
```

If permission is denied:

```bash
chmod +x mvnw
```

---

## 11. Build and Test the Application

Run the tests:

```bash
./mvnw clean test
```

Expected result:

```text
BUILD SUCCESS
```

Run the Spring Boot application:

```bash
./mvnw spring-boot:run
```

Successful startup logs:

```text
Tomcat started on port 8080
Started HelloSpringBootApplication
```

Stop the application by pressing:

```text
Ctrl + C
```

`Ctrl + C` must be pressed on the keyboard, not typed in the terminal.

---

## 12. Create the First REST Controller

Create this file:

```text
src/main/java/com/ram/learning/hello/HelloController.java
```

Add the following code:

```java
package com.ram.learning.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String home() {
        return "My first Spring Boot application is running!";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello Spring Boot!";
    }
}
```

Restart the application:

```bash
./mvnw spring-boot:run
```

---

## 13. Test the Endpoints

Open another terminal in the same Codespace.

Test the root endpoint:

```bash
curl http://localhost:8080
```

Expected output:

```text
My first Spring Boot application is running!
```

Test the `/hello` endpoint:

```bash
curl http://localhost:8080/hello
```

Expected output:

```text
Hello Spring Boot!
```

Different terminal paths do not cause any problem because both terminals run inside the same Codespace.

---

## 14. Configure Automatic Port Forwarding

Create a `.devcontainer` folder at the repository root:

```bash
cd /workspaces/spring-boot-learning
mkdir -p .devcontainer
```

Create the configuration file:

```text
.devcontainer/devcontainer.json
```

Add:

```json
{
  "forwardPorts": [8080],
  "portsAttributes": {
    "8080": {
      "label": "Spring Boot Application",
      "onAutoForward": "openBrowser"
    }
  }
}
```

Apply the configuration:

```text
Ctrl + Shift + P
→ Codespaces: Rebuild Container
```

After rebuilding, start the application again:

```bash
cd /workspaces/spring-boot-learning/01-first-spring-boot-app/hello-spring-boot
./mvnw spring-boot:run
```

The application URL will appear in the **Ports** tab.

Example:

```text
https://<codespace-name>-8080.app.github.dev
```

Test in the browser:

```text
https://<codespace-name>-8080.app.github.dev/
```

```text
https://<codespace-name>-8080.app.github.dev/hello
```

Do not use the laptop's `localhost:8080` when working through browser-based Codespaces. Use the forwarded URL from the Ports tab.

---

## 15. Install Recommended VS Code Extensions

Install these extension packs:

```text
Extension Pack for Java
Publisher: Microsoft
```

```text
Spring Boot Extension Pack
Publisher: VMware
```

The Spring Boot Extension Pack includes tools such as:

- Spring Boot Tools
- Spring Boot Dashboard
- Spring Initializr Java Support

---

## 16. Format Java Code

Format the current Java file:

```text
Shift + Alt + F
```

Alternatively:

```text
Right-click inside the Java file
→ Format Document
```

To enable format on save, add this to VS Code settings:

```json
{
  "[java]": {
    "editor.defaultFormatter": "redhat.java",
    "editor.formatOnSave": true
  }
}
```

---

## 17. Commit and Push Changes

Always return to the repository root before committing:

```bash
cd /workspaces/spring-boot-learning
```

Check all changes:

```bash
git status
```

Stage the changes:

```bash
git add .
```

Commit:

```bash
git commit -m "feat: add first Spring Boot application and Codespaces configuration"
```

Push to GitHub:

```bash
git push
```

Stopping the Spring Boot application before committing is not compulsory, but it keeps the terminal free.

---

## 18. Important Commands

### Check current location

```bash
pwd
```

### Return to repository root

```bash
cd /workspaces/spring-boot-learning
```

### Open the first Spring Boot project

```bash
cd /workspaces/spring-boot-learning/01-first-spring-boot-app/hello-spring-boot
```

### Run tests

```bash
./mvnw clean test
```

### Start the application

```bash
./mvnw spring-boot:run
```

### Stop the application

```text
Ctrl + C
```

### Check Git changes

```bash
git status
```

### Commit and push

```bash
git add .
git commit -m "your commit message"
git push
```

---

## 19. Final Repository Structure

```text
spring-boot-learning/
│
├── .devcontainer/
│   └── devcontainer.json
│
├── 01-first-spring-boot-app/
│   └── hello-spring-boot/
│       ├── .mvn/
│       ├── src/
│       ├── mvnw
│       ├── mvnw.cmd
│       └── pom.xml
│
├── 02-rest-api/
│   └── .gitkeep
├── 03-layered-architecture/
│   └── .gitkeep
├── 04-spring-data-jpa/
│   └── .gitkeep
├── 05-validation-exception/
│   └── .gitkeep
├── 06-testing/
│   └── .gitkeep
├── 07-spring-security/
│   └── .gitkeep
├── notes/
│   └── setup.md
├── .gitignore
└── README.md
```

---

## 20. Key Points

- Each topic folder will contain an independent Spring Boot project.
- Each project will have its own `pom.xml`, `src`, and Maven Wrapper.
- Run Maven commands from the project directory.
- Run Git commands from the repository root.
- The `target` folder should not be committed.
- The `.devcontainer` configuration should be committed.
- Port `8080` will automatically forward for future projects in this repository.
- Only one application can run on port `8080` at a time.
- Stop the current application before starting another application on the same port.