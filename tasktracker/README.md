# Task Tracker (Spring Boot)

This project is a small Task Tracker web app built with Spring Boot and Thymeleaf.

Quick status
- UI templates: `src/main/resources/templates` (task_list, task_form, task_details)
- Persistence: H2 file-backed by default (configured in `application.properties`)
- Spring Data JPA: `Task` entity and `TaskRepository` added

Run locally (requires JDK 17)

1) Ensure JDK 17 is installed and `JAVA_HOME` is configured.
   - Windows (PowerShell temporary):
     ```powershell
     $env:JAVA_HOME='C:\Program Files\Eclipse Adoptium\jdk-17'
     $env:Path = $env:JAVA_HOME + '\\bin;' + $env:Path
     ```
   - Verify:
     ```powershell
     java -version
     javac -version
     ```

2) Start the app with the Maven wrapper:
```powershell
.\mvnw.cmd spring-boot:run
```

3) Open the UI:
- Task list: `http://localhost:8080/tasks`
- H2 console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:file:./data/taskdb`, user `sa`)

Switch to MySQL (example)
1) Provide a MySQL server and set credentials.
2) Activate the `mysql` profile when running:
```powershell
.\mvnw.cmd -Dspring-boot.run.profiles=mysql spring-boot:run
```
3) The project includes `application-mysql.properties` as an example.

Migrations
- Flyway is included in `pom.xml`. Place migration scripts in `src/main/resources/db/migration`.

Notes
- The project previously contained Tanzu Spring Cloud Gateway extensions which were removed to make the project build locally. Re-add them if needed.

If you'd like, I can:
- Add Flyway baseline and an initial `V1__init.sql` for the `tasks` table.
- Run a code pass to add DTOs / service layer.
