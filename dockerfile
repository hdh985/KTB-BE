# Stage 1: Build using OpenJDK 21 (Debian-based slim image) and manually installed Gradle 8.3.1
FROM openjdk:21-jdk-slim AS build
WORKDIR /app

# 필요한 패키지 설치: wget, unzip
RUN apt-get update && apt-get install -y wget unzip

# Gradle 8.3.1 다운로드 및 압축 해제
RUN wget https://services.gradle.org/distributions/gradle-8.3.1-bin.zip -O gradle.zip && \
    unzip gradle.zip && \
    rm gradle.zip

# Gradle 환경변수 설정
ENV GRADLE_HOME=/app/gradle-8.3.1
ENV PATH=$GRADLE_HOME/bin:$PATH

# 소스 코드 복사
COPY . .

# Gradle 빌드 실행 (bootJar를 사용해 fat jar 생성)
RUN gradle clean bootJar --no-daemon --stacktrace

# Stage 2: Runtime using OpenJDK 21 (Debian-based slim image)
FROM openjdk:21-jdk-slim
WORKDIR /app
VOLUME /tmp
EXPOSE 8080

# 빌드 스테이지에서 생성된 jar 파일 복사 (build/libs 폴더 내의 jar 파일)
COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
