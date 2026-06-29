FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .

COPY src ./src

COPY data ./data

RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

COPY --from=builder /app/data ./data

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]