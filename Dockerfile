# Étape de build avec Maven et JDK 21
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /src
COPY . .
RUN mvn clean package -DskipTests

# Étape de production
FROM eclipse-temurin:21-jdk-jammy
COPY --from=build /src/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app.jar"]
