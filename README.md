# <img src="assets/logo.svg" width="28" height="28" style="vertical-align: middle;" /> Student Management System

A CLI Java application for managing student records with MySQL and JDBC. Features input validation, parameterized queries, and analytics reports.

[![Interface](https://img.shields.io/badge/Interface-CLI%20%2F%20Terminal-1f2937?style=flat-square&logo=gnubash&logoColor=white)](#)
[![Database](https://img.shields.io/badge/Database-MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white)](https://www.mysql.com/)

---

## Preview

| Console Menu | Top Performers | Age Analysis |
| :---: | :---: | :---: |
| <img src="assets/console_demo.png" width="280" alt="Console Menu" /> | <img src="assets/top_performers_overview.png" width="280" alt="Top Performers" /> | <img src="assets/age_range_analysis.png" width="280" alt="Age Analysis" /> |

---

## Features

- **CRUD Operations**: Add, view, update, and delete student records with automated database synchronization.
- **Targeted Search**: Query student records by unique ID, partial name matching (`LIKE`), or letter grade (`O`, `E`, `A`, `B`, `C`, `D`, `F`).
- **Analytics & Reports**: Generate grade distribution breakdowns, age-range filtering, summary statistics (average age, total count), and top 10 academic performers.
- **Input Validation**: Enforce alphabetical regex rules on names, validate age boundaries (5–120), and sanitize grade inputs.
- **SQL Security**: Execute all database transactions through parameterized `PreparedStatement` queries to prevent SQL injection vulnerabilities.

---

## Tech Stack

- **Backend**: Java 21, JDBC (MySQL Connector/J 9.2.0), Maven 3.9+, JUnit 5 (Jupiter 5.12.1)
- **Database**: MySQL 8.0+
- **Interface**: Interactive Terminal / Command-Line Interface (CLI)
- **AI Tooling**: Antigravity, Cursor

---

## Project Structure

```text
student-management-system/
├── assets/                             # Demo screenshots and branding assets
├── database/
│   └── schema.sql                      # MySQL schema and table definitions
├── src/
│   ├── main/
│   │   ├── java/.../management/
│   │   │   ├── main/Main.java          # CLI menu loop and interaction handler
│   │   │   ├── model/Student.java      # Student entity data model
│   │   │   ├── service/StudentManager.java # Business logic and SQL queries
│   │   │   └── util/DBConnection.java  # JDBC connection loader and manager
│   │   └── resources/
│   │       └── database.properties.example # DB credentials configuration template
│   └── test/java/.../
│       └── StudentManagerTest.java     # Unit test suite for service layer
├── pom.xml                             # Maven build and dependency configuration
└── README.md                           # Project documentation
```

---

## Getting Started

### Prerequisites

- **Java**: JDK `21+`
- **Maven**: `3.9+`
- **MySQL Server**: `8.0+`

### 1. Database Setup

Initialize the MySQL database and schema:

- **PowerShell (Windows)**:
  ```powershell
  Get-Content database/schema.sql | mysql -u root -p
  ```

- **CMD (Windows)**:
  ```cmd
  mysql -u root -p < database\schema.sql
  ```

- **Unix / macOS**:
  ```bash
  mysql -u root -p < database/schema.sql
  ```

### 2. Configuration

Copy the template configuration file:

- **PowerShell (Windows)**:
  ```powershell
  Copy-Item src\main\resources\database.properties.example src\main\resources\database.properties
  ```

- **CMD (Windows)**:
  ```cmd
  copy src\main\resources\database.properties.example src\main\resources\database.properties
  ```

- **Unix / macOS**:
  ```bash
  cp src/main/resources/database.properties.example src/main/resources/database.properties
  ```

Update `src/main/resources/database.properties` with your database credentials:

```properties
db.url=jdbc:mysql://localhost:3306/students_db
db.username=your_user
db.password=your_password
```

### 3. Run Application

Execute the CLI application via Maven:

- **All Platforms**:
  ```bash
  mvn clean compile exec:java -Dexec.mainClass="com.student.management.main.Main"
  ```

*(Or run `Main.java` directly within IntelliJ IDEA, Eclipse, or VS Code).*

### 4. Run Tests

Execute the JUnit test suite:

- **All Platforms**:
  ```bash
  mvn test
  ```

---

## Author

**Shreyan Sardar**
- **Portfolio**: [shreyandev.vercel.app](https://shreyandev.vercel.app)
- **GitHub**: [@ShreyanDev5](https://github.com/ShreyanDev5)
- **LinkedIn**: [shreyansardar](https://www.linkedin.com/in/shreyansardar/)
