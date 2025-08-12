
# 📚 Java Spring Boot Digital Library Application

A **Digital Library Management System** built using **Java Spring Boot** and **PostgreSQL**.  
It allows users to manage books, memberships, and borrowing/return transactions with real-world business rules.

---

## 📌 Features
- Add, update, and delete books in the library.
- Manage user memberships with different statuses.
- Prevent invalid status changes (e.g., **Expired → Active** not allowed).
- Track book borrowing and return transactions.
- Fetch detailed membership and user information via REST APIs.
- Database integration with **PostgreSQL**.

---

## 🛠️ Tech Stack
- **Backend:** Java, Spring Boot, Spring MVC, Spring Data JPA, Hibernate
- **Database:** PostgreSQL
- **Build Tool:** Maven
- **IDE:** IntelliJ IDEA / Eclipse
- **Version Control:** Git, GitHub

---
## 📂 Project Structure

digital-library/
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── org.digitalLibrary/
│ │ └── resources/
│ │ └── application.properties
│ └── test/
│ └── java/
│ └── org.digitalLibrary/
├── pom.xml
└── README.md

## FrontEnd
Access Endpoints via Postman

## Usage

**Books API** : Manage book records.

**Membership API** : Handle membership creation, updates, and rules.

**Transactions API** : Borrow and return books.

**Validation**: Business rules applied before saving/updating records.