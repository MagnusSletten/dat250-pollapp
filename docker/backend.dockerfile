FROM gradle:8-jdk21

WORKDIR /app

COPY . .

RUN ./gradlew build -x test