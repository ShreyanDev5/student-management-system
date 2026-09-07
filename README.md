# Student Management System

Console-based Java application for managing student records and generating SQL analytics with MySQL and JDBC.

[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](#)
[![Database](https://img.shields.io/badge/Database-MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white)](https://www.mysql.com/)

---

## Preview

| Main Dashboard | Student Lookup |
| :---: | :---: |
| <img src="assets/dashboard.png" width="420" alt="Main Dashboard" /> | <img src="assets/student_lookup.png" width="420" alt="Student Lookup" /> |
| **Top Performers (SQL Sort)** | **Summary Statistics (Aggregations)** |
| <img src="assets/top_performers.png" width="420" alt="Top Performers" /> | <img src="assets/summary_statistics.png" width="420" alt="Summary Statistics" /> |

---

## Features

- **Student Records:** Add, view, update, and delete student records backed by MySQL.
- **Flexible Search:** Query records by ID, partial name (`LIKE`), or letter grade.
- **SQL Analytics:** Grade distributions with percentage shares, age-range filters, summary stats, and top performers.
- **Input Validation:** Strict type and boundary checks on names, ages (5–120), and letter grades.
- **Parameterized Queries:** All SQL queries use `PreparedStatement` to prevent SQL injection.

---

## Tech Stack

- **Core:** Java 21, JDBC
- **Database:** MySQL 8.0+
- **Build & Test:** Maven, JUnit 5

---

## Project Structure

```text
student-management-system/
├── assets/                             # Preview screenshots
├── database/
│   └── schema.sql                      # Database schema and sample data
├── src/
│   ├── main/
│   │   ├── java/com/student/management/
│   │   │   ├── main/Main.java          # CLI interface and menu router
│   │   │   ├── model/Student.java      # Student data model
│   │   │   ├── service/StudentManager.java # Business logic and JDBC queries
│   │   │   └── util/DBConnection.java  # Database connection manager
│   │   └── resources/
│   │       └── database.properties.example
│   └── test/java/com/student/management/
│       └── StudentManagerTest.java
└── pom.xml
```

---

## Getting Started

### Prerequisites

- **Java**: JDK `21+`
- **Database**: MySQL Server `8.0+`
- **Build**: Maven `3.9+`

### 1. Database Setup

Import the schema into MySQL:

```bash
mysql -u root -p < database/schema.sql
```
*(Windows PowerShell: `Get-Content database/schema.sql | mysql -u root -p`)*

### 2. Configuration

Copy the example configuration:

```bash
cp src/main/resources/database.properties.example src/main/resources/database.properties
```
*(Windows CMD: `copy src\main\resources\database.properties.example src\main\resources\database.properties`)*

Update `src/main/resources/database.properties` with your database credentials:

```properties
db.url=jdbc:mysql://localhost:3306/students_db
db.username=your_user
db.password=your_password
```

### 3. Run Application

```bash
mvn compile exec:java -Dexec.mainClass="com.student.management.main.Main"
```

*(Or run [`Main.java`](src/main/java/com/student/management/main/Main.java) directly within IntelliJ IDEA, Eclipse, or VS Code).*

### 4. Run Tests

```bash
mvn test
```

---

## Author

**Shreyan Sardar**
- **Portfolio**: [shreyandev.vercel.app](https://shreyandev.vercel.app)
- **GitHub**: [@ShreyanDev5](https://github.com/ShreyanDev5)
- **LinkedIn**: [shreyansardar](https://www.linkedin.com/in/shreyansardar/)
