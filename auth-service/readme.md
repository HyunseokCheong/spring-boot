### 실행 방법

- naver email [SMTP](https://help.naver.com/service/30029/contents/21344?lang=ko) 설정
- src/main/resources/application.yml 파일에 이메일 정보 기입

```yml
spring:
  application:
    name: account-service
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/auth_service?serverTimezone=Asia/Seoul
    username: root
    password: 1234
  data:
    redis:
      host: localhost
      port: 6379
  jpa:
    show-sql: true
    properties:
      hibernate:
        format_sql: true
    hibernate:
      ddl-auto: create
  mail:
    host: smtp.naver.com
    username: ${naver-email} # naver email
    password: ${naver-password} # naver password
    port: 465
jwt:
  secret: b6167b6debd24fceb2c5555ebd87bac4b269b9379910f7d12fa6043ee6e49082e6d16ba0d2802f5c2f30dd0e7d18364ae819659c7f8727937f08c83c16bce3ab
  access-token-expiration: 30
  refresh-token-expiration: 3
springdoc:
  default-consumes-media-type: application/json
  default-produces-media-type: application/json
  swagger-ui:
    operations-sorter: alpha
    tags-sorter: alpha
    path: /swagger-ui.html
    disable-swagger-default-url: true
```
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
