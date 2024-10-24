# CCH - Cycling Competition Management System

## Overview
CCH (Cyclo Club Horizon) is a REST API designed to manage cycling time trial competitions. The system handles cyclists, teams, competitions, stages, and results management, providing a comprehensive solution for organizing and managing cycling events.

## 🚀 Features

### Core Functionalities
- **Cyclist Management**
    - Register new cyclists with personal details
    - Update cyclist information
    - View cyclists with sorting options (name, nationality, team)
    - Remove cyclists from the system

- **Team Management**
    - Create and manage cycling teams
    - Associate cyclists with teams
    - View team details and roster

- **Competition Management**
    - Create and organize competitions with details (name, date, location, distance)
    - Manage competition stages
    - Handle participant registration
    - Track competition results

- **Results & Rankings**
    - Record individual stage times
    - Calculate rankings automatically (via triggers)
    - Generate individual and cumulative rankings
    - Manage stage-specific results

### Technical Features
- RESTful API endpoints following best practices
- Comprehensive exception handling with custom exceptions
- Global error handling using @RestControllerAdvice
- DTO pattern implementation for data transfer
- Robust data validation
- Clean architecture following DDD principles

## 🏗 Architecture

### Domain-Driven Design (DDD) Implementation
- **Domain Layer**: Contains business logic and entities
- **Application Layer**: Houses application services and DTOs
- **Infrastructure Layer**: Manages persistence and external services
- **Presentation Layer**: Handles REST controllers and request/response models

### Technical Stack
- **Framework**: Spring Framework (IoC, MVC, Data JPA)
- **ORM**: Hibernate
- **Database**: [Your Database Choice]
- **Build Tool**: Maven
- **Documentation**: Swagger/OpenAPI

## 📡 API Endpoints

### Team Management
```
GET    /api/v1/teams
GET    /api/v1/teams/{id}
POST   /api/v1/teams
PUT    /api/v1/teams/{id}
DELETE /api/v1/teams/{id}
```

### Cyclist Management
```
GET    /api/v1/cyclists
GET    /api/v1/cyclists/{id}
POST   /api/v1/cyclists
PUT    /api/v1/cyclists/{id}
DELETE /api/v1/cyclists/{id}
```

### Competition Management
```
GET    /api/v1/competitions
GET    /api/v1/competitions/{id}
POST   /api/v1/competitions
PUT    /api/v1/competitions/{id}
DELETE /api/v1/competitions/{id}
```

### Stage Management
```
GET    /api/v1/stages
GET    /api/v1/stages/{id}
POST   /api/v1/stages
PUT    /api/v1/stages/{id}
DELETE /api/v1/stages/{id}
```

### Results Management
```
GET    /api/v1/general-results
GET    /api/v1/general-results/{competitionId}/{cyclistId}
POST   /api/v1/general-results
DELETE /api/v1/general-results/{competitionId}/{cyclistId}

GET    /api/v1/stage-results
GET    /api/v1/stage-results/{stageId}/{cyclistId}
POST   /api/v1/stage-results
DELETE /api/v1/stage-results/{stageId}/{cyclistId}
```

## 🛠 Technical Implementation

### Clean Code Principles
- SOLID principles adherence
- Design patterns implementation
- Clean architecture
- Comprehensive documentation
- Unit testing coverage

### Exception Handling
- Custom exception classes for domain-specific errors
- Global exception handling using @RestControllerAdvice
- Structured error responses
- Proper HTTP status code usage

### Data Transfer Objects (DTOs)
- Separate DTOs for request and response using records
- MapStruct for object mapping
- Input validation using Bean Validation

## 🧪 Testing
- JUnit for unit testing
- Mockito for mocking dependencies
- TDD approach for core functionalities

## 🚀 Getting Started

### Prerequisites
- JDK 21 
- Maven 3.6+
- Your preferred IDE
- Postman (for API testing)

### Installation
1. Clone the repository
   ```bash
   git clone https://github.com/AymaneTech/cch-cycling-mangement-in-java
   ```

2. Navigate to project directory
   ```bash
   cd cch-cycling-management
   ```

3. Build the project
   ```bash
   mvn clean install
   ```

4. Run the application
   ```bash
   mvn spring-boot:run
   ```

### Configuration
- Configure database connection in `application.properties`
- Adjust server port and other settings as needed