# 🛍️ Dety Parfum – Store Management System

![Java](https://img.shields.io/badge/Java-17+-red?style=flat-square\&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=flat-square\&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue?style=flat-square\&logo=postgresql)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-green?style=flat-square\&logo=swagger)
![Status](https://img.shields.io/badge/Status-In%20Development-yellow?style=flat-square)

Dety Parfum is a **full-stack store management system** designed for **cosmetics and perfume retail**. The project focuses on **backend robustness, clean architecture, and real-world business rules**, serving as a professional portfolio project for **Java Backend development**.

---

## 🚀 Project Goals

* Build a **production-ready backend** using Java and Spring Boot
* Apply **clean code and layered architecture** principles
* Implement **real business rules** found in retail systems
* Provide a solid and well-documented **RESTful API**
* Serve as a scalable base for future features (auth, reports, uploads, etc.)

---

## 🧱 Architecture Overview

The project follows a **layered architecture**, ensuring separation of concerns and maintainability:

* **Controller Layer** – REST endpoints and request/response handling
* **Service Layer** – Business rules and validations
* **Repository Layer** – Database access using Spring Data JPA
* **DTO Layer** – Data transfer objects to isolate domain entities
* **Exception Layer** – Centralized and custom exception handling

---

## 🧰 Tech Stack

### Backend

* Java 17
* Spring Boot
* Spring Security
* Spring Data JPA / Hibernate
* REST APIs
* Swagger / OpenAPI

### Database

* PostgreSQL

### Frontend

* HTML
* Tailwind CSS
* JavaScript

### Tools

* Maven
* Git & GitHub
* Postman
* Docker (planned)

---

## ✨ Features

### Core Modules

* Customers management
* Products and categories
* Orders and order items
* Payments

### Business Rules

* Automatic **stock deduction** when an order is created
* Orders can be completed **even with insufficient stock** (configurable behavior)
* Product price is initially loaded from the product but **can be updated from the order**
* When the price is updated in an order, the **product price is updated in the database**
* Support for **manual installment payments** (cash / Pix)

### Additional Features

* Dashboard with sales charts (Chart.js)
* Centralized exception handling
* DTO-based API communication
* Clean and organized package structure

---

## 📂 Project Structure

```
com.detyparfum
 ├── controller
 ├── service
 ├── repository
 ├── dto
 ├── entity
 ├── exception
 ├── config
 └── DetyParfumApplication.java
```

---

## 📖 API Documentation

The API is fully documented using **Swagger**.

After running the project, access:

```
http://localhost:8080/swagger-ui.html
```

---

## ⚙️ How to Run the Project

### Prerequisites

* Java 17+
* PostgreSQL
* Maven

### Steps

```bash
# Clone the repository
git clone https://github.com/kaisergui/StoreManagement.DetyParfum.git

# Enter the project directory
cd StoreManagement.DetyParfum

# Run the application
./mvnw spring-boot:run
```

Configure the database connection in `application.properties` before running.

---

## 🧪 Testing (Planned)

* Unit tests with JUnit
* Mockito for service layer testing
* Integration tests for controllers

---

## 🔮 Roadmap

* Authentication and authorization (JWT)
* Role-based access control
* Advanced reports and exports (CSV / PDF)
* Product image upload
* Pagination, filtering and search
* Dockerized environment

---

## 👨‍💻 Author

**Guilherme Kaiser**
Java Backend Developer

* GitHub: [https://github.com/kaisergui](https://github.com/kaisergui)
* LinkedIn: [https://www.linkedin.com/](www.linkedin.com/in/guilhermekaiserds)

---

⭐ *This project is under active development and continuously evolving as part of my backend portfolio.*
