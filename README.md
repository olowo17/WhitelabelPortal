# Spring Boot Application with JWT Authentication

This project involves building a role-based access control admin dashboard where clients can manage users and customers activities.

## Features

- JWT Authentication and Authorization
- Secure API Endpoints
- Stateless Authentication using JWT
- Password encoding with BCrypt
- JWT Token validation

## Technologies Used

- **Java 17**
- **Spring Boot**
- **Spring Security**
- **JWT (JSON Web Token)**
- **BCrypt Password Encoder**
- **My Sql** 
- **Lombok**
- **Maven**

## JWT Workflow

1. **Login**: User sends a request to `/api/auth/login` with valid credentials.
2. **Token Generation**: On successful authentication, a JWT is generated and returned in the response.
3. **Token Usage**: The client sends this token in the `Authorization` header for subsequent requests.
4. **Token Validation**: The JWT is validated in each request using JWT filters.

## Setup Instructions

1. **Clone the repository**:
  git clone https://github.com/olowo17/WhitelabelPortal.git
  cd spring-boot-jwt-security

3. **Build and Run**:
  mvn clean install
  mvn spring-boot:run



