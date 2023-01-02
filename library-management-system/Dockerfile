FROM openjdk:19-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} library-management-system.jar
ENTRYPOINT ["java","-jar","/library-management-system.jar"]
EXPOSE 8080