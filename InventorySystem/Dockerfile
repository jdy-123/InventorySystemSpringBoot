# Use an official Java runtime as a parent image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy the project files
COPY . .

# Install Maven (if using Maven)
RUN apt-get update && apt-get install -y maven

# Build the application
RUN mvn clean package

# Expose port 8080 (default for Spring Boot)
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/*.jar"]
