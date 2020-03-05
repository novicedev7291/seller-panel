FROM java:8

COPY target/*.jar /opt/seller-panel.jar

EXPOSE 8081

ENTRYPOINT java -jar /opt/seller-panel.jar
