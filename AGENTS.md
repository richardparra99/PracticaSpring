# AGENTS.md

## Repo shape
- This repo is **not** a single Maven workspace. It is one Git repo containing multiple independent Spring Boot apps.
- Do **not** run build or test commands from the repo root expecting all projects to work together.
- Always confirm which subproject is in scope first. The main app directories are:
  - `backend_carniceria`
  - `Backend-Spring/Backend`
  - `cursoSpringBoot`
  - `estudiante-sistemas`
  - `Persona_Backend`

## Working directory rule
- Run Maven commands from the target project directory, not from `C:\Users\DELL\Desktop\PracticaSpring`.
- Repo-root `git status` / `git diff` can mix changes from multiple apps because they share one Git repo.

## Verified commands
From the chosen project directory on Windows:
- Run app: `./mvnw.cmd spring-boot:run`
- Run tests: `./mvnw.cmd test`
- Build jar: `./mvnw.cmd package`

There is no verified root-level lint, formatter, typecheck, codegen, or CI workflow in this repo.

## Version split
- `backend_carniceria` and `Backend-Spring/Backend` use **Java 21**.
- `cursoSpringBoot`, `estudiante-sistemas`, and `Persona_Backend` use **Java 17**.
- `backend_carniceria` uses **Spring Boot 4.0.6**.
- The other apps use **Spring Boot 3.5.x**.

Do not assume one shared baseline for dependencies or Spring Security APIs across projects.

## Project notes

### `backend_carniceria`
- Package root: `backend_carniceria`.
- Uses PostgreSQL at runtime, but tests are isolated with H2 via `src/test/resources/application-test.properties`.
- This is the only subproject with a dedicated test profile verified for isolated auth/integration tests.
- Useful focused check: `./mvnw.cmd -Dtest=AuthIntegrationTest test`
- Auth module includes JWT, multi-role users, and bootstrap admin defaults from `application.properties`.

### `Backend-Spring/Backend`
- Package root: `Backend`.
- Uses PostgreSQL runtime DB `fast_code_db` from `application.properties`.
- Writes uploaded files to relative folder `uploads`.
- Security config hardcodes CORS origins to `http://localhost:4200` and `http://localhost:5173`.

### `Persona_Backend`
- Uses PostgreSQL runtime DB `persona-db`.
- `spring.jpa.hibernate.ddl-auto=create-drop` is enabled, so startup/shutdown can recreate schema.

### `cursoSpringBoot`
- Training/demo Spring Boot app, not wired into the other projects.

### `estudiante-sistemas`
- Small standalone CRUD-style Spring Boot app, also independent from the other projects.

## Before running an app
- Check that subproject's `src/main/resources/application.properties` first.
- Runtime DB names, credentials, and side effects differ per app.
- Prefer `backend_carniceria` when you need repeatable tests without a local PostgreSQL instance.
