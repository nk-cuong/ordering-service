# Ordering-Service Overview

## Description
The ordering-service is a microservice built with Spring Boot 3 that handles order management for a e-commerce application.

## Features
- **Order Management**: Allows users to create, view, update, and delete orders.
- **Authentication and Authorization**: Uses Spring Security and JWT for secure access to endpoints.
- **Database Integration**: Integrates with MySQL database to store user and order information.
- **RESTful API**: Provides a RESTful API for interacting with the ordering service.
- **Docker Support**: Docker and Docker Compose are used for containerization and orchestration.

## Project Structure
- **src/main/java**: Contains the Java source code for the microservice.
- **src/main/resources**: Contains configuration files and static resources.
- **src/test**: Contains unit and integration tests for the microservice.
- **pom.xml**: Maven configuration file for managing dependencies.
- **docker-compose.yml**: Docker Compose configuration file for defining services.

## Getting Started
To run the ordering-service locally, you need to:
1. Clone the repository: `git clone https://github.com/nk-cuong/ordering-service.git`
2. Navigate to the project directory: `cd ordering-service`
3. Start the application using Docker Compose: `docker-compose up`

## API Documentation
- **GET /v1/customer/orders**: Retrieve all orders.
- **POST /v1/customer/orders**: Create a new order.
- **PUT /v1/customer/orders/{orderId}**: Update an existing order.
- **DELETE /v1/customer/orders/{orderId}**: Delete an order.

For detailed API documentation, refer to the Swagger UI at `http://localhost:8080/api-docs/swagger-ui.html`.
