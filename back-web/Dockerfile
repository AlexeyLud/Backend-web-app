FROM openjdk:11
ADD target/springboot-gcp-application.jar sga.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "sga.jar"]