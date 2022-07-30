FROM openjdk:11
EXPOSE 8080
ADD target/wallet-service.jar wallet-service.jar
ENTRYPOINT ["java", "-jar","/wallet-service.jar" ]