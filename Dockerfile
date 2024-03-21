FROM openjdk:17-slim
RUN apt-get update && apt-get install -y maven
WORKDIR /app
COPY . .
RUN mvn install
EXPOSE 8080
CMD ["java", "-jar", "target/warehouse-0.0.1-SNAPSHOT.jar"] 