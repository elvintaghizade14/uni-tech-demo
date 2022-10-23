# Uni-Tech Digital Banking System Demo #

![Travis (.org)](https://img.shields.io/travis/isopropylcyanide/Jwt-Spring-Security-JPA)
![GitHub](https://img.shields.io/github/license/isopropylcyanide/Jwt-Spring-Security-JPA?color=blue)

<pre>
Total test count:  152
Used:
    - Java 11
    - Spring Boot -> Web, AOP, Security(JWT), Data JPA, Test, ArchTest, Validation
    - Spring Cloud Gateway
    - OpenFeign (for exchange rates service from `https://api.exchangerate.host/latest`
    - Redis
    - Postgres db, h2 db (only for testing)
    - Liquibase as db migration tool
    - DockerFile, Docker Compose
    - Project Lombok
    - MapStruct
    - Spring Fox (Swagger)
    - Slf4j
    - Sonarqube
    - Checkstyle
</pre>

## API Gateway ##

- Spring Cloud Gateway

## Getting Started ##

- `./start.sh` - start app
- `./stop.sh`  - stop app

## Architecture ##

![uni-tech-architecture](./_diagrams/architecture.png)

## Swagger Docs: ##

```
http://localhost:8080/swagger-ui/
```

### Ms-Identity Rest API ###

![ms-identity](./_diagrams/identity.png)

### Ms-Account Rest API ###

![ms-account](./_diagrams/account.png)

### Ms-Exchange Rest API ###

![ms-exchange](./_diagrams/exchange-rates.png)
