[//]: # (#  TaskFellow - Trello Clone Backend)

[//]: # ()
[//]: # (> A production-grade RESTful API for task management, featuring JWT authentication, secure file handling, and rate-limiting protection. Built with Spring Boot 3 and MySQL.)

[//]: # ()
[//]: # (![Java]&#40;https://img.shields.io/badge/Java-17-orange&#41;)

[//]: # (![Spring Boot]&#40;https://img.shields.io/badge/Spring_Boot-3.2.3-brightgreen&#41;)

[//]: # (![MySQL]&#40;https://img.shields.io/badge/Database-MySQL-blue&#41;)

[//]: # (![Security]&#40;https://img.shields.io/badge/Security-JWT-red&#41;)

[//]: # ()
[//]: # (##  Overview)

[//]: # (This backend application simulates the core functionality of **Trello**. It allows users to create accounts, manage tasks securely, and attach files. Unlike simple CRUD apps, this project implements enterprise-level patterns including **Stateless Authentication &#40;JWT&#41;**, **Rate Limiting &#40;Token Bucket Algorithm&#41;**, and **Layered Architecture**.)

[//]: # ()
[//]: # (##  Key Features)

[//]: # (* **🔐 Secure Authentication:** Stateless User Registration & Login using **JSON Web Tokens &#40;JWT&#41;** and BCrypt password hashing.)

[//]: # (* **🗄️ Data Persistence:** Fully integrated with **MySQL** for permanent data storage &#40;replaced H2 in-memory DB&#41;.)

[//]: # (* **📂 File Handling:** Secure file upload system allowing users to attach images/documents to tasks.)

[//]: # (* **🛡️ API Security:**)

[//]: # (    * **Rate Limiting:** Implemented using **Bucket4j** to prevent DDoS/Spam &#40;10 requests/minute limit&#41;.)

[//]: # (    * **Role-Based Access:** Protected endpoints ensuring only authenticated users can access data.)

[//]: # (* **📄 Interactive Documentation:** Integrated **Swagger UI &#40;OpenAPI 3.0&#41;** for real-time API testing and visualization.)

[//]: # ()
[//]: # (##  Tech Stack)

[//]: # (* **Framework:** Spring Boot 3 &#40;Java 17&#41;)

[//]: # (* **Database:** MySQL &#40;Hibernate/JPA&#41;)

[//]: # (* **Security:** Spring Security, JWT &#40;JJWT library&#41;)

[//]: # (* **Tools:** Maven, Lombok, Swagger UI)

[//]: # (* **Algorithms:** Token Bucket &#40;for Rate Limiting&#41;)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (##  Architecture)

[//]: # (The project follows a clean **Controller-Service-Repository** pattern:)

[//]: # (1.  **Controller Layer:** Handles HTTP requests and validates DTOs.)

[//]: # (2.  **Service Layer:** Contains business logic &#40;e.g., File storage logic, Rate limit checks&#41;.)

[//]: # (3.  **Repository Layer:** Interacts with MySQL using Spring Data JPA.)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (##  Getting Started)

[//]: # ()
[//]: # (### Prerequisites)

[//]: # (* Java 17)

[//]: # (* MySQL Server)

[//]: # (* Maven)

[//]: # ()
[//]: # (### 1. Database Setup)

[//]: # (Open your MySQL Workbench or Terminal and run:)

[//]: # (```sql)

[//]: # (CREATE DATABASE trello_db;)

[//]: # (```)

[//]: # ()
[//]: # (### 2. Configuration)

[//]: # (Update `src/main/resources/application.properties` with your credentials:)

[//]: # (```properties)

[//]: # (spring.datasource.url=jdbc:mysql://localhost:3306/trello_db)

[//]: # (spring.datasource.username=root)

[//]: # (spring.datasource.password=YOUR_PASSWORD)

[//]: # (```)

[//]: # ()
[//]: # (### 3. Run the Application)

[//]: # (```bash)

[//]: # (mvn spring-boot:run)

[//]: # (```)

[//]: # (The server will start on http://localhost:8080)

[//]: # ()
[//]: # (## API Documentation &#40;Swagger&#41;)

[//]: # (Once the application is running, access the full interactive API documentation here: )

[//]: # (http://localhost:8080/swagger-ui/index.html)


# 📋 TaskFellow - Trello Clone API

A modern, production-ready task management REST API built with Spring Boot, featuring JWT authentication, rate limiting, caching, and comprehensive task management capabilities.

## ✨ Features

### Core Functionality
- 🔐 **JWT Authentication** - Secure token-based authentication system
- 📝 **Task Management** - Complete CRUD operations for tasks
- 👤 **User Management** - Registration, login, and profile management
- 📎 **File Attachments** - Upload and manage task attachments
- 🔍 **Advanced Filtering** - Filter tasks by priority with pagination support
- 📊 **Analytics Dashboard** - View system-wide statistics

### Performance & Security
- 🚀 **Caching** - Spring Cache for optimized database queries
- ⚡ **Rate Limiting** - Bucket4j-based request throttling (10 req/min per IP)
- 🔒 **Password Encryption** - BCrypt hashing for secure password storage
- 📧 **Async Email** - Non-blocking email notifications
- 📖 **API Documentation** - Interactive Swagger UI

## 🛠️ Tech Stack

- **Framework**: Spring Boot 3.2.3
- **Language**: Java 17
- **Database**: MySQL (with H2 for testing)
- **Security**: Spring Security + JWT (jjwt 0.11.5)
- **ORM**: JPA/Hibernate
- **Documentation**: SpringDoc OpenAPI 3
- **Build Tool**: Maven
- **Additional Libraries**:
  - Lombok (boilerplate reduction)
  - Bucket4j (rate limiting)
  - Spring Cache
  - Bean Validation

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- MySQL 8.0+
- Maven 3.9+

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/yourusername/trello-clone.git
cd trello-clone
```

2. **Configure MySQL Database**
```sql
CREATE DATABASE trello_db;
```

3. **Update application.properties**
```properties
# src/main/resources/application.properties
spring.datasource.username=root
spring.datasource.password=your_password
```

4. **Build the project**
```bash
./mvnw clean install
```

5. **Run the application**
```bash
./mvnw spring-boot:run
```

The server will start at `http://localhost:8080`

## 📚 API Documentation

Access the interactive Swagger UI at: `http://localhost:8080/swagger-ui.html`

### Authentication Endpoints

#### Register User
```http
POST /auth/register
Content-Type: application/json

{
  "fullName": "John Doe",
  "email": "john@example.com",
  "password": "password123"
}
```

#### Login
```http
POST /auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "password123"
}

Response:
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### Task Endpoints

All task endpoints require JWT authentication. Include the token in the Authorization header:
```
Authorization: Bearer <your_jwt_token>
```

#### Create Task
```http
POST /api/tasks
Content-Type: application/json
Authorization: Bearer <token>

{
  "title": "Complete project documentation",
  "description": "Write comprehensive README",
  "priority": "high",
  "deadline": "2026-02-28T17:00:00"
}
```

#### Get All Tasks (with pagination & filtering)
```http
GET /api/tasks?page=0&size=10&sortBy=createdAt&priority=high
Authorization: Bearer <token>
```

#### Update Task
```http
PUT /api/tasks/{id}
Content-Type: application/json
Authorization: Bearer <token>

{
  "title": "Updated title",
  "description": "Updated description",
  "priority": "medium",
  "deadline": "2026-03-01T10:00:00"
}
```

#### Delete Task
```http
DELETE /api/tasks/{id}
Authorization: Bearer <token>
```

#### Upload Attachment
```http
POST /api/tasks/{taskId}/attachment
Content-Type: multipart/form-data
Authorization: Bearer <token>

file: <binary_file>
```

### Analytics Endpoint
```http
GET /api/analytics
Authorization: Bearer <token>

Response:
{
  "total_users": 42,
  "total_tasks": 156
}
```

## 🏗️ Project Structure
```
src/main/java/com/taskfellow/trello_clone/
├── config/              # Security, JWT, Rate Limiting, Web configuration
│   ├── JwtFilter.java
│   ├── SecurityConfig.java
│   ├── RateLimitInterceptor.java
│   └── WebConfig.java
├── controller/          # REST API endpoints
│   ├── AuthController.java
│   ├── TaskController.java
│   └── AnalyticsController.java
├── dto/                 # Data Transfer Objects
│   ├── LoginRequest.java
│   ├── RegisterRequest.java
│   └── TaskRequest.java
├── entity/              # JPA Entities
│   ├── User.java
│   └── Task.java
├── exception/           # Custom exceptions & global handler
│   ├── ResourceNotFoundException.java
│   └── GlobalExceptionHandler.java
├── repository/          # Data access layer
│   ├── UserRepository.java
│   └── TaskRepository.java
├── service/             # Business logic
│   ├── AuthService.java
│   ├── TaskService.java
│   ├── EmailService.java
│   └── RateLimitingService.java
└── util/                # Utility classes
    └── JwtUtil.java
```

## 🔧 Configuration

### JWT Secret
Update the JWT secret in `application.properties`:
```properties
jwt.secret=your_secure_secret_key_minimum_32_characters_long
```

### Rate Limiting
Current configuration: 10 requests per minute per IP address. Modify in `RateLimitingService.java`:
```java
Bandwidth limit = Bandwidth.classic(10, Refill.greedy(10, Duration.ofMinutes(1)));
```

### File Upload Limits
```properties
spring.servlet.multipart.max-file-size=5MB
spring.servlet.multipart.max-request-size=5MB
```

## 🗃️ Database Schema

### Users Table
| Column    | Type         | Constraints           |
|-----------|--------------|-----------------------|
| id        | BIGINT       | PRIMARY KEY, AUTO_INCREMENT |
| email     | VARCHAR(255) | UNIQUE, NOT NULL      |
| password  | VARCHAR(255) | NOT NULL              |
| full_name | VARCHAR(255) |                       |
| role      | VARCHAR(50)  |                       |

### Tasks Table
| Column         | Type         | Constraints           |
|----------------|--------------|-----------------------|
| id             | BIGINT       | PRIMARY KEY, AUTO_INCREMENT |
| title          | VARCHAR(255) | NOT NULL              |
| description    | TEXT         |                       |
| status         | VARCHAR(50)  |                       |
| priority       | VARCHAR(50)  |                       |
| deadline       | DATETIME     |                       |
| attachment_url | VARCHAR(500) |                       |
| user_id        | BIGINT       | FOREIGN KEY, NOT NULL |
| created_at     | DATETIME     |                       |

## 🔒 Security Features

1. **JWT Token Validation** - All API endpoints (except `/auth/**`) require valid JWT tokens
2. **Password Hashing** - BCrypt encryption with salt
3. **CORS Protection** - Configurable CORS policies
4. **Rate Limiting** - IP-based request throttling
5. **Input Validation** - Bean validation on all DTOs
6. **SQL Injection Protection** - JPA parameterized queries

## 📦 Caching Strategy

The application uses Spring's caching abstraction with the following cache:
- **user_tasks**: Caches paginated task lists per user
- Cache eviction on task creation, update, and deletion

## 🚦 Rate Limiting

Implemented using Bucket4j token bucket algorithm:
- **Limit**: 10 requests per minute per IP address
- **Response**: HTTP 429 (Too Many Requests) when exceeded
- **Header**: `X-Rate-Limit-Remaining` shows available tokens

## 📧 Email Notifications

Async email service triggers notifications on:
- New task creation
- Task assignment

**Note**: Currently configured for console output. Integrate SMTP for production use.

## 🧪 Testing

Run tests with:
```bash
./mvnw test
```

## 🚀 Deployment

### Production Checklist
- [ ] Change `jwt.secret` to a strong random key
- [ ] Update database credentials
- [ ] Configure SMTP for email service
- [ ] Set `spring.jpa.hibernate.ddl-auto=validate` in production
- [ ] Enable HTTPS
- [ ] Configure CORS for your frontend domain
- [ ] Set up monitoring and logging
- [ ] Configure file storage (S3, etc.) for attachments

### Docker Deployment (Optional)
```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/trello-clone-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request



## Authors

- [Sanaa Ara](https://github.com/sanaa-duhh) 
- [Chhavi](https://github.com/chhavi07-arch) 

##  Acknowledgments

- Spring Boot documentation
- JWT.io for token standards
- Bucket4j for rate limiting
- SpringDoc for API documentation

---

**Built with ❤️ using Spring Boot**