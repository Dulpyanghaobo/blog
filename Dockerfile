# Use OpenJDK 20 (or choose the base image that matches your JDK version)
FROM openjdk:20-slim

# Add Maintainer Info
LABEL maintainer="i564407"

# The application's jar file (assuming the jar file is built at build/libs/*.jar)
ARG JAR_FILE=build/libs/*.jar

# Add the application's jar to the container
COPY ${JAR_FILE} app.jar

# Run the jar file 
ENTRYPOINT ["java","-jar","/app.jar"]

# Expose the port your application runs on
EXPOSE 8081
