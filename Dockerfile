FROM openjdk:11.0-oracle

WORKDIR /opt/app

COPY . .

RUN ./mvnw install

EXPOSE 8081

COPY db/*.csv /tmp/

COPY src/main/resources/application.prod.properties /tmp/application.prod.properties

ENTRYPOINT [ "java", "-jar", "/opt/app/target/citylist.jar", "--spring.config.name=application,jdbc", "--spring.config.location=/tmp/application.prod.properties" ]