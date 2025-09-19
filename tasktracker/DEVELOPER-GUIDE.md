# Developer Guide — Task Tracker

This guide helps you set up a development environment, run the app locally, and switch between H2 (default) and MySQL (production-like) databases.

## Prerequisites
- JDK 21 installed (or JDK 17+). The project currently targets Java 17 but runs fine on JDK 21.
- Git and a working shell (PowerShell on Windows).
- Internet access to download Maven dependencies.

## Environment setup (Windows PowerShell)
### Install JDK 21 (winget)
```powershell
winget install --id EclipseAdoptium.Temurin.21.JDK -e
```
Or Chocolatey:
```powershell
choco install temurin21jdk -y
```
Set `JAVA_HOME` for current session (replace path if different):
```powershell
$env:JAVA_HOME = 'C:\Program Files\Eclipse Adoptium\jdk-21'
$env:Path = $env:JAVA_HOME + '\\bin;' + $env:Path
java -version
javac -version
```

## Build & run (H2 - default)
- Run tests:
```powershell
.\mvnw.cmd test
```
- Run the app:
```powershell
.\mvnw.cmd spring-boot:run
```
- Open in browser:
  - Task list: `http://localhost:8080/tasks`
  - H2 console: `http://localhost:8080/h2-console`
    - JDBC URL: `jdbc:h2:file:./data/taskdb`
    - Username: `sa` (blank password)

## Switching to MySQL (example)
1. Create a MySQL database (e.g., `taskdb`).
2. Set environment variables or use the `application-mysql.properties` profile.
3. Example run with env var for password (PowerShell):
```powershell
$env:SPRING_DATASOURCE_PASSWORD='your_mysql_password'
.\mvnw.cmd -Dspring-boot.run.profiles=mysql spring-boot:run
```

### `application-mysql.properties` (example)
The project includes `src/main/resources/application-mysql.properties` — it uses `spring.flyway.enabled=true` so Flyway will run migrations at startup.

## Flyway migrations
- Migrations are in `src/main/resources/db/migration`.
- The repo contains `V1__init.sql` which creates the `tasks` table.
- Flyway will apply migrations automatically when the app starts with a database that Flyway can access.

## Data migration from H2 to MySQL
- Small dataset: use H2 console `SCRIPT TO 'dump.sql'` then edit and import into MySQL.
- For larger or production: use a Flyway-based migration or write a migration utility (I can help generate one).

## Security & secrets
- Do not commit real passwords or secrets.
- `application-mysql.properties` uses `spring.datasource.password=secret` as an example. Replace it at runtime with an environment variable instead:
```
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD:}
```
- Recommended: use environment variables or a secret manager (GitHub Actions secrets, Azure Key Vault, etc.).

## Commit checklist
- Ensure tests pass locally: `.\mvnw.cmd test`.
- Remove local-only changes from the index (e.g., local `application-*.properties`) or add them to `.gitignore`.
- Ensure `application-mysql.properties` does not contain real credentials (replace with env variable placeholder before committing if necessary).
- Commit with a clear message:
```powershell
git add -A
git commit -m "Add H2 JPA persistence, Flyway migration, TaskService, and controller refactor"
```
- If pre-commit hooks fail, run `git commit --no-verify` with caution.

## Troubleshooting
- "No compiler is provided" — install JDK and set `JAVA_HOME`.
- Maven dependency resolution errors — ensure you have internet access; remove unknown vendor-specific BOMs if unnecessary.
- Slow or hanging git in VS Code — try committing from terminal, disable heavy extensions temporarily, or check `git status`.

## Next improvements I can help with
- Add integration tests for controllers and repository (H2-based).
- Add a one-off migration tool to move H2 data to MySQL.
- Add CI workflow to run tests and optionally run a local MySQL container for integration tests.

---
If you want, I can now:
- Replace `application-mysql.properties` password with environment variable placeholder and update files accordingly.
- Update `pom.xml` Java version to 21.
- Add a `V2__seed.sql` sample data migration for dev.

Tell me which of these you'd like me to do next.
