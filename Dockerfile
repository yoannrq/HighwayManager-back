# Étape de build avec Maven et JDK 21
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /build
COPY . .
RUN ./mvnw clean package -DskipTests

# Étape de production
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /build/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-jar", \
    "app.jar"]
