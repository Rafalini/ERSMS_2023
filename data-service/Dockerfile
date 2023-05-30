FROM openjdk:20
ADD target/*.jar data-service
ENTRYPOINT ["java", "-jar", "data-service"]
EXPOSE 8083