FROM openjdk:11-slim as build
WORKDIR /appData
WORKDIR /
COPY target/classification-server-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "/classification-server-0.0.1-SNAPSHOT.jar"]