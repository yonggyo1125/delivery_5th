FROM openjdk:21-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENV DB_DDL_AUTO=create

ENTRYPOINT ["java", "-jar", "-DDB_URL=${DBURL}", "-DDB_USERNAME=${DBUSERNAME}", "-DDB_PASSWORD=${DBPASSWORD}","-DDB_DDL_AUTO=${DDL_AUTO}", "app.jar"]

EXPOSE 3000