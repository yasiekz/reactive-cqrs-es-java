# Reactive CQRS + ES + DDD

This is tutorial project to learn how to connect SpringBoot2 with reactive CQRS + ES with some DDD techniques 

Project resolves real problem with bank accounts and payments. **Under development**

#### Key features:
- payments and account balance build from events
- event store on mongoDB, fully separated from domain

#### Scheduled functionality
- Load domain objects from snapshots
- REST API calls with CQRS

## Local run

#### Requirements

- Java 11 to build project - build using docker image is scheduled
- Docker
- Docker-compose which supports version '3' of docker-compose.yml file

```bash
./gradlew composeUp
```

