FROM openjdk:11-slim
VOLUME /tmp
COPY build/libs/reactive-es-demo.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
