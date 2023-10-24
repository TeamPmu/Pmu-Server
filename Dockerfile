FROM openjdk:17-alpine
COPY ./build/libs/pmu-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "app.jar"]
