FROM openjdk:17
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENV PORT 8080
ENTRYPOINT ["java","-jar","-Xmx1024M","-Dserver.port=${PORT}","app.jar"]
