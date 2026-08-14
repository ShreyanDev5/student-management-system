# Student Management System

A CLI Java application for managing student records with MySQL and JDBC. Features input validation, parameterized queries, and analytics reports.

---

## Demo

<div align="center">

| Console Menu | Top Performers | Age Analysis |
| :---: | :---: | :---: |
| <img src="assets/console_demo.png" alt="Console Interface" width="280"> | <img src="assets/top_performers_overview.png" alt="Top Performers" width="280"> | <img src="assets/age_range_analysis.png" alt="Age-Range Analysis" width="280"> |

</div>

---

## Features

- **CRUD Operations:** Add, view, update, and delete student records.
- **Targeted Search:** Filter by ID, partial name (`LIKE`), or grade (`O`, `E`, `A`, `B`, `C`, `D`, `F`).
- **Analytics & Reports:** Grade distribution, age-range filtering, summary statistics (count, average age), and top 10 performers.
- **Input Validation:** Strict regex and boundary checks for names, age (5–120), and valid grades.
- **SQL Security:** Parameterized `PreparedStatement` queries across all operations to prevent SQL injection.

---

## Tech Stack

| Java | Database | Driver | Build | Testing |
| :--- | :--- | :--- | :--- | :--- |
| Java 21 | MySQL 8.0+ | MySQL Connector/J 9.2.0 | Maven 3.9+ | JUnit 5 (Jupiter 5.12.1) |

---

## Project Structure

```text
student-management-system/
├── assets/                           # Screenshots
├── database/schema.sql               # DB schema & tables
├── src/main/java/.../
│   ├── main/Main.java                # CLI menu loop
│   ├── model/Student.java            # Student entity model
│   ├── service/StudentManager.java   # Business logic & SQL queries
│   └── util/DBConnection.java        # JDBC connection loader
├── src/main/resources/
│   └── database.properties.example   # DB configuration template
└── pom.xml
```

---

## Quickstart

### 1. Initialize Database
```bash
# Windows (PowerShell)
Get-Content database/schema.sql | mysql -u root -p

# macOS / Linux / Git Bash
mysql -u root -p < database/schema.sql
```

### 2. Configure Credentials
Copy the template to `database.properties`:
```bash
# Windows (PowerShell)
Copy-Item src/main/resources/database.properties.example src/main/resources/database.properties

# macOS / Linux / Git Bash
cp src/main/resources/database.properties.example src/main/resources/database.properties
```

Update `src/main/resources/database.properties` with your credentials:
```properties
db.url=jdbc:mysql://localhost:3306/students_db
db.username=your_user
db.password=your_password
```

### 3. Run Application
```bash
mvn clean compile exec:java -Dexec.mainClass="com.student.management.main.Main"
```
*(Or run `Main.java` directly in IntelliJ IDEA, Eclipse, or VS Code).*

### 4. Run Tests
```bash
mvn test
```
