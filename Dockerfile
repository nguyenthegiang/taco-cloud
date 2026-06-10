FROM eclipse-temurin:21-jre

COPY target/taco-cloud-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]