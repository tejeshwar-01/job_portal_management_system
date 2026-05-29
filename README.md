# Job Portal Management System

A Full-Stack Job Portal application built using **Java, Spring Boot, Spring Security, Spring Data JPA, and Thymeleaf**. It provides a robust platform for job seekers to apply for roles, employers to post job openings, and seamless interaction between the two via role-based access control.

## Project Overview

This project includes key features necessary for a job listing and application platform, including:

- **Role-Based Access Control:** Differentiated experiences for Job Seekers (Students) and Employers.
- **Job Management:** Employers can post and manage job listings. Users can browse and apply to these jobs.
- **Application Tracking:** Built-in statuses and tracking for submitted job applications (e.g., Application Status, Employment Status).
- **File Upload System:** Ability to upload files such as resumes. Upload limits are set to 50MB per file and are stored locally in an `uploads` directory.
- **User Authentication:** Secured with Spring Security and configured for role-based capabilities.

### Key Entities
- **User:** Accounts for both Employers and Job Seekers.
- **Job:** Details and requirements of the posted job.
- **Application:** Links the Job Seeker to the posted Job, tracking application status.

## Technologies Used
- Java 17
- Spring Boot 3.2.4
- Spring Security
- Spring Data JPA (Hibernate)
- Thymeleaf (Server-Side UI Rendering)
- Web MVC & Validation
- **Database:** MySQL

---

## Database Configuration

This project is configured to use a **MySQL Database** for reliable and structured data persistence.

### Database Details
- **Database Used:** MySQL
- **Database Name:** `inventorydb`

### Connection Configuration
In the `src/main/resources/application.properties` file, the following database configurations are used:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/inventorydb?createDatabaseIfNotExist=true
spring.datasource.username=your username
spring.datasource.password=your password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

#### Explanation of MySQL Settings:
- `spring.datasource.url`: The application connects to MySQL running on the local machine (`localhost`) at the default port `3306` inside the `inventorydb` database. The `createDatabaseIfNotExist=true` flag ensures that Spring Boot will automatically create the database `inventorydb` if you haven't created it in MySQL manually.
- **Database Credentials**: 
  - **Username**: your username
  - **Password**: your password
  *(Ensure you update these credentials if your local MySQL instance has a different user or password setup).*
- `spring.jpa.hibernate.ddl-auto=update`: Hibernate will automatically manage the schema, updating existing tables with new entity changes without dropping the data.

## Getting Started

1. **Prerequisites:** Ensure you have **Java 17**, **Maven**, and **MySQL** server installed locally.
2. **Database Setup:** Run your MySQL server. Since the properties are set to automatically create the database, you only need to ensure the username and password  exist in your MySQL server with adequate permissions.
3. **Build & Run:** 
   Run the project using Maven:
   ```sh
   mvn spring-boot:run
   ```
4. Access the web interface typically at `http://localhost:8080/`.
