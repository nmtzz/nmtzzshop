@echo off
REM Run Spring Boot Server
cd "target"

REM Run the JAR file
java -jar server-0.0.1-SNAPSHOT.jar

REM Server Started
pause
