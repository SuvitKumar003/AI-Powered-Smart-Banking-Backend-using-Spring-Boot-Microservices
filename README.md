# 🏦 AI-Powered Smart Banking Backend

> A production-grade banking backend built with **Spring Boot Microservices** featuring **AI-powered transaction categorization**, **JWT authentication**, and **real-time financial insights**.

[![Java](https://img.shields.io/badge/Java-25-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

---

## 📋 Table of Contents

- [Project Overview](#-project-overview)
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Getting Started](#-getting-started)
- [API Endpoints](#-api-endpoints)
- [Testing](#-testing)
- [Screenshots](#-screenshots)

---

## 🎯 Project Overview

This project demonstrates a **real-world digital banking backend** system with industry-standard practices:

- ✅ RESTful API design
- ✅ Secure authentication with JWT
- ✅ AI-powered expense categorization
- ✅ Fraud risk detection
- ✅ Financial insights and analytics
- ✅ Layered architecture (Controller → Service → Repository)
- ✅ Exception handling & validation
- ✅ Swagger API documentation

**Perfect for demonstrating** in interviews for Java Backend Developer roles!

---

## ⭐ Features

### 1. **User Authentication & Security** 🔐
- User registration with validation
- Secure login with **JWT tokens**
- **BCrypt password encryption**
- Protected endpoints with Spring Security

### 2. **Transaction Management** 💳
- Create debit/credit transactions
- View transaction history
- Filter by category and date range
- Real-time account balance updates

### 3. **AI-Powered Categorization** 🤖
- **Automatic expense categorization**:
  - Food & Dining
  - Travel & Transportation
  - Bills & Utilities
  - Shopping
  - Entertainment
  - Healthcare
  - Education
  - Salary & Income
  - Investment
- Rule-based keyword matching (expandable to ML models)
- **Fraud risk scoring** based on transaction patterns

### 4. **Financial Insights & Analytics** 📊
- Category-wise spending breakdown
- Monthly spending summaries
- Percentage distribution
- Transaction count by category

### 5. **Developer Experience** 🛠️
- **Swagger UI** for API testing
- **H2 Console** for database inspection
- Comprehensive error messages
- Validation on all inputs

---

## 🚀 Tech Stack

| Technology | Purpose |
|------------|---------|
| **Java 17+** | Programming language |
| **Spring Boot 3.2.2** | Application framework |
| **Spring Data JPA** | Database operations |
| **Spring Security** | Authentication & authorization |
| **JWT (JSON Web Tokens)** | Stateless authentication |
| **H2 Database** | In-memory database |
| **Lombok** | Reduce boilerplate code |
| **Springdoc OpenAPI** | Swagger documentation |
| **Maven** | Build tool |

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────┐
│         Client (Postman / Frontend)             │
└────────────────┬────────────────────────────────┘
                 │ REST API (JSON)
                 ▼
┌─────────────────────────────────────────────────┐
│            Spring Boot Backend                  │
│  ┌──────────────────────────────────────────┐  │
│  │  Controllers (REST Endpoints)            │  │
│  │  - AuthController                        │  │
│  │  - UserController                        │  │
│  │  - TransactionController                 │  │
│  │  - InsightsController                    │  │
│  └─────────────────┬────────────────────────┘  │
│                    │                            │
│  ┌─────────────────▼────────────────────────┐  │
│  │  Services (Business Logic)               │  │
│  │  - AuthService                           │  │
│  │  - UserService                           │  │
│  │  - TransactionService                    │  │
│  │  - AiCategorizationService ✨            │  │
│  │  - InsightsService                       │  │
│  └─────────────────┬────────────────────────┘  │
│                    │                            │
│  ┌─────────────────▼────────────────────────┐  │
│  │  Repositories (Data Access)              │  │
│  │  - UserRepository                        │  │
│  │  - TransactionRepository                 │  │
│  └─────────────────┬────────────────────────┘  │
│                    │                            │
│  ┌─────────────────▼────────────────────────┐  │
│  │  Security Layer                          │  │
│  │  - JWT Authentication Filter             │  │
│  │  - Password Encryption                   │  │
│  └──────────────────────────────────────────┘  │
└────────────────┬────────────────────────────────┘
                 │
                 ▼
┌─────────────────────────────────────────────────┐
│            H2 Database (In-Memory)              │
│            Tables: users, transactions          │
└─────────────────────────────────────────────────┘
```

---

## 🔧 Getting Started

### Prerequisites

- ✅ Java JDK 17 or higher
- ✅ Maven 3.6+
- ✅ Postman (for API testing)

### Installation & Running

1. **Clone the repository**
```bash
git clone https://github.com/yourusername/smart-banking-backend.git
cd smart-banking-backend
```

2. **Build the project**
```bash
mvn clean install
```

3. **Run the application**
```bash
mvn spring-boot:run
```

The server will start on **http://localhost:8080**

4. **Access the application**
- 🌐 **Swagger UI**: http://localhost:8080/swagger-ui.html
- 🗄️ **H2 Console**: http://localhost:8080/h2-console
  - **JDBC URL**: `jdbc:h2:mem:smartbankingdb`
  - **Username**: `sa`
  - **Password**: (leave empty)

---

## 🎨 Premium Frontend (React)
I have added a state-of-the-art **AI Banking Dashboard** to this project!

### Features
- **Glassmorphism UI**: Modern, premium dark theme.
- **AI Feedback**: Real-time spending categorization and fraud risk visualization.
- **Dynamic Charts**: Interactive spending breakdown using Recharts.
- **Secure Flow**: Full JWT integration with Login/Register.

### How to Run:
1. Open a new terminal.
2. Navigate to the frontend folder: `cd banking-ui`
3. Install dependencies: `npm install`
4. Start the dev server: `npm run dev`
5. Open: `http://localhost:5173`

---

## 📡 API Endpoints

### Authentication

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/auth/register` | Register new user | ❌ |
| POST | `/api/auth/login` | Login and get JWT | ❌ |

### User Management

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/users/profile` | Get user profile | ✅ |

### Transactions

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/transactions` | Create transaction | ✅ |
| GET | `/api/transactions` | Get all transactions | ✅ |
| GET | `/api/transactions/category/{category}` | Filter by category | ✅ |
| GET | `/api/transactions/date-range` | Filter by date range | ✅ |

### Insights & Analytics

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/insights/category-wise` | Category-wise spending | ✅ |
| GET | `/api/insights/monthly-summary` | Monthly summary | ✅ |

---

## 🧪 Testing

### Using Postman

1. **Register a user**
```json
POST http://localhost:8080/api/auth/register
Content-Type: application/json

{
  "username": "johndoe",
  "email": "john@example.com",
  "password": "password123",
  "fullName": "John Doe",
  "phoneNumber": "9876543210"
}
```

2. **Login and get JWT token**
```json
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "usernameOrEmail": "johndoe",
  "password": "password123"
}
```

3. **Create a transaction** (use the JWT token from login)
```json
POST http://localhost:8080/api/transactions
Authorization: Bearer <your-jwt-token>
Content-Type: application/json

{
  "type": "DEBIT",
  "amount": 500.00,
  "description": "Lunch at McDonald's restaurant",
  "merchantName": "McDonald's",
  "location": "Mumbai"
}
```

The AI will automatically categorize this as **FOOD** 🍔

4. **Get insights**
```
GET http://localhost:8080/api/insights/category-wise
Authorization: Bearer <your-jwt-token>
```

---

## 📸 Screenshots

> Add screenshots of:
> - Swagger UI
> - Postman requests/responses
> - H2 Database console
> - Transaction categorization demo

---

## 🎓 Learning Outcomes

This project demonstrates:

✅ **Spring Boot** application development  
✅ **RESTful API** design principles  
✅ **Spring Security** with JWT authentication  
✅ **JPA/Hibernate** for database operations  
✅ **Layered architecture** pattern  
✅ **Exception handling** & input validation  
✅ **AI integration** (rule-based + expandable to ML)  
✅ **Swagger** documentation  
✅ **Industry best practices**

---

## 🚀 Future Enhancements

- [ ] Integrate with Python ML model for advanced categorization
- [ ] Add Redis caching for performance
- [ ] Implement rate limiting
- [ ] Add unit and integration tests
- [ ] Deploy with Docker
- [ ] Add email notifications
- [ ] Implement scheduled reports

---

## 📄 License

This project is licensed under the MIT License.

---

## 👨‍💻 Author

**Your Name**  
📧 Email: your.email@example.com  
🔗 LinkedIn: [your-linkedin](https://linkedin.com/in/yourprofile)  
🐙 GitHub: [yourusername](https://github.com/yourusername)

---

## 🙏 Acknowledgments

Built with ❤️ using Spring Boot framework and industry best practices.

Perfect for demonstrating in **Java Backend Developer** interviews! 🎯