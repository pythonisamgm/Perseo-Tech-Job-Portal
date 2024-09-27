
# Perseo Management System

## Overview

The Perseo Management System is a robust and scalable application built with Spring Boot, designed for managing users, courses, shopping carts, and user experiences. The system provides a secure backend with role-based access control, enabling a seamless experience for both administrators and users.

## Features

- **User Management:** Create, read, update, and delete users with role-based access control.
- **Course Management:** Manage courses including creation, updating, deletion, and retrieval.
- **Shopping Cart Management:** Handle shopping cart operations including adding courses, calculating total amounts, and more.
- **Experience Management:** Manage user experiences including creation, updating, deletion, and retrieval.
- **Authentication:** Secure API endpoints using JWT (JSON Web Tokens).
- **Role-based Access Control:** Differentiated access for admins and regular users.

## Technology Stack

- **Java**
- **Spring Boot**
- **Spring Security**
- **JPA / Hibernate**
- **ModelMapper**
- **BCrypt Password Encoder**
- **JSON Web Tokens (JWT)**
- **Mockito (for unit testing)**
- **Docker**

## Project Structure

The project follows a standard Spring Boot application structure:

- **config:** Configuration classes for Spring Security, JWT, and general application settings.
- **controllers:** REST API endpoints for users, courses, shopping carts, experiences, and authentication.
- **dtos:** Data Transfer Objects (DTOs) for handling requests and responses.
- **jwt:** JWT authentication filter and utilities.
- **models:** Entity classes representing database tables.
- **repositories:** JPA repositories for database operations.
- **services:** Business logic implementation for handling various entities and authentication.
- **test:** Unit tests for controllers and services.

## Docker Setup

### Docker Compose Configuration

A `docker-compose.yml` file is provided to set up and run the Perseo application using Docker:

```yaml
version: '3.8'

services:
  perseo-app:
    build: .
    container_name: perseo-app
    ports:
      - "8081:8081"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://host.docker.internal:3306/perseo_db
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD:
      SPRING_JPA_HIBERNATE_DDL_AUTO: update
```

### Dockerfile

The Dockerfile is used to build the Docker image for the Perseo application:

```dockerfile
# Use the official OpenJDK 21 image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Build the application using Maven
RUN mvn clean install

# Copy the JAR file from the target directory to the container
COPY target/perseo-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8081

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Running the Application with Docker

1. **Build the Docker image:**
   ```bash
   docker-compose build
   ```

2. **Run the application:**
   ```bash
   docker-compose up
   ```

3. **Access the application:**
   Open your browser and navigate to `http://localhost:8081`.

## Key Components

### Configuration

- **AppConfig:** Configures beans such as ModelMapper for object mapping.
- **ApplicationConfig:** Configures authentication providers, password encoders, and user details service.
- **WebSecurityConfig:** Sets up security filters and defines access rules for different endpoints.

### Controllers

- **AuthController:** Manages user authentication and registration.
- **CourseController:** Handles course-related operations such as creating, updating, and deleting courses.
- **ExperienceController:** Manages experiences, including creating, updating, and deleting user experiences.
- **ShoppingCartController:** Handles shopping cart operations such as adding courses, calculating totals, and more.
- **UserController:** Manages user operations including creating, updating, and deleting users.

### Models

- **User:** Represents users and implements `UserDetails` for Spring Security.
- **Course:** Represents courses available in the system.
- **Experience:** Represents work experiences linked to users.
- **ShoppingCart:** Represents shopping carts associated with users.
- **ERole:** Enum defining the roles (e.g., ADMIN, USER) in the system.

### DTOs

- **UserDTO:** Data Transfer Object for users.
- **CourseDTO:** Data Transfer Object for courses.
- **ExperienceDTO:** Data Transfer Object for experiences.
- **ShoppingCartDTO:** Data Transfer Object for shopping carts.
- **LoginRequest:** DTO for login requests.
- **RegisterRequest:** DTO for registration requests.
- **AuthResponse:** DTO for authentication responses.

### Services

- **AuthService:** Handles authentication logic, including login and registration.
- **UserServiceImpl:** Implements user-related operations.
- **CourseServiceImpl:** Implements course-related operations.
- **ExperienceServiceImpl:** Implements experience-related operations.
- **ShoppingCartServiceImpl:** Implements shopping cart-related operations.

## Unit Testing

The project includes comprehensive unit tests for controllers and services using Mockito:

- **Controller Tests:** Cover all API endpoints, verifying correct HTTP status codes and response bodies.
- **Service Tests:** Test business logic in isolation, covering various scenarios including success cases and error handling.

To run the tests:
```bash
./mvnw test
```

## Security

This application uses Spring Security with JWT for authentication. Include the JWT token in the `Authorization` header for accessing protected endpoints.

## Setup and Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-repo/perseo-app.git
   ```

2. **Configure your database settings** in `application.properties`.

3. **Run the application** using Maven:
   ```bash
   ./mvnw spring-boot:run
   ```
