FROM openjdk:11-slim as build

WORKDIR /appData

WORKDIR /

COPY target/backend-server-0.0.1-SNAPSHOT.jar .

ENTRYPOINT [ "java", "-jar", "/backend-server-0.0.1-SNAPSHOT.jar" ]