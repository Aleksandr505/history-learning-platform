# For Java 11, try this
FROM adoptopenjdk/openjdk11:alpine-jre

ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,address=*:5005,server=y,suspend=n

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]