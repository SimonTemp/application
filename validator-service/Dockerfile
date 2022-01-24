FROM openjdk:11
ADD target/validator-service-2.6.2.jar validator-service-2.6.2.jar
ADD sample.db .
EXPOSE 8080
ENTRYPOINT ["java","-jar","validator-service-2.6.2.jar"]

