# AssetLedger – Secure Portfolio & Net Worth Tracker API

A secure RESTful backend built with **Spring Boot** and **PostgreSQL** that allows users to manage financial assets, track investments, and calculate their portfolio's net worth.

The application follows a clean layered architecture with DTO separation, JWT authentication, validation, exception handling, and interactive API documentation.

---

# 🚀 Tech Stack

- Java 25
- Spring Boot 4
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- PostgreSQL
- Jakarta Validation
- Maven
- Swagger / OpenAPI

---

# ✨ Features

## 🔐 Authentication

- User Registration
- User Login
- JWT Token Generation
- Secure Protected Endpoints
- Stateless Authentication using Spring Security

---

## 💼 Asset Management

- Create Asset
- Update Asset
- Delete Asset
- Get Asset by ID
- List All Assets of Logged-in User
- Automatic Upsert Logic
    - Existing asset quantities are merged
    - Invested amount is automatically updated
- One asset record per user

---

## 📊 Portfolio Analytics

- Calculate Total Net Worth
- Portfolio Summary
    - Total Investment
    - Total Assets
    - Investment by Asset Type
- Portfolio Allocation
    - Stocks
    - Crypto
    - Gold
    - Mutual Funds

---

## 📄 Pagination

- Paginated Asset Listing

Example:

```
GET /assets/page?page=0&size=10
```

---

## ✅ Validation

- Request Validation
- Custom Validation Messages
- Financial values stored using `BigDecimal`

---

## ⚠️ Exception Handling

- Global Exception Handler
- Custom Exceptions
    - UserNotFoundException
    - AssetNotFoundException
- Consistent Error Responses

Example:

```json
{
    "timestamp": "...",
    "status": 404,
    "error": "Not Found",
    "message": "Asset not found with id: 10"
}
```

---

## 📖 API Documentation

Interactive Swagger UI available at:

```
http://localhost:8080/swagger-ui/index.html
```

---

# 🔌 API Endpoints

## Authentication

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/auth/register` | Register User |
| POST | `/auth/login` | Login User |

---

## Assets

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/assets` | Create / Update Asset |
| GET | `/assets` | Get All Assets |
| GET | `/assets/{id}` | Get Asset by ID |
| PUT | `/assets/{id}` | Update Asset |
| DELETE | `/assets/{id}` | Delete Asset |

---

## Portfolio

| Method | Endpoint | Description |
|---------|----------|-------------|
| GET | `/assets/networth` | Total Net Worth |
| GET | `/assets/summary` | Portfolio Summary |
| GET | `/assets/allocation` | Portfolio Allocation |
| GET | `/assets/page` | Paginated Assets |

---

# 🧠 Key Backend Concepts Implemented

- Layered Architecture
- DTO Pattern using Java Records
- Spring Security
- JWT Authentication
- Role-based Request Protection
- Stateless Authentication
- RESTful API Design
- Bean Validation
- Global Exception Handling
- Custom Exceptions
- JPA Relationships
- JPQL Aggregate Queries
- Pagination
- Financial Calculations using BigDecimal
- Swagger/OpenAPI Documentation
- Constructor-based Dependency Injection
- Transaction Management (`@Transactional`)

---

# 🛠️ Setup

## 1. Clone Repository

```bash
git clone https://github.com/<your-username>/Asset-Ledger-Backend.git
cd Asset-Ledger-Backend
```

---

## 2. Create PostgreSQL Database

```sql
CREATE DATABASE assetledger;
```

---

## 3. Configure Database

Update your `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/assetledger
    username: your_username
    password: your_password
```

---

## 4. Run Application

```bash
mvn spring-boot:run
```

---

## 5. Open Swagger

```
http://localhost:8080/swagger-ui/index.html
```

---

# 📂 Project Structure

```
src
├── Config
├── Controller
├── DTO
├── Exception
├── Model
├── Repository
├── Security
└── Service
```

---

# 🔮 Future Improvements

- Search Assets
- Filter Assets by Type
- Dynamic Sorting
- Portfolio Performance Analytics
- Current Market Price Integration
- Profit/Loss Tracking
- Transaction History
- Docker Support
- Unit & Integration Testing
- CI/CD Pipeline (GitHub Actions)
- React Frontend Dashboard
- Cloud Deployment (Render/Railway + Vercel)

---

# 📸 API Preview

Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

---

# 👨‍💻 Author

**MD Haseebuddin**

Developer | Java | Spring Boot | PostgreSQL
