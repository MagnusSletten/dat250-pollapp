FROM gradle:8-jdk21 as builder 

WORKDIR /app

COPY . .

RUN ["./gradlew", "build"]

FROM eclipse-temurin:21-jre
COPY --from=builder /app/build/libs/demo-0.0.1-SNAPSHOT.jar app.jar
CMD ["java","-jar","app.jar"]

