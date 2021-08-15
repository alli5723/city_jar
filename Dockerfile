FROM openjdk:11.0-oracle

EXPOSE 8081

ADD target/*.jar city-api.jar

ADD db/*.csv /tmp/

ENTRYPOINT [ "java", "-jar", "/city-api.jar" ]