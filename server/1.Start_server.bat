@echo off
REM Run Spring Boot Server

REM Run the JAR file
mvn clean package && cd "target" && java -jar server-0.0.1-SNAPSHOT.jar

REM Server Started
pause
