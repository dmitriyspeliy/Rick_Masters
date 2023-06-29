FROM openjdk:11.0.11-jre-slim
ADD /target/*.jar homework-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","homework-0.0.1-SNAPSHOT.jar"]