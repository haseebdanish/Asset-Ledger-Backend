
# 💰 AssetLedger – Backend

A RESTful backend for AssetLedger, a personal finance and net-worth tracking application.

Built using Java and Spring Boot, the backend provides secure user authentication, asset management, portfolio analytics, and user-specific data access through REST APIs.

## 🚀 Live API

- **Backend API:** https://asset-ledger-backend.onrender.com
- **Swagger Documentation:** https://asset-ledger-backend.onrender.com/swagger-ui/index.html
- **Frontend:** https://asset-ledger-front-end.vercel.app

## ✨ Features

### 🔐 Authentication & Security
- User registration and login.
- JWT-based authentication.
- Password hashing using BCrypt.
- Stateless authentication using Spring Security.
- Protected endpoints.
- User-specific data access and ownership validation.
- Prevention of unauthorized access to other users' assets.

### 📊 Asset Management
- Create, retrieve, update, and delete assets.
- Automatic asset aggregation when an existing asset is added again.
- Asset ownership validation.
- Support for multiple asset categories.

### 📈 Portfolio Analytics
- Total invested amount.
- Net-worth calculation based on invested amounts.
- Asset allocation by category.
- Largest and smallest holdings.
- Average holding value.
- Total number of holdings.

### 🔎 Search, Filtering & Pagination
- Search assets by keyword.
- Filter assets by asset type.
- Paginated asset retrieval.

### 🛡️ Validation & Error Handling
- Request validation using Jakarta Validation.
- Centralized exception handling.
- Meaningful HTTP status codes and error responses.
- Validation for asset names, quantities, and invested amounts.

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Spring Boot 3.5.4 | Backend framework |
| Spring Security | Authentication and authorization |
| Spring Data JPA | Database access |
| Hibernate | ORM |
| PostgreSQL | Relational database |
| JWT (JJWT 0.12.7) | Token-based authentication |
| Maven | Build and dependency management |
| Docker | Containerization |
| Neon | Managed PostgreSQL hosting |
| Render | Backend deployment |
| Swagger / OpenAPI | API documentation |

## 🏗️ Project Structure

```text
src
└── main
    ├── java
    │   └── com.haseeb.assetledger
    │       ├── Config
    │       ├── Controller
    │       ├── DTO
    │       ├── Entity
    │       ├── Exception
    │       ├── Repository
    │       ├── Security
    │       └── Service
    │
    └── resources
        └── application.yml
```

## 📡 API Endpoints

All endpoints except registration and login require JWT authentication.

### 🔐 Authentication

| Method | Endpoint | Description | Authentication |
|---|---|---|---|
| POST | `/auth/register` | Register a new user | Not required |
| POST | `/auth/login` | Authenticate a user and generate JWT | Not required |

### 👤 User

| Method | Endpoint | Description |
|---|---|---|
| GET | `/users/me` | Retrieve the authenticated user's profile |

### 💼 Asset Management

| Method | Endpoint | Description |
|---|---|---|
| POST | `/assets` | Add a new asset or update an existing holding |
| GET | `/assets` | Retrieve all assets belonging to the authenticated user |
| GET | `/assets/{id}` | Retrieve a specific asset |
| PUT | `/assets/{id}` | Update an existing asset |
| DELETE | `/assets/{id}` | Delete an asset |

### 🔎 Asset Search & Filtering

| Method | Endpoint | Description |
|---|---|---|
| GET | `/assets/search?keyword={keyword}` | Search assets by keyword |
| GET | `/assets/type/{assetType}` | Filter assets by type |
| GET | `/assets/page` | Retrieve paginated assets |

### 📈 Portfolio & Analytics

| Method | Endpoint | Description |
|---|---|---|
| GET | `/assets/summary` | Retrieve portfolio summary |
| GET | `/assets/allocation` | Retrieve asset allocation |
| GET | `/assets/analytics` | Retrieve portfolio analytics |
| GET | `/assets/networth` | Calculate total net worth based on invested amounts |

## 🪙 Supported Asset Types

- `STOCK`
- `CRYPTO`
- `GOLD`
- `MUTUAL_FUND`
- `BANK`
- `REAL_ESTATE`

## 🔑 Authentication Flow

AssetLedger uses JWT-based authentication.

1. A user registers through `/auth/register`.
2. The user logs in through `/auth/login`.
3. The backend validates the credentials.
4. A JWT token is generated and returned.
5. The frontend stores the token and includes it in subsequent authenticated requests.
6. Spring Security validates the token before allowing access to protected endpoints.

Authenticated requests must include:

```http
Authorization: Bearer <JWT_TOKEN>
```

The backend uses a stateless security configuration, meaning authentication is performed using the JWT supplied with each request.

## 🗄️ Database Design

The application uses PostgreSQL with Spring Data JPA and Hibernate.

Each asset is associated with a specific user.

Asset records contain:

- `id`
- `assetName`
- `assetType`
- `investedAmount`
- `quantity`
- `createdAt`
- `updatedAt`
- `user`

When a user adds an asset with the same name and type as an existing holding, the backend aggregates the quantity and invested amount instead of creating a duplicate record.

All asset operations are scoped to the authenticated user to ensure data isolation.

## ⚙️ Getting Started

### 📋 Prerequisites

- Java 21
- Maven
- PostgreSQL
- Git

### 1. Clone the repository

```bash
git clone https://github.com/haseebdanish/Asset-Ledger-Backend.git
```

### 2. Navigate to the project directory

```bash
cd Asset-Ledger-Backend
```

### 3. Configure environment variables

Configure the following environment variables before running the application:

| Variable | Description |
|---|---|
| `DB_URL` | PostgreSQL JDBC connection URL |
| `DB_USERNAME` | Database username |
| `DB_PASSWORD` | Database password |
| `JWT_SECRET` | Secret key used for JWT signing |
| `PORT` | Application port (defaults to 8080 locally) |

Do not commit database credentials or JWT secrets to version control.

### 4. Run the application

Using Maven:

```bash
./mvnw spring-boot:run
```

Alternatively, build the application:

```bash
./mvnw clean package
```

The backend will be available at:

http://localhost:8080

## 🐳 Docker

The application includes a Dockerfile for containerized deployment.

Build the Docker image:

```bash
docker build -t asset-ledger-backend .
```

Run the container:

```bash
docker run -p 8080:8080 \
  -e DB_URL=<DATABASE_URL> \
  -e DB_USERNAME=<DATABASE_USERNAME> \
  -e DB_PASSWORD=<DATABASE_PASSWORD> \
  -e JWT_SECRET=<JWT_SECRET> \
  asset-ledger-backend
```

Replace the placeholder values with your own configuration.

## ☁️ Deployment Architecture

The production application uses the following architecture:

```text
React Frontend
     |
     | HTTPS / REST API
     v
Spring Boot Backend
     |
     | JPA / Hibernate
     v
PostgreSQL Database
```

| Component | Hosting |
|---|---|
| Frontend | Vercel |
| Backend | Render |
| Database | Neon PostgreSQL |
| Containerization | Docker |

## 📖 API Documentation

Swagger UI is available at:

https://asset-ledger-backend.onrender.com/swagger-ui/index.html

Use it to explore the available endpoints and test API requests.

## ⚠️ Current Limitations

- Net worth is currently calculated using invested amounts rather than live market valuations.
- Live market price integration is not yet implemented.
- Historical portfolio performance tracking is planned for a future release.

## 🔮 Future Improvements

- Live stock, cryptocurrency, gold, and mutual fund price integration.
- Portfolio performance tracking.
- Historical net-worth tracking.
- Automated testing and improved test coverage.
- Additional financial analytics.

## 👨‍💻 Author

**Haseeb Danish**

GitHub: https://github.com/haseebdanish
