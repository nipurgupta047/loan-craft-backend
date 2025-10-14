# ---------------- BUILD STAGE ----------------
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# ---------------- RUNTIME STAGE ----------------
FROM openjdk:17.0.1-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/upload-0.0.1-SNAPSHOT.jar upload.jar

# Create a directory where we'll mount the Google credentials file at runtime
RUN mkdir -p /etc/secrets

# Set the environment variable for Google credentials
# The actual file will be mounted here by Render (Secret File)
ENV GOOGLE_APPLICATION_CREDENTIALS=/etc/secrets/google-credentials.json

# Expose the port
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "upload.jar"]
