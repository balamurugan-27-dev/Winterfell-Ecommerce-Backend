
# STEP 1: Get Java 21 and Maven
FROM maven:3.9-eclipse-temurin-21 AS build

# STEP 2: Set working directory
WORKDIR /app

# STEP 3: Copy Maven settings (Google mirror)
COPY settings.xml /root/.m2/settings.xml

# STEP 4: Copy project files
COPY pom.xml .
COPY src ./src

# STEP 5: Build Spring Boot application
RUN mvn -B -ntp clean package -DskipTests

# STEP 6: Get lightweight Java 21 runtime
FROM eclipse-temurin:21-jre

WORKDIR /app

# STEP 7: Copy generated JAR
COPY --from=build /app/target/*.jar app.jar

# STEP 8: Run application
ENTRYPOINT ["java", "-jar", "app.jar"]
