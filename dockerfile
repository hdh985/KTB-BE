# Stage 1: Build using OpenJDK 22 (Debian-based slim image) and manually installed Gradle 8.8
FROM openjdk:22-jdk-slim AS build
WORKDIR /app

# 필요한 패키지 설치: wget, unzip
RUN apt-get update && apt-get install -y wget unzip

# Gradle 8.8 다운로드 및 압축 해제
RUN wget https://services.gradle.org/distributions/gradle-8.8-bin.zip -O gradle.zip && \
    unzip gradle.zip && \
    rm gradle.zip

# Gradle 환경변수 설정
ENV GRADLE_HOME=/app/gradle-8.8
ENV PATH=$GRADLE_HOME/bin:$PATH

# JAVA_HOME 명시적으로 설정 (컨테이너의 JDK 22 경로, 이미지에 따라 다를 수 있음)
ENV JAVA_HOME=/opt/java/openjdk

# 소스 코드 복사
COPY . .

# Gradle 빌드 실행 (툴체인 관련 옵션 제거하여 시스템 JDK 사용)
RUN gradle clean bootJar --no-daemon --stacktrace
