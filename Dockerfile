FROM maven:3.8.6 AS MAVEN_BUILD
COPY ./ ./
RUN mvn clean package

FROM openjdk:17
COPY --from=MAVEN_BUILD /target/classmanager-0.0.1-SNAPSHOT.jar /app.jar
CMD ["java", "-jar", "/app.jar"]