# Dockerfile
# Multi-stage build for a Spring Boot Gradle project

# 1) Build stage
FROM gradle:8.5-jdk21 AS build
WORKDIR /workspace

# Copy Gradle wrapper and build files first to leverage Docker layer cache
COPY gradlew .
COPY gradle ./gradle
COPY settings.gradle.kts ./
COPY packages/sdui/build.gradle.kts ./packages/sdui/
COPY packages/sdui-common/build.gradle.kts ./packages/sdui-common/

# Pre-fetch dependencies
RUN ./gradlew :sdui:dependencies --info
RUN ./gradlew :sdui-common:dependencies --info

# Now copy sources and build
COPY packages/sdui/src ./packages/sdui/src/
COPY packages/sdui-common/src ./packages/sdui-common/src/

RUN ./gradlew :sdui:build -x test # Build the project, skipping tests

# 2) Runtime stage
FROM gradle:jdk23
WORKDIR /app

# Copy the built JAR from the builder stage and give it a stable name
# Adjust the path to the JAR file based on your Gradle build output
COPY --from=build /workspace/packages/sdui/build/libs/*.jar app.jar

EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=docker
ENV SERVER_PORT=8080
ENTRYPOINT ["java","-jar","app.jar"]
