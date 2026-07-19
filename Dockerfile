FROM gradle:8.5-jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

RUN chmod +x gradlew

RUN ./gradlew clean build -x test --no-daemon

FROM gcr.io/distroless/java21-debian12
COPY --from=build /home/gradle/src/build/libs/consulta-reemplacamiento-ms-0.0.1-SNAPSHOT.jar ./ms-placas.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-Duser.timezone=America/Lima","-Dserver.port=${PORT:10110}","-jar","ms-placas.jar"]