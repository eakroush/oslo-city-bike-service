# oslo-city-bike-service

# Oslo City Bike Service Application

Is a Java Service that reads from "oslo-city-bike" downstream  
and transforms the data into a suitable data structure in a NoSQL database.

The Service exposes Rest API  to:
  1. Keep track of all bike stalls and their position. 
  2. Keep track of the bike stall status and history every 5 minutes.

### Prerequisites

- Java 11 and above
- Maven

### Installing

######Checkout the project from git

```
git clone path
```

######To run the application
```
mvn spring-boot:run
```

######To run tests

```
mvn clean verify
```


## API documentation
http://localhost:8181/city-bike/swagger-ui


## Built With

* [Java](https://docs.oracle.com/) - The language Java
* [Spring Framework](https://spring.io/) - Spring framework
* [MongoDB](https://www.mongodb.com/) - MongoDB

## Authors

* **Eimad Al Akroush**

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Thanks for the challenge



