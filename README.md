[![Build Status](https://travis-ci.com/yasiekz/reactive-cqrs-es-java.svg?branch=master)](https://travis-ci.com/yasiekz/reactive-cqrs-es-java)
[![codecov](https://codecov.io/gh/yasiekz/reactive-cqrs-es-java/branch/master/graph/badge.svg)](https://codecov.io/gh/yasiekz/reactive-cqrs-es-java)

# Reactive CQRS + ES + DDD

This is tutorial project to learn how to connect SpringBoot2 with reactive CQRS + ES with some DDD techniques 

Project resolves real problem with bank accounts and payments. **Under development**

#### Key features:
- payments and account balance build from events
- event store on mongoDB, fully separated from domain
- Tests with docker images provided by test containers https://www.testcontainers.org/

#### Scheduled functionality
- Load domain objects from snapshots
- REST API calls with CQRS
- Test with spock

## Local run

#### Requirements

- Java 11 to build project - build using docker image is scheduled
- Docker
- Docker-compose which supports version '3' of docker-compose.yml file

```bash
./gradlew composeUp
```

