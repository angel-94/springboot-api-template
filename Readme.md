# Spring Boot API Template

A Spring Boot REST API template with user management functionality.

## Prerequisites

- Java 17+
- Docker (for MySQL container)

## Quick Start

### 1. Start MySQL Database
```bash
docker run -d \
  --name mysql-demo \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=demo_db \
  -e MYSQL_USER=demo \
  -e MYSQL_PASSWORD=demo \
  -p 3306:3306 \
  mysql:8.0
```

### 2. Build Project
```bash
./gradlew clean build -x test
```

### 3. Run Application
```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080`

## API Documentation

Swagger UI: `http://localhost:8080/swagger-ui.html`

## Available Endpoints

### Users
- `GET /v1/users` - List users (paginated)
- `POST /v1/users` - Create user
- `GET /v1/users/{id}` - Get user by ID
- `PUT /v1/users/{id}` - Update user
- `PATCH /v1/users/{id}/status` - Update user status
- `DELETE /v1/users/{id}` - Soft delete user
- `GET /v1/users/rfc/{rfc}` - Find by RFC
- `GET /v1/users/curp/{curp}` - Find by CURP

## Sample User Creation

```json
{
  "username": "john.doe",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phoneNumber": "5551234567",
  "rfc": "DOEJ850315ABC",
  "curp": "DOEJ850315HDFNGL01"
}
```

## Database

The application uses Flyway for database migrations. Tables are created automatically on startup.

Initial users are seeded with username/password: `admin/password123`

## User Status Values
- `ACTIVE` - User can access the system
- `INACTIVE` - User cannot access the system
- `BLOCKED` - User is temporarily blocked

## Development Commands

```bash
# Clean build without tests
./gradlew clean build -x test

# Run with tests
./gradlew clean build

# Run application
./gradlew bootRun

# Run tests only
./gradlew test
```
