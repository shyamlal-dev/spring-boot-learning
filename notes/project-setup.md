# Spring Boot Setup Using GitHub Codespaces

This guide explains how to create a Spring Boot learning repository, upload a Spring Initializr project, run it in GitHub Codespaces, configure port forwarding, and push changes to GitHub.

---

# 1. Create a GitHub Repository

Create a new GitHub repository with the following settings:

```text
Repository Name: spring-boot-learning
Visibility: Public
Add README: Yes
.gitignore: Java
License: None
```

The Java `.gitignore` does not ignore Java source files.

It ignores generated files such as:

```text
target/
*.class
IDE configuration files
```

Create a Codespace:

```text
Repository → Code → Codespaces → Create codespace on main
```

---

# 2. Check the Current Directory

Run:

```bash
pwd
```

## Meaning

`pwd` stands for:

```text
Print Working Directory
```

It displays the directory in which the terminal is currently working.

Expected repository root:

```text
/workspaces/spring-boot-learning
```

If the terminal is inside the first Spring Boot project, the output will be:

```text
/workspaces/spring-boot-learning/01-first-spring-boot-app/hello-spring-boot
```

Use `pwd` before running file-management commands to avoid creating or deleting files in the wrong directory.

---

# 3. Repository Root and Project Directory

There are two important locations.

## Repository root

```text
/workspaces/spring-boot-learning
```

Use the repository root for:

- Creating topic folders
- Running Git commands
- Adding notes
- Managing `.devcontainer`
- Committing and pushing changes

## Spring Boot project directory

```text
/workspaces/spring-boot-learning/01-first-spring-boot-app/hello-spring-boot
```

Use the project directory for:

- Running Maven commands
- Building the application
- Running tests
- Starting the Spring Boot application

---

# 4. Relative Path vs Absolute Path

## Relative path

A relative path starts from the current directory.

If the terminal is already at:

```text
/workspaces/spring-boot-learning
```

Use:

```bash
cd 01-first-spring-boot-app/hello-spring-boot
```

This means:

> Start from the current directory and open the given folder path.

## Absolute path

An absolute path starts from the Linux root directory `/`.

The following command can be used from any terminal location:

```bash
cd /workspaces/spring-boot-learning/01-first-spring-boot-app/hello-spring-boot
```

## Incorrect path

```bash
cd /01-first-spring-boot-app/hello-spring-boot
```

This command searches for `01-first-spring-boot-app` directly under the Linux root directory.

Because that folder does not exist there, the terminal returns:

```text
No such file or directory
```

## Important rule

```bash
cd folder/path
```

Searches from the current directory.

```bash
cd /complete/path
```

Searches from the Linux root directory.

---

# 5. Create the Learning Folder Structure

Go to the repository root:

```bash
cd /workspaces/spring-boot-learning
```

Create the required folders:

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

## Meaning of `mkdir -p`

```text
mkdir = Make Directory
-p = Create parent directories if required
```

The `-p` option also prevents an error if the directory already exists.

Expected structure:

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

# 6. Keep Empty Folders in Git

Git does not track empty folders.

Create a `.gitkeep` file inside each empty folder:

```bash
touch 02-rest-api/.gitkeep
touch 03-layered-architecture/.gitkeep
touch 04-spring-data-jpa/.gitkeep
touch 05-validation-exception/.gitkeep
touch 06-testing/.gitkeep
touch 07-spring-security/.gitkeep
touch notes/.gitkeep
```

## Meaning of `touch`

`touch` creates an empty file if the file does not already exist.

## Purpose of `.gitkeep`

`.gitkeep` is a placeholder file that allows an empty folder to be tracked by Git.

When an actual file is added to the folder, remove `.gitkeep`.

Example:

```bash
rm notes/.gitkeep
```

## Meaning of `rm`

```text
rm = Remove a file
```

A file can also be deleted using the VS Code Explorer:

```text
Right-click file → Delete
```

---

# 7. Generate the First Spring Boot Project

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

Add the dependency:

```text
Spring Web
```

Click:

```text
Generate
```

The downloaded file will be:

```text
hello-spring-boot.zip
```

## Java package-name rule

A Java package name cannot contain a hyphen.

Incorrect:

```text
com.ram.learning.hello-spring-boot
```

Correct:

```text
com.ram.learning.hello
```

---

# 8. Upload the ZIP File to Codespace

In the Codespace Explorer:

```text
01-first-spring-boot-app
→ Right-click
→ Upload
→ Select hello-spring-boot.zip
```

The ZIP should be available at:

```text
01-first-spring-boot-app/hello-spring-boot.zip
```

The file can also be dragged from the laptop and dropped into the Codespace Explorer.

---

# 9. Extract the Spring Boot Project

Go to the repository root:

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

## Meaning of `unzip`

`unzip` extracts files from a ZIP archive.

## Meaning of `-d`

`-d` specifies the destination directory where the files should be extracted.

Delete the ZIP after extraction:

```bash
rm 01-first-spring-boot-app/hello-spring-boot.zip
```

The ZIP is no longer required after extraction.

---

# 10. Verify the Extracted Project

List the project files:

```bash
ls 01-first-spring-boot-app/hello-spring-boot
```

Display hidden files and detailed information:

```bash
ls -la 01-first-spring-boot-app/hello-spring-boot
```

## Meaning of `ls`

```text
ls = List files and directories
```

## Meaning of `ls -la`

```text
-l = Display detailed information
-a = Display hidden files
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

The `pom.xml` file should be directly available at:

```text
01-first-spring-boot-app/hello-spring-boot/pom.xml
```

---

# 11. Fix an Extra Nested Folder

Sometimes extraction may create an extra folder:

```text
hello-spring-boot/
└── hello-spring-boot/
    ├── pom.xml
    ├── src/
    └── mvnw
```

Move the inner project files one level up:

```bash
rsync -a \
01-first-spring-boot-app/hello-spring-boot/hello-spring-boot/ \
01-first-spring-boot-app/hello-spring-boot/
```

## Meaning of `rsync -a`

```text
rsync = Copy and synchronize files and folders
-a = Preserve the directory structure and file properties
```

Delete the extra inner folder:

```bash
rm -rf 01-first-spring-boot-app/hello-spring-boot/hello-spring-boot
```

## Meaning of `rm -rf`

```text
rm = Remove
-r = Remove a directory and its contents recursively
-f = Force removal without confirmation
```

Use `rm -rf` carefully. Verify the path before running it.

Check the structure again:

```bash
ls -la 01-first-spring-boot-app/hello-spring-boot
```

Run this fix only when an extra nested folder exists.

---

# 12. Install VS Code Extensions

Open the Extensions panel:

```text
Ctrl + Shift + X
```

Install:

```text
Extension Pack for Java
Publisher: Microsoft
```

It provides:

- Java syntax highlighting
- IntelliSense
- Java debugger
- Maven support
- Java testing support
- Java project management

Also install:

```text
Spring Boot Extension Pack
Publisher: VMware
```

It provides:

- Spring Boot Tools
- Spring Boot Dashboard
- Spring Initializr Java Support

If the extension shows:

```text
Switch to Pre-Release Version
```

the stable version is already installed.

There is no need to switch to the pre-release version.

---

# 13. Verify Java and Maven

Move to the Spring Boot project:

```bash
cd /workspaces/spring-boot-learning/01-first-spring-boot-app/hello-spring-boot
```

Verify the current location:

```bash
pwd
```

Check Java:

```bash
java -version
```

Check Maven Wrapper:

```bash
./mvnw -version
```

## Meaning of `./mvnw`

```text
./ = Run a file from the current directory
mvnw = Maven Wrapper
```

Maven Wrapper runs the Maven version configured for the project.

If permission is denied:

```bash
chmod +x mvnw
```

## Meaning of `chmod +x`

```text
chmod = Change file permissions
+x = Add execute permission
```

---

# 14. Build and Test the Application

Run:

```bash
./mvnw clean test
```

## Command meaning

```text
clean = Delete previous build output
test = Compile the project and run automated tests
```

Expected output:

```text
BUILD SUCCESS
```

Generated files are stored inside:

```text
target/
```

The `target` folder should not be committed to Git.

---

# 15. Run the Spring Boot Application

Start the application:

```bash
./mvnw spring-boot:run
```

Expected logs:

```text
Tomcat started on port 8080
Started HelloSpringBootApplication
```

The terminal remains occupied while the application is running. This is normal.

## Stop the application

Click inside the running terminal and press:

```text
Ctrl + C
```

Do not type `Ctrl + C` in the terminal.

## Restart the application

Stop the application:

```text
Ctrl + C
```

Start it again:

```bash
./mvnw spring-boot:run
```

---

# 16. Create the First REST Controller

Create:

```text
src/main/java/com/ram/learning/hello/HelloController.java
```

Add:

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

Restart the application after changing the code:

```text
Ctrl + C
```

Then run:

```bash
./mvnw spring-boot:run
```

---

# 17. Test the Application Using curl

Keep the application running in the first terminal.

Open another terminal:

```text
Terminal → New Terminal
```

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

## Meaning of `curl`

`curl` means:

```text
Client URL
```

It is a command-line tool used to send requests to a URL and display the response.

Two terminals can have different current directories. This does not affect port access because both terminals run inside the same Codespace.

---

# 18. Configure Automatic Port Forwarding

A browser-based Codespace runs inside a cloud container.

Therefore, the local laptop browser should not directly use:

```text
http://localhost:8080
```

The Codespace forwarded URL should be used.

Go to the repository root:

```bash
cd /workspaces/spring-boot-learning
```

Create the directory:

```bash
mkdir -p .devcontainer
```

Create:

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

## Configuration meaning

```text
forwardPorts
```

Specifies the ports that Codespaces should forward.

```text
8080
```

The default port used by Spring Boot.

```text
label
```

Provides a readable name in the Ports panel.

```text
onAutoForward
```

Tells Codespaces what to do when the port becomes active.

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

Open the Ports panel and use the forwarded URL:

```text
https://codespace-name-8080.app.github.dev
```

Test:

```text
https://codespace-name-8080.app.github.dev/
```

```text
https://codespace-name-8080.app.github.dev/hello
```

The `.devcontainer` directory should be committed to Git.

---

# 19. Future Spring Boot Projects and Ports

The root `.devcontainer` configuration will work for future projects in the same repository if those projects use port `8080`.

Only one application can use port `8080` at a time.

Stop the current application before starting another application:

```text
Ctrl + C
```

To run another application simultaneously, use a different port in its `application.properties`:

```properties
server.port=8081
```

Add the new port to `devcontainer.json`:

```json
{
  "forwardPorts": [8080, 8081]
}
```

---

# 20. Format Java Code

Format the current Java file using:

```text
Shift + Alt + F
```

Alternative:

```text
Right-click inside Java file
→ Format Document
```

If VS Code asks for a formatter, select:

```text
Language Support for Java by Red Hat
```

Properly formatted code:

```java
public class HelloController {

    public String hello() {
        return "Hello Spring Boot!";
    }
}
```

Incorrect formatting:

```java
public class HelloController{
    public String hello(){
        return "Hello Spring Boot!";
    }
}
```

---

# 21. Enable Format on Save

Open Settings:

```text
Ctrl + ,
```

Search:

```text
Format On Save
```

Enable:

```text
Editor: Format On Save
```

Save a file using:

```text
Ctrl + S
```

The Java code will be 