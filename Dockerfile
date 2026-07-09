FROM maven:3-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn dependency:go-offline -q && mvn clean package -DskipTests -q

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/target/serviceconnect-backend-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]