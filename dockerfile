# Stage 1: Build using OpenJDK 22 and manually installed Gradle 8.8
FROM openjdk:22-jdk-slim AS build
WORKDIR /app

# 필요한 패키지 설치: wget, unzip
RUN apt-get update && apt-get install -y wget unzip

# Gradle 8.8 다운로드 및 압축 해제 (Gradle 8.8는 JDK 22를 지원)
RUN wget https://services.gradle.org/distributions/gradle-8.8-bin.zip -O gradle.zip && \
    unzip gradle.zip && \
    rm gradle.zip

# Gradle 환경변수 설정
ENV GRADLE_HOME=/app/gradle-8.8
ENV PATH=$GRADLE_HOME/bin:$PATH

# 소스 코드 복사
COPY . .

# Gradle 빌드 실행 (도구체인 자동 감지 비활성화)
RUN gradle -Dorg.gradle.java.installations.auto-detect=false clean bootJar --no-daemon --stacktrace

# Stage 2: Runtime using OpenJDK 22 (Debian-based slim image)
FROM openjdk:22-jdk-slim
WORKDIR /app
VOLUME /tmp
EXPOSE 8080

# 빌드 스테이지에서 생성된 jar 파일 복사 (build/libs 폴더 내의 jar 파일)
COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
