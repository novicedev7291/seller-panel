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
export REDIS_HOST=<Redis service host>
export REDIS_PORT=<Redis service port>
export REDIS_PASSWORD =<Redis password>
export MAIL_HOST =<Email service host>
export MAIL_PORT =<Email service port>
export MAIL_USERNAME =<Email username>
export MAIL_PASSWORD =<Email password>
export JWT_SECRET=<Jwt secret key>
export JWT_EXPIRATION =<Jwt expiration in milliseconds>
export UI_REGISTER_URL =<UI register url>
export INVITATION_TOKEN_EXPIRY =<Invitation token expiry in milliseconds>
```

### Or you can copy `env.example.sh` to local file and then run
`source filename`
> Don't forget to change the values in your file.


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

For docker compose, you don't need `DB_HOST` environment variable as its added to support the local development, you can just export variable `IP` instead which would be your network `IP` or machine ip on which your db is running, since compose is supposed to be used for local development, the port and whole jdbc url is hardcoded into yml file except the ip part, so just:

```
export IP=192.168.1.1
```


## Run using docker-compose

```
docker-compose up

// To run in background

docker-compose up -d

//To stop 

docker-compose down
```

