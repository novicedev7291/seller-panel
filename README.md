# seller-panel
A simple seller panel to help sellers to maintain their orders

## Prequisites

* Java 8
* Maven installed on your machine
* [Postgresql for db](https://www.postgresql.org/download)
* [Docker(Optional)](https://docs.docker.com/)

## Environment variables

```
export SERVER_PORT =<Server port>
export DB_HOST=jdbc:postgresql//<db-ip>:<db-port>/seller-panel
export DB_USER=<Your db username>
export DB_PASS=<Your db password>
export MAIL_HOST =<Email host>
export MAIL_PORT =<Email port>
export MAIL_USERNAME =<Email username>
export MAIL_PASSWORD =<Email password>
export JWT_SECRET=<Jwt secret key>
export JWT_EXPIRATION =<Jwt expiration in milliseconds>
```

## Run using java

```
mvn clean install
java -jar target/seller-panel.jar

// To stop 

ctrl+q
```

## Run using docker (optional)

```
// First build the docker image
//Replace the version with value you want to keep i.e latest, 0.0.1 etc.

docker build . -t seller-panel/version 

//Finaly run using

docker run -p 8081:8081 -t seller-panel/version

// To stop 

ctrl+q

```

## Run using docker-compose

```
docker-compose up

// To run in background

docker-compose up -d

//To stop 

docker-compose down
```

