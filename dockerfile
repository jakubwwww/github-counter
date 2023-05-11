FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw package

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/github-counter-0.0.1-SNAPSHOT.jar github-counter-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/app/github-counter-0.0.1-SNAPSHOT.jar"]