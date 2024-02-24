FROM eclipse-temurin:17-jdk-focal
COPY target/hypertrophyapp-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]