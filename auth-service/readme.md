### 실행 방법

- naver email [SMTP](https://help.naver.com/service/30029/contents/21344?lang=ko) 설정
- src/main/resources/application.yml 파일에 이메일 정보 기입

```bash
# build mysql and redis
$ docker-compose build

# run mysql and redis
$ docker-compose up -d

# build project
$ ./gradlew build

# run project
$ ./gradlew bootRun
```

### API 문서

http://localhost:8080/swagger-ui.html
