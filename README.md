# User service

## How to build

To build the service from sources we need a JDK 17 and a recent version of maven (3.5.4 or newer):

```
mvn clean package
```

## How to run

You can use one of the following commands to run the service:

```
mvn spring-boot:run
```

OR

```
java -jar target/user-service-0.0.1-SNAPSHOT.jar
```

## How use the api

You can invoke the api using curl as follows or using the postman collection that you can find in src/test/resources

Create a user

```
curl -i -XPOST -H "Content-Type: application/json" -d '{"name": "John Doe", "birthdate": "1980-06-01", "countryOfResidence": "France", "phoneNumber": "0612345678", "gender": "MALE"}' http://localhost:8080/users
```

Get a user

```
curl -i http://localhost:8080/users/1
```