FROM eclipse-temurin:17-jre
MAINTAINER trongbui
WORKDIR /app
COPY target/api-service-0.0.1-SNAPSHOT.jar /api-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/api-service-0.0.1-SNAPSHOT.jar"]