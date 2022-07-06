FROM openjdk:11-slim as build
COPY target/dashboard-java-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "/dashboard-java-0.0.1-SNAPSHOT.jar"]