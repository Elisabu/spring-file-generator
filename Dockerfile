# Use the official OpenJDK base image for Java 11
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot JAR file into the container
COPY target/* /app

# Expose the port your Spring Boot application will listen on
EXPOSE 8080

# Define the command to run your application
CMD ["java", "-jar", "retiro-0.0.1-SNAPSHOT.jar"]