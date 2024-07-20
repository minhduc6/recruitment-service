FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} recruitment-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","-Xmx1024M","-Dserver.port=${PORT}","recruitment-service-0.0.1-SNAPSHOT.jar"]
