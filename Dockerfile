FROM adoptopenjdk/openjdk11:alpine-jre

EXPOSE 8082

ARG JAR_FILE=target/test-api-springboot-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} app.jar

ENTRYPOINT [ "java", "-jar", "/app.jar" ]