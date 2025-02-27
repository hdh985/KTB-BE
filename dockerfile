# Stage 1: Build
FROM gradle:8.2.1-jdk17 AS build
WORKDIR /home/gradle/project

# 소스 코드 전체를 복사 (필요한 경우 .dockerignore 파일로 불필요한 파일 제외)
COPY --chown=gradle:gradle . .

# Gradle Wrapper를 사용해 빌드 (테스트는 생략할 수 있음)
RUN gradle clean bootJar --no-daemon

# Stage 2: Run
FROM openjdk:17-jdk-alpine
VOLUME /tmp
EXPOSE 8080

# 빌드 스테이지에서 생성된 jar 파일을 복사합니다.
# 일반적으로 Gradle은 build/libs 폴더에 fat jar 파일을 생성합니다.
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
