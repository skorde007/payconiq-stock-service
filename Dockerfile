FROM openjdk:11
ADD target/payconiq-stock-service-0.0.1-SNAPSHOT.jar payconiq-stock-service-0.0.1-SNAPSHOT.jar
EXPOSE 9999
ENTRYPOINT ["java", "-jar", "payconiq-stock-service-0.0.1-SNAPSHOT.jar"]
