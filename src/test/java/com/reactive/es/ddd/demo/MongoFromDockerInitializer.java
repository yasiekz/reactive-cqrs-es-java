package com.reactive.es.ddd.demo;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy;

public class MongoFromDockerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final String MONGO_USERNAME = "mongo";
    private static final String MONGO_PASSWORD = "mongo";
    private static final String MONGO_DB_NAME = "admin";
    private static final int MONGO_PORT = 27017;

    private static GenericContainer mongo = new GenericContainer("mongo").withExposedPorts(MONGO_PORT)
        .withEnv("MONGO_INITDB_ROOT_USERNAME", MONGO_USERNAME)
        .withEnv("MONGO_INITDB_ROOT_PASSWORD", MONGO_PASSWORD)
        .withEnv("MONGO_INITDB_DATABASE", MONGO_DB_NAME);

    private static String buildMongoDBUri() {
        return "mongodb://" + MONGO_USERNAME + ":" + MONGO_PASSWORD + "@" + mongo.getContainerIpAddress() + ":"
            + mongo.getMappedPort(MONGO_PORT) + "/" + MONGO_DB_NAME;
    }

    @Override
    public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
        mongo.setWaitStrategy(new HostPortWaitStrategy());
        mongo.start();

        TestPropertyValues valuesSpringData = TestPropertyValues.of("spring.data.mongodb.uri=" + buildMongoDBUri());
        valuesSpringData.applyTo(applicationContext);
    }
}
