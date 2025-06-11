# Use OpenJDK as base image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy Maven wrapper files and source
COPY . .

# Grant permission to Maven wrapper
RUN chmod +x mvnw

# Build the app
RUN ./mvnw clean install

# Run the app
CMD ["java", "-jar", "target/kanji-pwa-1.0-SNAPSHOT.jar"]