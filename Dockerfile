# --------------------------------------
# Builder stage
# --------------------------------------
FROM gradle:5.6.2-jdk11 AS builder

WORKDIR /usr/src/app
COPY . .
RUN gradle build


# --------------------------------------
# Final image
# --------------------------------------
FROM openjdk:11.0.4-jdk-slim

COPY --from=builder /usr/src/app/build/libs/pizza-hub-backend*.jar \
                    /app/pizza-hub-backend.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/pizza-hub-backend.jar"]

