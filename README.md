# 🏦 AI-Powered Smart-Banking Platform

> **Advanced Digital Banking Suite** featuring a Microservices-inspired bridge between Spring Boot (Java) and PyTorch (Python).

---

## 🏗️ Technical Architecture
This project demonstrates a high-performance **Microservices-lite** approach, decoupling heavy business logic from specialized AI computations.

### 🌐 The "Java-Python Bridge"
*   **Core Engine (Spring Boot 3.2.2):** Handles user accounts, transaction ledgers, and secure session management.
*   **AI Micro-Service (FastAPI/Python):** Offloads computationally expensive tasks like Neural Network inference and Anomaly Detection.
*   **Communication:** Inter-service data exchange via high-speed RESTful protocols with a focus on fault-tolerance.

---

## ⭐ Advanced Technical Highlights

### 1. **Stateless Security Architecture** 🔐
*   **JWT zero-trust model**: Implemented a custom `OncePerRequestFilter` to validate tokens without server-side session overhead.
*   **BCrypt Salting**: Industry-standard password hashing with defensive salt-and-pepper patterns.

### 2. **Transaction Intelligence (ML)** 🤖
*   **Heuristic + Neural Categorization**: COMBINES fast keyword heuristics with an **NLP model** for high-accuracy spending analysis.
*   **Unsupervised Anomaly Detection**: Uses **Isolation Forest** algorithms to detect fraudulent patterns without needing labeled historical fraud data—crucial for emerging banking platforms.

### 3. **High-Performance Persistence** �
*   **Data Consistency**: Leverages Spring's `@Transactional` boundary manager to ensure atomicity in complex multi-step banking operations.
*   **Optimized Queries**: Custom `@Query` aggregations for Category-Wise insights to minimize database round-trips.

---

## 🚀 Tech Stack & Design Patterns
*   **Backend:** Java 25 (Optimized for Virtual Threads), Spring Boot, Hibernate (JPA).
*   **AI/ML:** Python 3.9, PyTorch (Neural Networks), Scikit-Learn (Isolation Forest).
*   **Frontend:** React 18, Vite (60FPS rendering), Recharts (SVG Analytics).
*   **Design Patterns:** DTO Pattern, Repository Pattern, Singleton, Strategy Pattern (for categorization).

---

## 🔧 Deployment Readiness
The system is packaged as a **Self-Contained Executable (JAR)**, featuring embedded server logic (Tomcat) and externalized configuration for immediate cloud deployment.

### 1. Quick Start
```bash
# Backend
mvn clean install
mvn spring-boot:run

# AI Service
cd ml-service
python main.py

# Frontend
cd banking-ui
npm install && npm run dev
```

---
*Built with a focus on Scalability, Intelligence, and Modern Backend Engineering.*
