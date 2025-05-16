# 🌐DEPLOY
## 배포 사이트
[네이버 지식iN 클론](http://54.180.228.216:8080/)

## 문제 상황
GET요청은 성공적으로 응답 확인
마찬가지로 회원가입도 응답 받음
그러나 회원가입한 email과 password로 로그인하려고 할 경우 403에러를 받음

### 배포한 환경에서 백엔드 로그 확인하는 법
EC2에서 ssh로 들어갑니다.
너는 Spring Boot 애플리케이션을 Docker 컨테이너로 실행 중이야!

```
CONTAINER ID   IMAGE                   COMMAND
fb9bdd5a6507   yeonee911/deploy-test   "java -jar /app.jar"
```
→ 이게 바로 너의 Spring 애플리케이션이고, 이름은 spring-knowledgein-21st야
→ 즉, EC2 내부가 아니라 Docker 컨테이너 안에서 로그가 찍히고 있어!

다음과 같은 명령어 입력

```
sudo docker logs spring-knowledgein-21st
```

### 원인 파악
```angular2html
Caused by: io.lettuce.core.RedisConnectionException: Unable to connect to host.docker.internal:6379
```
🚨 Docker 컨테이너 내부에서 host.docker.internal로 Redis에 연결하려고 했는데, 그 주소를 못 찾겠다는 뜻이야.
host.docker.internal은 Docker for Windows/Mac에서만 동작하는 호스트 주소 전용 예약어야.
하지만 EC2는 리눅스고, Docker도 리눅스 기반에서 도는 중이니까:
🔥 host.docker.internal이라는 주소가 존재하지 않아!
➜ 그래서 Redis 연결을 못 해서 로그인 후 토큰 저장 실패
➜ 결과적으로 예외 발생 → Spring Security에서 인증 실패 → 403 Forbidden

기존 application.yml을 살펴보자
```angular2html
spring:
  data:
    redis:
      host: host.docker.internal
      port: 6379
```

이 경우 해결책은 두 가지이다. 하나는 docker 이미지에 redis를 포함하는 것이고 다른 하나는 서버에 redis를 설치하는 것이다.
나는 db도 이미지로 띄우는데 그냥 이미지로 띄우고자 했다. 따라서 docker-compose.yml을 수정해준다 

```angular2html

  redis:
    image: redis:latest
    container_name: redis
    ports:
      - "6379:6379"
    networks:
      - network

  web:
    container_name: spring-knowledgein-21st
    image: yeonee911/deploy-test
    ports:
      - "8080:8080"
    depends_on:
      - db
      - redis
    networks:
      - network
    env_file:
      - .env
    restart: always
    volumes:
      - app:/app
```

변경 사항이 생겼으므로 다시 배포를 진행해준다.. (자동배포를 얼른 배우고 싶다... 너무 귀찮음)


## 한 가지 팁
docker hub가 안 켜질때..!

1. 작업관리자에서 docker를 검색하고 관련 모든 작업을 끝낸다
2. docker hub를 관리자 권한으로 실행한다. 