FROM openjdk:20
ADD target/*.jar image-analyzer

#for app to run in docker
COPY ./src/main/resources/access_key.json /

ENTRYPOINT ["java", "-jar", "image-analyzer"]
EXPOSE 8082