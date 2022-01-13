# game-service-java Project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Database setup
```postgresql
create database game_service;
create user game_service with password 's3cr3t';
grant all privileges on database game_service to game_service;
```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

## Building and running docker image
Before building a docker image, replace line in `src/main/resources/application.properties`
```
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/game_service
```
with
```
quarkus.datasource.jdbc.url=jdbc:postgresql://host.docker.internal:5432/game_service
```

Build and run docker image
```
./mvnw package

docker build -f src/main/docker/Dockerfile.jvm -t quarkus/game-service-jvm .

docker run -i --rm -p 8080:8080 quarkus/game-service-jvm
```

## Calling the service

### Quarkus-generated Swagger-UI
After running the app, go to http://localhost:8080/q/swagger-ui/ to see controller, requests and responses specifications.

It's also possible to call API endpoints there.
