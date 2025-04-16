FROM openjdk:20-jdk
WORKDIR /app
COPY . .
RUN ./gradlew build
EXPOSE 8080
CMD ["java", "-jar", "build/libs/blog-0.0.1-SNAPSHOT.jar"]
