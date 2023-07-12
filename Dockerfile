FROM eclipse-temurin:17
ARG JAR_FILE=build/libs/calocheck-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=prod", "/app.jar"]
