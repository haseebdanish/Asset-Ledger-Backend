# 💰 AssetLedger – Net Worth Tracker (Backend)

A RESTful backend built with **Spring Boot + PostgreSQL** that allows users to manage financial assets and calculate net worth dynamically.

Designed using clean layered architecture with DTO separation and validation.

---

## 🚀 Tech Stack

- Java 25  
- Spring Boot 4  
- Spring Data JPA  
- PostgreSQL  
- Hibernate  
- Jakarta Validation  
- Maven  

---

## ✨ Features

### 👤 User
- Create user  
- Fetch user by ID  
- Input validation  
- Password not returned in API responses  

### 💼 Assets
- Add or update asset (upsert logic)  
- One row per asset per user  
- Automatic quantity & invested amount aggregation  
- List user assets  

### 📊 Net Worth
- Calculates total invested amount per user  
- Uses database-level aggregation (`SUM()` query)  

---

## 🔌 API Endpoints

### Create User
POST `/users`

### Get User
GET `/users/{userId}`

### Add or Update Asset
POST `/users/{userId}/assets`

### Get Assets
GET `/users/{userId}/assets`

### Get Net Worth
GET `/users/{userId}/net-worth`

---

## 🧠 Key Backend Concepts Implemented

- DTO pattern using Java records  
- Validation (`@NotBlank`, `@Email`, `@Positive`)  
- Upsert business logic  
- Proper JPA relationships (`@ManyToOne`)  
- Financial precision using `BigDecimal`  
- Avoiding reserved SQL keywords  

---

## 🛠️ Setup

1. Create PostgreSQL database:
   ```sql
   CREATE DATABASE assetledger;
2. Configure application.yml
3. Run the application
4. Test endpoints using Postman

---

## 🔮 Future Improvements
- Global exception handling
- Authentication (JWT)
- Real-time market price integration
- React frontend dashboard
