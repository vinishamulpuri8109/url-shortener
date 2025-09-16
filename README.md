# URL Shortener

A simple URL shortener service built with Spring Boot.

## Features

- Shorten long URLs
- Redirect to original URLs
- Track click statistics
- H2 in-memory database
- RESTful API

## Technologies

- Spring Boot 3.2.0
- Spring Data JPA
- H2 Database
- Maven

## API Endpoints

- `POST /api/url/shorten` - Shorten a URL
- `GET /api/url/r/{code}` - Redirect to original URL
- `GET /api/url/stats/{code}` - Get URL statistics
- `GET /api/url/health` - Health check

## Setup

1. Clone the repository
2. Run: `mvn spring-boot:run`
3. Access: `http://localhost:8080`

## H2 Console

Access: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (empty)
