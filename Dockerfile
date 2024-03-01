FROM openjdk:18-jdk-alpine
COPY target/demo-render-0.0.1-SNAPSHOT.jar demorender.jar

ENTRYPOINT [ "java", "-jar" , "taller.jar"]