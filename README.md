# State of Dev 📊🔍

A professional survey platform tailored for the IT industry 💻🌐

## 🌟 Overview

Inspired by the StackOverflow Developer Survey, State of Dev is a robust platform designed to collect insights from the IT community. Built with Spring Boot and a monolithic architecture, it offers efficient survey management with hierarchical structures, diverse question types, and real-time answer statistics tracking.

## 🏗️ Architecture

The platform employs a monolithic architecture built with Spring Boot, featuring two bounded contexts:
- 📋 Survey Management (Core)
- 🔐 Identity and Access (Supporting)

## 🛠️ Tech Stack

### 💻 Core Technologies
- **Language**: Java 21
- **Framework**: Spring Boot 3.2.0
- **Database**: PostgreSQL 16
- **Caching**: Redis 7.2

### 🧪 Development Tools & Practices
- Test-Driven Development (TDD)
- Maven
- JUnit 5
- AssertJ

### 📖 API & Documentation
- OpenAPI 3.0
- Swagger UI

## ✨ Key Features

- 📊 Hierarchical survey structure
- 📅 Yearly editions with real-time tracking
- 📋 Multiple question types (Single/Multiple choice)
- 📈 Answer statistics and analytics
- 🔐 Secure owner authentication with role-based permissions
- 🚀 Performance boost via Redis caching

## 🚀 Getting Started

### 📋 Prerequisites
- JDK 21
- Maven 3.8+

### 🔧 Installation

1. Clone the repository:
```bash
git clone https://github.com/hariti-asm/SurveyIT/
cd SurveyIT
```

2. Build the application:
```bash
./mvnw clean install
```

3. Run the application:
```bash
./mvnw spring-boot:run
```

4. Access the application at: http://localhost:8080

## 🧪 Testing

```bash
# Run unit tests
./mvnw test

# Run integration tests
./mvnw verify -P integration-test
```

## 📚 API Documentation
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI Documentation: Accessible via /v3/api-docs

## 🤝 Contributing

Contributions are welcome! 

1. Create an issue
2. Submit a pull request

## 📝 Project Structure
```
state-of-dev/
├── src/
│   ├── main/java/com/stateofdev/
│   │   ├── config/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── entity/
│   │   ├── exception/
│   │   ├── repository/
│   │   └── service/
│   └── test/
└── pom.xml
```

## 🌈 Caching Configuration

State of Dev uses Redis for caching:

**Cached Data**:
- Survey templates
- Active survey editions
- Frequently accessed subjects
- Answer statistics

## 👩‍💻 Author

Built with ❤️ by Hariti Asmaa 🚀

