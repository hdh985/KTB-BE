# Stage 1: Build
FROM gradle:8.2.1-jdk21 AS build
WORKDIR /home/gradle/project

# 소스 코드 전체 복사 (필요한 경우 .dockerignore로 불필요한 파일 제외)
COPY --chown=gradle:gradle . .

# Gradle Wrapper를 사용해 빌드 (테스트는 생략)
RUN gradle clean bootJar --no-daemon --stacktrace

# Stage 2: Run
FROM openjdk:21-jdk-alpine
VOLUME /tmp
EXPOSE 8080

# 빌드 스테이지에서 생성된 fat jar 파일 복사 (build/libs 폴더 내의 jar 파일)
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
