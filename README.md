# CodeAlpha Backend Project: URL Shortener

A Spring Boot REST API that turns long HTTP/HTTPS URLs into short codes, stores them in MySQL, and redirects short links to their original destinations.

## Features
- Create a short URL (`POST /api/urls`)
- Retrieve URL details (`GET /api/urls/{code}`)
- Redirect a short link (`GET /{code}`)
- MySQL persistence using Spring Data JPA
- Input validation and clear 400/404 error responses
- Short-code collision check

## Requirements
- JDK 17 or newer
- Maven 3.6+
- MySQL 8+
- Postman (recommended for API testing)

## Setup
1. Create a MySQL database, or allow the JDBC URL to create it:
   ```sql
   CREATE DATABASE codealpha_url_shortener;
   ```
2. Open `src/main/resources/application.properties`.
3. Replace `your_mysql_password` with your local MySQL password. Alternatively, set environment variables `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD`.
4. From this folder, run:
   ```bash
   mvn spring-boot:run
   ```
5. The API runs at `http://localhost:8080`.

## Test the API

### 1. Create a short URL
Request:
```http
POST http://localhost:8080/api/urls
Content-Type: application/json

{
  "url": "https://spring.io/projects/spring-boot"
}
```
Expected response (the code and timestamp will vary):
```json
{
  "originalUrl": "https://spring.io/projects/spring-boot",
  "shortCode": "a1b2c3d4",
  "shortUrl": "http://localhost:8080/a1b2c3d4",
  "createdAt": "2026-10-16T10:30:00"
}
```

### 2. Get URL details
```http
GET http://localhost:8080/api/urls/a1b2c3d4
```

### 3. Redirect
Open `http://localhost:8080/a1b2c3d4` in a browser. It should redirect to the original URL.

### 4. Invalid URL
Send `POST /api/urls` with:
```json
{"url":"javascript:alert(1)"}
```
The API should return HTTP 400.

### 5. Unknown code
Request `GET http://localhost:8080/api/urls/notreal` and expect HTTP 404.

## Main project structure
```text
src/main/java/com/codealpha/urlshortener/
  UrlShortenerApplication.java
  controller/
    UrlController.java
    ApiExceptionHandler.java
  dto/
    CreateUrlRequest.java
    ShortUrlResponse.java
  model/
    ShortUrl.java
  repository/
    ShortUrlRepository.java
  service/
    UrlShortenerService.java
    UrlNotFoundException.java
    InvalidUrlException.java
src/main/resources/application.properties
```

## Before submission
- Set your own MySQL password locally; never upload passwords to GitHub.
- Run and test every endpoint.
- Add your own name and project screenshots to the repository if required by your internship.
- Use the repository naming format requested by your submission form.
- Be prepared to explain the controller, service, repository, entity, and database flow.
