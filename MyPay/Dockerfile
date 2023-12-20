FROM --platform=linux/amd64 eclipse-temurin:17-jdk-alpine
#FROM openjdk:17-alpine
EXPOSE 8080
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
