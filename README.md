
---

# URL Shortener Service

This is a URL shortener service developed using Spring Boot and Java. It allows users to shorten long URLs into unique alphanumeric IDs. The service also provides options to set a time-to-live (TTL) for each shortened URL.

## Getting Started

These instructions will help you set up and run the URL shortener service on your local machine for development and testing purposes.

### Prerequisites

Make sure you have the following software installed on your machine:

- Java Development Kit (JDK) 21 or higher
- Apache Maven
- **Docker and Docker Compose** for running the PostgreSQL database

### Installing

1. Clone the repository to your local machine:

```
https://github.com/Divin17/take-home-test.git
```

2. Navigate to the project directory:

```
cd url-shortener
```

3. Update the database connection settings in the `application.properties` file:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/url_shortener
spring.datasource.username=your_username
spring.datasource.password=your_password
```

4. Build the project using Maven:

```
mvn clean install
```

### Running the Service

You can run the Spring Boot service:

```
mvn spring-boot:run
```

The service will start on the default port `8082` set in the `application.properties`. You can access the service using the base URL `http://localhost:8080`.

### Docker Compose Configuration

The `docker-compose.yml` file is included in the project and is configured to spin up a PostgreSQL container for the application. Change the credentials to match the ones set in `application.properties`:

```yaml
version: '3.8'
services:
  db:
    image: postgres:latest
    environment:
      POSTGRES_USER: your_username
      POSTGRES_PASSWORD: your_password
      POSTGRES_DB: url_shortener
    ports:
      - "5432:5432"
```

Make sure Docker and Docker Compose are installed on your machine to run the PostgreSQL database.

## API Documentation

The URL shortener service provides the following REST endpoints:

- `POST /shorten`: Shortens a long URL and returns the shortened URL.
  - Request Body: JSON object containing the original URL optional id and optional TTL 
    ```
    {
      "originalUrl": "https://www.google.com",
      "id": "",
      "ttl": 500
      }
    ```
  - Response: JSON object with the shortened URL.
- `GET /{id}`: Redirects to the original URL associated with the given ID.
- `DELETE /shorten/{id}`: Deletes the shortened URL associated with the given ID.

## Built With

- Spring Boot - Framework for building web applications with Java
- PostgreSQL - Relational database management system
- Docker & Docker Compose - Containerization tools for running PostgreSQL
- Maven - Dependency management and build automation tool

## Authors

- ISHIMWE Divin Fiston (https://github.com/Divin17)

---