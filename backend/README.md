mbotc/src/main/resources/application.properties 작성

```
# DB
spring.datasource.url=jdbc:mysql://localhost:3306/bootdb?allowPublicKeyRetrieval=true&createDatabaseIfNotExist=true&autoReconnect=true&characterEncoding=UTF-8&serverTimezone=Asia/Seoul
spring.datasource.username= [user name]
spring.datasource.password= [user password]
spring.datasource.platform=mysql
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jackson.time-zone: Asia/Seoul

server.servlet.context-path=/api/v1

# Redis
spring.redis.host= [user server host : ex)localhost]
spring.redis.port= [user port number : ex)6379]
```

