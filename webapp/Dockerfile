FROM openjdk:20
ADD target/*.jar webapp

#for app to run in docker
COPY ./src/main/resources/silken-forest-388009-f6bb8da3a731.json /

ENTRYPOINT ["java", "-jar", "webapp"]
EXPOSE 8081