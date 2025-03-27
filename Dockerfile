# Stage 1: Build the application
FROM gradle:8.5-jdk21 AS builder

WORKDIR /app

# Copy Gradle files
COPY build.gradle settings.gradle /app/
COPY gradle /app/gradle

# Copy source code
COPY src /app/src

# Build the application
RUN gradle build --no-daemon

# Stage 2: Create the runtime image
FROM openjdk:21-jdk-slim

WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the application with the prod profile
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "app.jar"]