
# STEP 1: Get Java 21 and Maven
FROM maven:3.9-eclipse-temurin-21 AS build

# STEP 2: Set working directory
WORKDIR /app

# STEP 3: Copy project files
COPY pom.xml .
COPY src ./src

# STEP 4: Build Spring Boot application
RUN mvn clean package -DskipTests

# STEP 5: Get lightweight Java 21 runtime
FROM eclipse-temurin:21-jre

WORKDIR /app

# STEP 6: Copy generated JAR
COPY --from=build /app/target/*.jar app.jar

# STEP 7: Run application
ENTRYPOINT ["java", "-jar", "app.jar"]
