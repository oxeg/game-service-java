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

## Healthcheck endpoints
There are 2 endpoints available for health check and readiness check

* http://localhost:8080/q/health/ready - checks that http service is ready + database connection is established
* http://localhost:8080/q/health/live - checks that http service is up and running

Separate endpoint `http://localhost:8080/q/health` shows accumulated health and readiness status.
It is also available with on UI page `http://localhost:8080/q/health-ui/`.

## Calling the service

### Generated Swagger-UI
After running the app, go to http://localhost:8080/q/swagger-ui/ to see controller, requests and responses specifications.

It's also possible to call API endpoints there.

### Manual calls
Create user
```shell
curl -siX POST 'http://localhost:8080/user'  \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "NewUser"
}'
```
Save created user id for future use
```shell
NEW_USER_ID=$(curl -sX POST 'http://localhost:8080/user'  \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "NewUser"
}' | jq -r '.id')
```
Save game state
```shell
curl -siX PUT "http://localhost:8080/user/$NEW_USER_ID/state"  \
--header 'Content-Type: application/json' \
--data-raw '{
    "gamesPlayed": 42,
    "score": 358
}'
```
Load game state
```shell
curl -siX GET "http://localhost:8080/user/$NEW_USER_ID/state"
```
Create friend userss for future use
```shell
FRIEND_1_ID=$(curl -sX POST 'http://localhost:8080/user'  \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Friend 1"
}' | jq -r '.id')

curl -siX PUT "http://localhost:8080/user/$FRIEND_1_ID/state"  \
--header 'Content-Type: application/json' \
--data-raw '{
    "gamesPlayed": 100,
    "score": 1000
}'

FRIEND_2_ID=$(curl -sX POST 'http://localhost:8080/user'  \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Friend 2"
}' | jq -r '.id')

curl -siX PUT "http://localhost:8080/user/$FRIEND_2_ID/state"  \
--header 'Content-Type: application/json' \
--data-raw '{
    "gamesPlayed": 200,
    "score": 2000
}'
```
Update friends
```shell
curl -siX PUT "http://localhost:8080/user/$NEW_USER_ID/friends"  \
--header 'Content-Type: application/json' \
--data-raw '{
    "friends": [
      "'$FRIEND_1_ID'",
      "'$FRIEND_2_ID'"
    ]
}'
```
Get friends
```shell
curl -siX GET "http://localhost:8080/user/$NEW_USER_ID/friends"
```
Get all users
```shell
curl -siX GET 'http://localhost:8080/user'
```
