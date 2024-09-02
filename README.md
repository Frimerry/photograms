# 포토그램

### 의존성

- Sring Boot DevTools
- Lombok
- Spring Data JPA
- MariaDB Driver
- Spring Security
- Spring Web
- oauth2-client

### 데이터베이스

```sql
create user 'username'@'%' identified by 'password';
GRANT ALL PRIVILEGES ON *.* TO 'username'@'%';
create database photogram;
```

### yml 설정

```yml
server:
  port: 8081
  servlet:
    context-path: /
    encoding:
      charset: utf-8
      enabled: true
    
spring:
  mvc:
    view:
      prefix: /WEB-INF/views/
      suffix: .jsp
      
  datasource:
    driver-class-name: org.mariadb.jdbc.Driver
    url: jdbc:mariadb://localhost:3306/photogram?serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true&useSSL=false
    username: username
    password: password
    
  jpa:
    open-in-view: true
    hibernate:
      ddl-auto: update
      naming:
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
    show-sql: true
      
  servlet:
    multipart:
      enabled: true
      max-file-size: 2MB

  security:
    user:
      name: test
      password: 1234   

#file:
#  path: C:/src/springbootwork-sts/upload/
```