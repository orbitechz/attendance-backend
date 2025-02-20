# FASE DE BUILD
FROM maven:3.9.3-eclipse-temurin-17 AS build-maven
WORKDIR /opt
COPY src .
COPY pom.xml .
RUN mvn -f ./pom.xml clean package -DskipTests

# FASE DE RUNNER
FROM openjdk:17-jdk as runner
WORKDIR /opt
COPY --from=build /opt/target/*.jar ./attendance-api.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","./attendance-api.jar"]