FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /root
COPY pom.xml ./pom.xml
RUN apk add --no-cache maven && mvn install:install-file -Dfile=./pom.xml -DgroupId=com.licor -DartifactId=licor-marketplace -Dversion=1.0.0-SNAPSHOT -Dpackaging=pom
WORKDIR /app
COPY notificaciones-service/pom.xml .
COPY notificaciones-service/src ./src
RUN mvn package -DskipTests
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
