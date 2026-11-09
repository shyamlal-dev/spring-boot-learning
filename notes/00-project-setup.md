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

The Java code will be formatted automatically when saved.

---

# 22. Fix Java File Looking Like Plain Text

Check the language mode in the bottom-right corner of VS Code.

It should display:

```text
Java
```

If it displays `Plain Text`:

```text
Click Plain Text
→ Select Java
```

If colors, IntelliSense, or formatting still do not work, follow these steps.

## Reload VS Code

```text
Ctrl + Shift + P
→ Developer: Reload Window
```

## Clean Java Language Server

```text
Ctrl + Shift + P
→ Java: Clean Java Language Server Workspace
→ Restart and Delete
```

Wait for Java and Maven indexing to complete.

## Switch to Standard Mode

If the bottom status bar displays:

```text
Java: Lightweight Mode
```

Click it and select:

```text
Switch to Standard Mode
```

Finally, format the file:

```text
Shift + Alt + F
```

---

# 23. Important VS Code Shortcuts

## Open Command Palette

```text
Ctrl + Shift + P
```

Used for:

- Reloading VS Code
- Cleaning the Java Language Server
- Rebuilding the Codespace container
- Running VS Code commands

## Open Extensions

```text
Ctrl + Shift + X
```

Used to install Java and Spring Boot extensions.

## Open Settings

```text
Ctrl + ,
```

Used to configure Format on Save and editor settings.

## Format Document

```text
Shift + Alt + F
```

Used to correct code indentation and spacing.

## Save File

```text
Ctrl + S
```

Used to save the current file.

## Stop Running Application

```text
Ctrl + C
```

Used inside the selected terminal to stop the running application.

## Open Explorer

```text
Ctrl + Shift + E
```

Used to open the project files and folders.

## Open or Hide Terminal

```text
Ctrl + `
```

The backtick key is normally located below the `Esc` key.

---

# 24. Git Workflow

Return to the repository root:

```bash
cd /workspaces/spring-boot-learning
```

Verify the path:

```bash
pwd
```

Expected:

```text
/workspaces/spring-boot-learning
```

Check changes:

```bash
git status
```

## Meaning of `git status`

It displays:

- Modified files
- New files
- Deleted files
- Staged files
- Current branch

Stage all changes:

```bash
git add .
```

## Meaning of `git add .`

It adds all changes under the current directory to the Git staging area.

Commit:

```bash
git commit -m "feat: add first Spring Boot application"
```

## Meaning of `git commit -m`

```text
commit = Save staged changes in Git history
-m = Add the commit message directly
```

Push:

```bash
git push
```

## Meaning of `git push`

It uploads local Git commits to the remote GitHub repository.

Stopping the Spring Boot application before committing is not compulsory.

---

# 25. Useful Commit Messages

```bash
git commit -m "chore: add Spring Boot learning structure"
```

```bash
git commit -m "feat: add first Spring Boot application"
```

```bash
git commit -m "feat: add hello REST endpoints"
```

```bash
git commit -m "chore: add Codespaces port configuration"
```

```bash
git commit -m "docs: add project setup notes"
```

```bash
git commit -m "chore: remove unnecessary gitkeep file"
```

Commit prefixes:

```text
feat = New feature
fix = Bug fix
docs = Documentation change
test = Testing-related change
refactor = Code restructuring
chore = Configuration or maintenance change
```

---

# 26. Common Problems and Solutions

## No such file or directory

Incorrect:

```bash
cd /01-first-spring-boot-app/hello-spring-boot
```

Correct relative path from repository root:

```bash
cd 01-first-spring-boot-app/hello-spring-boot
```

Correct absolute path:

```bash
cd /workspaces/spring-boot-learning/01-first-spring-boot-app/hello-spring-boot
```

## Maven Wrapper permission denied

Run:

```bash
chmod +x mvnw
```

Then:

```bash
./mvnw spring-boot:run
```

## Browser shows 404

If the application has started but the browser shows `404`, the requested endpoint is not defined.

Add an endpoint:

```java
@GetMapping("/")
public String home() {
    return "Application is running";
}
```

## Port 8080 is already in use

Stop the currently running application:

```text
Ctrl + C
```

Start the required application:

```bash
./mvnw spring-boot:run
```

## Port is missing from the Ports panel

Verify that the application is running:

```bash
./mvnw spring-boot:run
```

Check:

```text
.devcontainer/devcontainer.json
```

Rebuild the Codespace:

```text
Ctrl + Shift + P
→ Codespaces: Rebuild Container
```

## Java indentation is incorrect

Use:

```text
Shift + Alt + F
```

Enable Format on Save:

```text
Ctrl + ,
→ Search: Format On Save
→ Enable
```

## Java code has limited colors or no IntelliSense

Run:

```text
Ctrl + Shift + P
→ Developer: Reload Window
```

Then:

```text
Ctrl + Shift + P
→ Java: Clean Java Language Server Workspace
→ Restart and Delete
```

Wait for Java and Maven indexing to finish.

---

# 27. Command Quick Reference

## Show the current directory

```bash
pwd
```

## Return to the repository root

```bash
cd /workspaces/spring-boot-learning
```

## Open the first project using a relative path

Run this from the repository root:

```bash
cd 01-first-spring-boot-app/hello-spring-boot
```

## Open the first project using an absolute path

Run this from any location:

```bash
cd /workspaces/spring-boot-learning/01-first-spring-boot-app/hello-spring-boot
```

## List files

```bash
ls
```

## List hidden files with details

```bash
ls -la
```

## Run tests

```bash
./mvnw clean test
```

## Start the application

```bash
./mvnw spring-boot:run
```

## Test the root endpoint

```bash
curl http://localhost:8080
```

## Test the hello endpoint

```bash
curl http://localhost:8080/hello
```

## Remove a file

```bash
rm notes/.gitkeep
```

## Check Git changes

```bash
git status
```

## Stage all changes

```bash
git add .
```

## Commit changes

```bash
git commit -m "your commit message"
```

## Push changes

```bash
git push
```

---

# 28. Final Repository Structure

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

# 29. Final Checklist

```text
GitHub repository created
Java .gitignore selected
Codespace created
Learning folders created
Spring Initializr project generated
Project ZIP uploaded and extracted
Java and Maven verified
Spring Web dependency added
Application tests passed
Spring Boot application started
REST endpoints tested using curl
Port 8080 forwarded
Application opened in the browser
Java extensions installed
Java formatting configured# Spring Boot Project Setup Using GitHub Codespaces

This guide documents the current setup for the **Student Management System** project, including project import, daily Maven workflow, Codespaces port forwarding, Postman access, and common fixes.

---

## 1. Current Repository Structure

```text
spring-boot-learning/
├── .devcontainer/
│   └── devcontainer.json
├── notes/
│   ├── 00-project-setup.md
│   └── 01-spring-boot-basics.md
├── projects/
│   └── student-management-system/
│       ├── .mvn/
│       ├── src/
│       ├── mvnw
│       ├── mvnw.cmd
│       └── pom.xml
├── .gitignore
└── README.md
```

### Important locations

Repository root:

```text
/workspaces/spring-boot-learning
```

Spring Boot project directory:

```text
/workspaces/spring-boot-learning/projects/student-management-system
```

Use the repository root for Git commands and notes. Use the project directory for Maven and Spring Boot commands.

---

## 2. Import a Spring Initializr Project

Upload the downloaded ZIP inside:

```text
projects/student-management-system/
```

Extract it:

```bash
unzip "student-management-system.zip"
```

The final project folder must directly contain:

```text
pom.xml
mvnw
mvnw.cmd
.mvn/
src/
```

Avoid this duplicate structure:

```text
student-management-system/student-management-system/
```

Delete the ZIP after successful extraction:

```bash
rm "student-management-system.zip"
```

Always verify the current location and files:

```bash
pwd
ls -la
```

---

## 3. Maven Wrapper

Give the Maven Wrapper execute permission when required:

```bash
chmod +x mvnw
```

Meaning:

- `chmod` changes file permissions.
- `+x` adds execute permission.
- `mvnw` is the Maven Wrapper included with the project.

This is normally required only once.

Verify Maven:

```bash
./mvnw -version
```

---

## 4. Daily Development Workflow

Open the project directory:

```bash
cd /workspaces/spring-boot-learning/projects/student-management-system
```

Confirm the location:

```bash
pwd
```

### Normal development

Run the application:

```bash
./mvnw spring-boot:run
```

Expected logs:

```text
Tomcat started on port 8080
Started StudentManagementSystemApplication
```

Stop the application with:

```text
Ctrl + C
```

### Run automated tests

```bash
./mvnw test
```

### Fresh test after a stale-build or build issue

```bash
./mvnw clean test
```

### Full verification before commit

```bash
./mvnw clean verify
```

### Daily command rule

```text
Normal coding and manual API testing  -> ./mvnw spring-boot:run
Tests added or changed                -> ./mvnw test
Stale build or unusual build issue    -> ./mvnw clean test
Before an important commit            -> ./mvnw clean verify
```

Do not run `clean` after every small change because it deletes the `target/` directory and forces a full rebuild.

---

## 5. Test the Application Inside Codespaces

Keep the application running in one terminal. Open another terminal and run:

```bash
curl http://localhost:8080
```

Example student endpoints:

```bash
curl http://localhost:8080/students
curl http://localhost:8080/students/1
```

If `curl` returns the expected response, Spring Boot and port `8080` are working inside the Codespace, even if the Ports panel is empty.

---

## 6. Configure Port 8080

Create or update:

```text
.devcontainer/devcontainer.json
```

Use:

```json
{
  "forwardPorts": [8080],
  "portsAttributes": {
    "8080": {
      "label": "Student Management System",
      "onAutoForward": "openBrowser"
    }
  }
}
```

Configuration meaning:

- `forwardPorts` asks Codespaces to forward port `8080`.
- `label` is only the display name in the Ports panel.
- `onAutoForward` controls what Codespaces does when the port becomes active.

The label does not need to match:

```properties
spring.application.name=student-management-system
```

The Spring application name and the Codespaces port label are independent.

After changing `devcontainer.json`:

```text
Ctrl + Shift + P
-> Codespaces: Rebuild Container
```

A container rebuild does not automatically start Spring Boot. Start it again:

```bash
cd /workspaces/spring-boot-learning/projects/student-management-system
./mvnw spring-boot:run
```

---

## 7. Get the Codespaces Forwarded URL

If the Ports panel remains empty but `curl` works, print the URL manually:

```bash
echo "https://${CODESPACE_NAME}-8080.app.github.dev"
```

Example format:

```text
https://<codespace-name>-8080.app.github.dev
```

Open the generated URL in the browser.

### URL stability

- The URL normally remains the same while using the same Codespace and port.
- Stopping, starting, suspending, or resuming the same Codespace normally keeps the URL.
- A newly created Codespace receives a different name, so its forwarded URL also changes.

---

## 8. Make the Port Accessible to Postman

Codespaces ports are private by default. A browser may work because it has an authenticated GitHub session, while Postman may receive a GitHub authentication HTML page instead of the API response.

Make port `8080` public:

```bash
gh codespace ports visibility 8080:public -c "$CODESPACE_NAME"
```

If GitHub CLI authentication is required:

```bash
gh auth login
```

Then run the visibility command again.

After testing, make the port private:

```bash
gh codespace ports visibility 8080:private -c "$CODESPACE_NAME"
```

### Security note

A public forwarded port can be accessed by anyone who has its URL. Do not expose secrets, production credentials, or sensitive data.

---

## 9. Postman Environment Setup

Create a Postman environment named:

```text
Student Management System - Codespace
```

Add this variable:

```text
Variable: baseUrl
Value: https://<codespace-name>-8080.app.github.dev
```

Do not add a trailing `/` to `baseUrl`.

Select this environment from the Postman environment dropdown.

Use these request URLs:

```text
POST   {{baseUrl}}/students
GET    {{baseUrl}}/students
GET    {{baseUrl}}/students/1
PUT    {{baseUrl}}/students/1
DELETE {{baseUrl}}/students/1
```

For POST and PUT requests:

```text
Body -> raw -> JSON
Content-Type: application/json
```

Example body:

```json
{
  "name": "Ram Kumar",
  "email": "ramkumar@example.com",
  "course": "Advanced Spring Boot"
}
```

If a new Codespace is created, update only the `baseUrl` environment variable. Saved requests will continue to work.

---

## 10. Package Structure Rule

Keep the main application class in the root package:

```text
com.sms.studentmanagement
```

Keep application layers under the same package:

```text
com.sms.studentmanagement.controller
com.sms.studentmanagement.dto
com.sms.studentmanagement.entity
com.sms.studentmanagement.exception
com.sms.studentmanagement.repository
com.sms.studentmanagement.service
```

Recommended structure:

```text
com/sms/studentmanagement/
├── StudentManagementSystemApplication.java
├── controller/
├── dto/
├── entity/
├── exception/
├── repository/
└── service/
```

Spring Boot component scanning starts from the package containing the main application class and scans its sub-packages.

---

## 11. Git Workflow

Before Git operations, return to the repository root:

```bash
cd /workspaces/spring-boot-learning
```

Check changes:

```bash
git status
```

Before committing, verify the application:

```bash
cd projects/student-management-system
./mvnw clean verify
cd /workspaces/spring-boot-learning
```

Stage, commit, and push:

```bash
git add .
git commit -m "docs: update Codespaces project setup"
git push origin main
```

If local and remote branches have different commits:

```bash
git pull --rebase origin main
git push origin main
```

Do not use `git push --force` unless the history rewrite is intentional and fully understood.

Before deleting a Codespace, always commit and push important changes. Uncommitted changes can be permanently lost when the Codespace is deleted.

---

## 12. Common Problems and Fixes

### `mvnw: No such file or directory`

The terminal is in the wrong directory.

```bash
cd /workspaces/spring-boot-learning/projects/student-management-system
```

### Maven Wrapper permission denied

```bash
chmod +x mvnw
```

### Application starts but Ports panel is empty

First verify the backend:

```bash
curl http://localhost:8080
```

Then print the forwarded URL:

```bash
echo "https://${CODESPACE_NAME}-8080.app.github.dev"
```

Also verify `devcontainer.json`, save it, and rebuild the container.

### Rebuild completed but application is not running

A rebuild stops running processes. Start Spring Boot again:

```bash
./mvnw spring-boot:run
```

### Browser works but Postman returns GitHub HTML

The forwarded port is private. Make it public temporarily:

```bash
gh codespace ports visibility 8080:public -c "$CODESPACE_NAME"
```

### Postman returns `404 Not Found`

Verify the controller path. For student APIs, use the complete endpoint:

```text
{{baseUrl}}/students
```

### Postman returns `405 Method Not Allowed`

Verify the HTTP method, such as `GET`, `POST`, `PUT`, or `DELETE`.

### Postman returns `400 Bad Request`

Verify:

- JSON syntax
- DTO field names
- Required fields
- Validation rules
- `Content-Type: application/json`

### Port 8080 is already in use

Stop the old application using `Ctrl + C`, or find the process:

```bash
lsof -i :8080
```

### Accidental package move appears as deleted and untracked files

Inspect first:

```bash
git status
```

If the move was accidental and no intentional changes must be preserved:

```bash
git restore .
```

Preview untracked deletion before removing anything:

```bash
git clean -fdn
```

Use `git clean -fd` only after carefully reviewing the preview.

---

## 13. Quick Command Reference

```bash
# Repository root
cd /workspaces/spring-boot-learning

# Project directory
cd /workspaces/spring-boot-learning/projects/student-management-system

# Current directory
pwd

# List all files
ls -la

# Maven Wrapper permission
chmod +x mvnw

# Run tests
./mvnw test

# Fresh test
./mvnw clean test

# Full verification
./mvnw clean verify

# Run application
./mvnw spring-boot:run

# Test locally inside Codespaces
curl http://localhost:8080
curl http://localhost:8080/students

# Print forwarded URL
echo "https://${CODESPACE_NAME}-8080.app.github.dev"

# Make port public for Postman
gh codespace ports visibility 8080:public -c "$CODESPACE_NAME"

# Make port private again
gh codespace ports visibility 8080:private -c "$CODESPACE_NAME"

# Git workflow
git status
git add .
git commit -m "docs: update Codespaces project setup"
git push origin main
```

---

## 14. Final Checklist

```text
Student Management System project is in the correct directory
pom.xml and mvnw are directly inside the project folder
Maven Wrapper is executable
Tests pass
Application starts on port 8080
localhost endpoint works through curl
.devcontainer forwards port 8080
Forwarded Codespaces URL opens in the browser
Port is public only when external Postman access is needed
Postman uses the baseUrl environment variable
All REST endpoints are tested
Important changes are committed and pushed
```

---

## Summary

- Run Maven commands from `projects/student-management-system`.
- Use `./mvnw spring-boot:run` for normal development.
- Use `curl http://localhost:8080` to confirm the application is running.
- Use `devcontainer.json` to configure port `8080`.
- Rebuild the container after changing dev-container configuration.
- Use the Codespaces forwarded HTTPS URL outside the container.
- Temporarily make port `8080` public for Postman, then make it private again.
- Store the forwarded URL in Postman's `baseUrl` environment variable.
- A new Codespace creates a new forwarded URL.

.devcontainer committed
Source code and notes pushed to GitHub
```
