State of Dev 📊
A professional survey platform tailored for the IT industry

Inspired by the StackOverflow Developer Survey, State of Dev is a robust platform designed to collect insights from the IT community. Built with Spring Boot and a monolithic architecture, it offers efficient survey management with hierarchical structures, diverse question types, and real-time answer statistics tracking.

📑 Table of Contents
Overview
Architecture
Class Diagram
Tech Stack
Key Features
Getting Started
Development
Testing
API Documentation
Overview
State of Dev provides a seamless experience for organizing and conducting developer surveys. Its key features include:

Hierarchical survey structure with subjects and sub-subjects
Support for multiple question types: Single/Multiple choice
Yearly survey editions with real-time statistics tracking
Analytics and reporting capabilities for improved insights
Architecture
The platform employs a monolithic architecture built with Spring Boot, featuring two bounded contexts:

Survey Management (Core)
Identity and Access (Supporting)
Class Diagram
The project comprises the following components:

Controllers: SurveyEditionController, SurveyController, SurveyParticipationController, SurveyResultsController
DTOs: SurveyEditionWithQuestionCountDTO, SurveyResultsDTO, CreateSurveyRequestDTO, UpdateSurveyRequestDTO
Entities: SurveyEdition, Survey, Owner, Chapter, Question, Answer
Services: ISurveyParticipationService, ISurveyResultsService
Repositories: AnswerRepository, ChapterRepository, OwnerRepository, QuestionRepository, SurveyEditionRepository, SurveyRepository
Other: GlobalExceptionHandler, Mappers (AnswerMapper, ChapterMapper, etc.)
Tech Stack
Core Technologies
Java: 21
Spring Boot: 3.2.0
PostgreSQL: 16
Redis: 7.2
Development Tools & Practices
Test-Driven Development (TDD)
Maven
JUnit 5, AssertJ
API & Documentation
OpenAPI 3.0
Swagger UI
Key Features
📊 Hierarchical survey structure
📅 Yearly editions with real-time tracking
📋 Multiple question types
📈 Answer statistics and analytics capabilities
🔐 Secure owner authentication with role-based permissions
🚀 Performance boost via Redis caching
Getting Started
Prerequisites
JDK 21
Maven 3.8+
Installation
Clone the repository:

bash
Copy code
git clone https://github.com/yourusername/state-of-dev.git  
cd state-of-dev  
Build the application:

bash
Copy code
./mvnw clean install  
Run the application:

bash
Copy code
./mvnw spring-boot:run  
Access the application at: http://localhost:8080

Development
Project Structure
plaintext
Copy code
state-of-dev/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── stateofdev/
│   │   │           ├── config/
│   │   │           │   ├── MapperConfiguration.java
│   │   │           │   ├── OpenAPIConfiguration.java
│   │   │           │   └── RedisConfiguration.java
│   │   │           ├── controller/
│   │   │           │   ├── ChapterController.java
│   │   │           │   ├── QuestionController.java
│   │   │           │   ├── SurveyController.java
│   │   │           │   └── SurveyEditionController.java
│   │   │           ├── dto/
│   │   │           │   ├── CreateSurveyRequestDTO.java
│   │   │           │   ├── SurveyEditionWithQuestionCountDTO.java
│   │   │           │   └── SurveyResultsDTO.java
│   │   │           ├── entity/
│   │   │           │   ├── Answer.java
│   │   │           │   ├── Chapter.java
│   │   │           │   ├── Owner.java
│   │   │           │   ├── Question.java
│   │   │           │   ├── Survey.java
│   │   │           │   └── SurveyEdition.java
│   │   │           ├── exception/
│   │   │           │   └── GlobalExceptionHandler.java
│   │   │           ├── repository/
│   │   │           │   ├── AnswerRepository.java
│   │   │           │   ├── ChapterRepository.java
│   │   │           │   ├── OwnerRepository.java
│   │   │           │   ├── QuestionRepository.java
│   │   │           │   ├── SurveyEditionRepository.java
│   │   │           │   └── SurveyRepository.java
│   │   │           └── service/
│   │   │               ├── ISurveyParticipationService.java
│   │   │               ├── ISurveyResultsService.java
│   │   │               ├── QuestionService.java
│   │   │               ├── SurveyEditionService.java
│   │   │               └── SurveyService.java
├── test/
└── pom.xml
Testing
Run Tests
bash
Copy code
# Unit tests  
./mvnw test  

# Integration tests  
./mvnw verify -P integration-test  
API Documentation
Swagger UI: http://localhost:8080/swagger-ui.html
OpenAPI Documentation: Accessible via /v3/api-docs
Caching Configuration
State of Dev leverages Redis for caching:

Cached Data
Survey templates
Active survey editions
Frequently accessed subjects
Answer statistics
Configuration Example (application.yml):
yaml
Copy code
spring:
  cache:
    type: redis
    redis:
      time-to-live: 3600000
      cache-null-values: false
  redis:
    host: localhost
    port: 6379
Contributing
Feel free to contribute! Create an issue or submit a pull request.

Built with ❤️ by Hariti asmaa 🚀














