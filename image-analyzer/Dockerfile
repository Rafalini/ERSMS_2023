FROM openjdk:20
ADD target/*.jar image-analyzer

#for app to run in docker
COPY ./src/main/resources/silken-forest-388009-9d4d8198cba5.json /

ENTRYPOINT ["java", "-jar", "image-analyzer"]
EXPOSE 8082