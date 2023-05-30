FROM openjdk:20
ADD target/*.jar image-analyzer
ENTRYPOINT ["java", "-jar", "image-analyzer"]
EXPOSE 8082