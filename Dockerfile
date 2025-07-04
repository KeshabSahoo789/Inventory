#Use Java 21 base image
FROm eclipse-temurin:21-jdk-alpine

#Create app directory
WORKDIR/app

#Copy JAR file into the container
COPY target/*.jar app.jar

#Expose port Spring Boot uses
EXPOSE 8080

#Run the Application

ENTRYPOINT ["java", "-jar", "app.jar"]