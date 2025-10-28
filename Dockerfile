FROM openjdk:21-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "-Ddb.url=${DB_URL}", "-Ddb.username=${DB_USERNAME}", "-Ddb.password=${DB_PASSWORD}", "app.jar"]

EXPOSE 3000