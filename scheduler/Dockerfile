FROM openjdk:11-slim as build
COPY target/scheduler-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "/scheduler-0.0.1-SNAPSHOT.jar"]