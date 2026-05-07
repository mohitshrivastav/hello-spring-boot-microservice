# hello-spring-boot-microservice

A minimal Spring Boot microservice with a `/hello` REST endpoint. Generated as sample code.

## Stack

- Java 17
- Spring Boot 3.3.5 (`spring-boot-starter-web`, `spring-boot-starter-actuator`)
- Maven

## Endpoints

| Method | Path                  | Description                                  |
| ------ | --------------------- | -------------------------------------------- |
| GET    | `/`                   | Service metadata (`service`, `status`)       |
| GET    | `/hello`              | Returns `Hello, world!` with a timestamp     |
| GET    | `/hello?name=Mohit`   | Returns `Hello, Mohit!` with a timestamp     |
| GET    | `/actuator/health`    | Spring Boot Actuator health check            |

## Run locally

Requires Java 17+ and Maven 3.9+.

```bash
mvn spring-boot:run
```

Then in another terminal:

```bash
curl http://localhost:8080/
curl http://localhost:8080/hello
curl 'http://localhost:8080/hello?name=Mohit'
curl http://localhost:8080/actuator/health
```

## Test

```bash
mvn test
```

## Build a runnable jar

```bash
mvn clean package
java -jar target/hello-spring-boot-microservice-0.0.1-SNAPSHOT.jar
```

## Project layout

```
.
├── pom.xml
├── src
│   ├── main
│   │   ├── java/com/example/hello
│   │   │   ├── HelloApplication.java
│   │   │   └── HelloController.java
│   │   └── resources/application.properties
│   └── test
│       └── java/com/example/hello/HelloControllerTest.java
└── docs/ci-workflow.yml.sample
```

## Enabling CI (GitHub Actions)

A ready-to-use workflow lives at `docs/ci-workflow.yml.sample`. To enable it,
move the file into the canonical location and commit:

```bash
mkdir -p .github/workflows
git mv docs/ci-workflow.yml.sample .github/workflows/ci.yml
git commit -m "Enable GitHub Actions CI"
git push
```

(Adding files under `.github/workflows/` requires a token/credential with the
`workflow` scope, which is why this scaffold ships the file under `docs/`.)
