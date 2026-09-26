# Employee Management System

A Spring Boot application to manage employee records with CRUD operations.  
Built with **Java, Spring Boot, MySQL, and REST APIs**.

## Features
- Add new employees
- Update employee details
- Delete employees
- View employee list
- RESTful API endpoints for integration

## Tech Stack
- **Backend**: Java, Spring Boot
- **Database**: MySQL (Spring Data JPA)
- **Build Tool**: Maven
- **Other**: Lombok

## Setup Instructions
1. Clone the repository:
   ```bash
   git clone https://github.com/ChaitraShivanagoudaPatil/employee-management-spring-boot
2.Navigate to the project folder:
cd employee-management
3.Configure application.properties with your MySQL credentials:
spring.datasource.url=jdbc:mysql://localhost:3306/employees
spring.datasource.username=root
spring.datasource.password=yourpassword
4.Run the application:
mvn spring-boot:run

API Endpoints
POST /employees → Add new employee

GET /employees → Get all employees

GET /employees/{id} → Get employee by ID

PUT /employees/{id} → Update employee

DELETE /employees/{id} → Delete employee


Future Enhancements
Role-based authentication (Spring Security + JWT)

Pagination and sorting

Dockerized deployment


