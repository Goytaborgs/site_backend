FROM maven:3.8.5-openjdk-17 AS build
COPY . /app
WORKDIR /app
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Etapa de execução
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /app/target/goytaborgs-0.0.1-SNAPSHOT.jar goytaborgs-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "goytaborgs-0.0.1-SNAPSHOT.jar"]