FROM openjdk:21
LABEL authors="Yuhao Zhu"

EXPOSE 8080
WORKDIR /app
COPY /target/acp*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]