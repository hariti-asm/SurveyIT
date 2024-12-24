
# State of Dev 📊🔍

A professional survey platform tailored for the IT industry 💻🌐

---

## 🌟 Overview

Inspired by the StackOverflow Developer Survey, **State of Dev** is a robust platform designed to collect insights from the IT community. Built with **Spring Boot** and a **monolithic architecture**, it offers efficient survey management with hierarchical structures, diverse question types, and real-time answer statistics tracking.

---

## 🏗️ Architecture

The platform employs a monolithic architecture built with **Spring Boot**, featuring two bounded contexts:

- 📋 **Survey Management (Core)**
- 🔐 **Identity and Access (Supporting)**

---

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

---

## ✨ Key Features

- 📊 Hierarchical survey structure
- 📅 Yearly editions with real-time tracking
- 📋 Multiple question types (Single/Multiple choice)
- 📈 Answer statistics and analytics
- 🔐 Secure owner authentication with role-based permissions
- 🚀 Performance boost via Redis caching

---

## 🚀 Getting Started

### 📋 Prerequisites
- JDK 21
- Maven 3.8+

### 🔧 Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/username/SurveyIT/
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

4. Access the application at: [http://localhost:8080](http://localhost:8080)

---

## 🧪 Testing

Run the tests using the following commands:

```bash
# Run unit tests
./mvnw test

# Run integration tests
./mvnw verify -P integration-test
```

---

## 📚 API Documentation
- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- OpenAPI Documentation: Accessible via `/v3/api-docs`

---

## 🤝 Contributing

Contributions are welcome!

1. Create an issue.
2. Submit a pull request.

---

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

---

## Caching Configuration

**State of Dev** uses Redis for caching the following data:

- Survey templates
- Active survey editions
- Frequently accessed subjects
- Answer statistics

---
## 🧪 Piple Line of the application

![Description](/src/main/resources/static/assets/images/surveyItPipeLine.png)

## ⚠️ Hosting with Docker 🚢

You can containerize and host the **State of Dev** application using Docker.

### 🔧 Steps to Host

1. **Build the Docker Image**  
   This command builds a Docker image using the `Dockerfile` in your project:
   ```bash
   docker build -t username/survey .
   ```

2. **Login to Docker Hub**  
   To push your Docker image to Docker Hub, authenticate using:
   ```bash
   docker login --username <your-username>
   ```
   You will be prompted to enter your Docker access token for secure authentication.

3. **Push the Docker Image**  
   Upload your image to Docker Hub using the following command:
   ```bash
   docker push username/survey
   ```

4. **Pull and Run the Docker Image**  
   Any user can now pull and run your containerized application:
   ```bash
   docker pull username/survey
   docker run -p 8080:8080 username/survey
   ```

---

## 🔎 Code Quality with SonarQube

**State of Dev** integrates **SonarQube** to analyze and maintain code quality.

### Running SonarQube Analysis

Ensure SonarQube is running locally or remotely. Then, execute the analysis using Maven:
```bash
./mvnw sonar:sonar   -Dsonar.projectKey=stateofdev   -Dsonar.host.url=http://localhost:9000   -Dsonar.login=<your-sonar-token>
```

---

## 👩‍💼 Author

Built with ❤️ by **Hariti Asmaa** 🚀
