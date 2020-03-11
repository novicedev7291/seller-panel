FROM openjdk:8-alpine

ADD target/seller-panel.jar /opt/

EXPOSE 8081

ENTRYPOINT java -jar /opt/seller-panel.jar
