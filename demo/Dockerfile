# For Java 11, try this
FROM adoptopenjdk/openjdk11:alpine-jre

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} web.jar

ENTRYPOINT ["java","-jar","/web.jar"]