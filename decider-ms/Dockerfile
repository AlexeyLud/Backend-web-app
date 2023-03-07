FROM openjdk:11
COPY target/pubsub-gcp-app.jar pga.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "pga.jar"]