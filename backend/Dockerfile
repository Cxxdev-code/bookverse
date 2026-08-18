FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src ./src
RUN mvn -B clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "if [ \"$BOOKVERSE_DB_PORT\" = \"-1\" ] || [ -z \"$BOOKVERSE_DB_PORT\" ]; then export BOOKVERSE_DB_PORT=5432; fi; exec java -Dspring.profiles.active=prod -jar /app/app.jar"]
