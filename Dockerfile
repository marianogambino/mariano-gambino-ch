FROM openjdk:8-jdk-alpine
MAINTAINER baeldung.com
COPY target/wenance-0.0.1-SNAPSHOT.jar wenancech-0.0.1.jar
ENTRYPOINT ["java","-jar","/wenancech-0.0.1.jar"]