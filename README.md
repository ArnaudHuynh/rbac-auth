# RBAC App

A Role-Based Access Control (RBAC) application built with Spring Boot, featuring user authentication, role-based authorization, and dynamic menu generation based on user roles. The project includes Swagger UI for API documentatio.

## Features
- **User Authentication**: Register, login, and logout using JWT.
- **Role-Based Access**: Secure endpoints for `ADMIN` and `USER` roles.
- **Dynamic Menus**: Generate menu items based on user roles.
- **API Documentation**: Explore APIs via Swagger UI.
- **Request Tracing**: Unique `requestId` for logging and debugging.
- **Deployment**: Run locally or with Docker and PostgreSQL.

## Tech Stack
- **Backend**: Spring Boot 3.4.4, Spring Security, Spring Data JPA
- **Database**: H2 (dev), PostgreSQL (prod)
- **Authentication**: JWT (JSON Web Tokens)
- **API Docs**: Springdoc OpenAPI (Swagger UI)
- **Build Tool**: Gradle
- **Containerization**: Docker, Docker Compose

## Prerequisites
- **Java 21**: Ensure JDK 21 is installed.
- **Gradle**: For building the project.
- **Docker**: For production deployment with PostgreSQL.
- **Postman** (optional): For API testing.

## Setup and Running

### 1. Clone the Repository
```bash
git clone <repository-url>
cd rbac-app
```

### 2. Run Locally (Development Mode with H2)
1. **Build the Project**:
   ```bash
   ./gradlew build
   ```
2. **Run the Application**:
    - Start in `dev` profile (uses H2 database):
      ```bash
      ./gradlew bootRun --args='--spring.profiles.active=dev'
      ```
    - Access the app at `http://localhost:8080`.

3. **Explore APIs**:
    - Open Swagger UI: `http://localhost:8080/swagger-ui.html` (public in `dev` mode).
    - Access H2 Console (optional): `http://localhost:8080/h2-console`
        - JDBC URL: `jdbc:h2:mem:testdb`
        - Username: `sa`
        - Password: (leave blank)

### 3. Run with Docker (Production Mode with PostgreSQL) *NOT DONE YET*
1. **Build and Run**:
   ```bash
   docker-compose up --build
   ```
    - Starts the app and PostgreSQL database.
    - Access the app at `http://localhost:8080`.

2. **Stop Containers**:
   ```bash
   docker-compose down
   ```

3. **Explore APIs**:
    - In `prod` mode, Swagger UI requires `ADMIN` role authentication.
    - Log in as the default admin:
      ```
      POST http://localhost:8080/api/auth/login
      Content-Type: application/json
 
      {
          "username": "admin",
          "password": "admin123"
      }
      ```
    - Use the JWT token in the `Authorization` header:
      ```
      Authorization: Bearer <jwt-token>
      ```
    - Open `http://localhost:8080/swagger-ui.html` and authorize with the token.

## API Endpoints

### Register a User
```
POST http://localhost:8080/api/auth/register
Content-Type: application/json

{
    "username": "newuser",
    "password": "password123",
    "email": "newuser@example.com"
}
```
- **Response**: `200 OK`
  ```json
  {
      "requestId": "abc123",
      "data": "User registered successfully"
  }
  ```

### Log In
```
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
    "username": "newuser",
    "password": "password123"
}
```
- **Response**: `200 OK`
  ```json
  {
      "requestId": "abc123",
      "data": {
          "token": "<jwt-token>"
      }
  }
  ```

### Get Menu Items
```
GET http://localhost:8080/api/menu/sidebar
Authorization: Bearer <jwt-token>
```
- **Response**: `200 OK`
  ```json
  {
      "requestId": "abc123",
      "data": [
          {"name": "Home", "url": "/home", "role": "USER"},
          {"name": "Admin Dashboard", "url": "/admin", "role": "ADMIN"}
      ]
  }
  ```

### Access Admin Endpoint
```
GET http://localhost:8080/api/admin
Authorization: Bearer <jwt-token>
```
- **Response**: `200 OK` (requires `ADMIN` role)
  ```json
  {
      "requestId": "abc123",
      "data": "Hello, Admin!"
  }
  ```

### Log Out
```
POST http://localhost:8080/api/auth/logout
Authorization: Bearer <jwt-token>
```
- **Response**: `200 OK`
  ```json
  {
      "requestId": "abc123",
      "data": "Logged out successfully"
  }
  ```

## Database Setup
- **Development**: H2 in-memory database (`application-dev.properties`).
- **Production**: PostgreSQL (`application-prod.properties`, `docker-compose.yml`).
    - Database: `rbacdb`
    - Username: `rbacuser`
    - Password: `rbacpassword`

## Testing
1. **Run Tests**:
   ```bash
   ./gradlew test
   ```
2. **View Report**:
    - Open `build/reports/tests/test/index.html`.

## Troubleshooting
- **Swagger UI Access Denied (403)**:
    - In `dev` mode: Ensure `--spring.profiles.active=dev` is set.
    - In `prod` mode: Authenticate as `admin` (`admin`/`admin123`) and include the JWT token.
- **Swagger UI Failed to Load API (500)**:
    - Check server logs for errors.
    - Verify dependency versions (Spring Boot 3.2.5, Springdoc 2.2.0).
- **Database Connection Issues**:
    - Ensure PostgreSQL container is running and credentials match `application-prod.properties`.