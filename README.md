# Online-E-Commerce-Shopping-Platform
Task submission by Student Code: DAS004858 Task: Online E-Commerce Shopping Platform
# E-Commerce Shopping Platform (Java Full Stack Internship)

* **Student Name:** Parameshwaran
* **Student Code:** DAS-JV-001
* **Task ID:** 121
* **Reference ID:** DAS004858
* **Internship Provider:** Data Alcott Systems

---

## 📌 Project Overview
This project is an E-Commerce Shopping Platform built as part of the Free Java Full Stack Internship program[cite: 1]. The application follows a robust MVC architecture, connecting a modern frontend user interface with a relational MySQL database via Spring Boot and Hibernate[cite: 1].

---

## 🛠️ Tech Stack
* **Backend:** Java, Spring Boot, Spring Security, Spring Data JPA (Hibernate)
* **Frontend:** Thymeleaf, Tailwind CSS, HTML5
* **Database:** MySQL
* **Build Tool:** Maven

---

## ✨ Core Features Implemented
* **User Registration & Login (Spring Security):** Secure authentication system utilizing encrypted passwords via BCrypt before storage in the MySQL database[cite: 1].
* **Product Catalog & Search:** Dynamic product display with a functional search bar that queries the database using Spring Data JPA to filter products by name or category[cite: 1].
* **Shopping Cart Management:** Users can add products to their active cart, manage quantities, view dynamically calculated totals, and tie sessions securely to the database[cite: 1].
* **Admin Inventory Controls:** Protected administrative routes for managing products and database records[cite: 1].

---

## 🚀 Getting Started & Installation

### 1. Prerequisites
* Java JDK 17 or higher
* MySQL Server installed locally
* Maven

### 2. Database Setup
Create a database named `ecommerce_db` in your MySQL instance:

CREATE DATABASE ecommerce_db;

3. Configure Properties
Update src/main/resources/application.properties with your local MySQL credentials:

Properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db?createDatabaseIfNotExist=true&useSSL=false
spring.datasource.username=root
spring.datasource.password=your_mysql_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
4. Run the Application
Run the application using Maven or directly through your IDE (IntelliJ IDEA):

Bash
mvn spring-boot:run
Access the application in your web browser at: http://localhost:8080

🎥 Video Demonstration
The video walkthrough showcasing the application structure, code, database records, and core feature demonstrations has been recorded and submitted as part of the project requirements.
