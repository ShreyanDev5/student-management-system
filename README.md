# 📱 Student Management System

A sleek, lightweight, console-based Java application for managing student records stored in a MySQL database. Built using standard JDBC connectivity with parameterized queries for robust and secure data access.

---

## 📸 Application Demos

Here is the system in action, showing the robust terminal user interface and reporting engine:

### 🎮 Main Interactive Console
![Console Interface Demo](assets/console_demo.png)

### 🏆 Top Performers Overview
![Top Performers Overview](assets/top_performers_overview.png)

### 📊 Age-Range Analysis Report
![Age-Range Analysis Report](assets/age_range_analysis.png)

---

## ✨ Features

- **🎓 Student Operations (CRUD):** Add new students, view all records, search dynamically by ID, name, or grade, update information, and remove records.
- **📈 Advanced Reporting:** Generate real-time reports including grade distributions, age-range filters, summary statistics (averages, counts), and top 10 academic performers.
- **🛡️ Secure Data Access:** Utilizes parameterized `PreparedStatement` JDBC queries to fully protect against SQL injection vulnerabilities.
- **📁 Organized Structure:** Clean architectural separation of models, service layers, and configuration properties.

---

## 🛠️ Requirements

- **Java Development Kit (JDK):** Version 21 or later
- **Build Tool:** Maven 3.9+
- **Database:** MySQL Server 8.0+

---

## 🚀 Setup & Installation

### 1. Database Setup
Ensure your MySQL server is running, and initialize the database schema using the script provided:
```bash
mysql -u root -p < database/schema.sql
```
*(Alternatively, copy and run the SQL commands from `database/schema.sql` in your database IDE like MySQL Workbench).*

### 2. Configure Properties
Create your local database configuration file from the template provided:
1. Navigate to the resources folder: `src/main/resources`
2. Duplicate `database.properties.example` and name the copy `database.properties`.
3. Open `database.properties` and replace the default credentials with your MySQL server `db.username` and `db.password`.

---

## 🏃 Running the Application

### Via IDE
1. Open the project in your favorite Java IDE (e.g., IntelliJ IDEA, VS Code).
2. Let the IDE import dependencies from `pom.xml`.
3. Run `Main.java` inside the `com.student.management.main` package.

### Via Command Line
Compile and run the project using Maven:
```powershell
mvn clean compile exec:java -Dexec.mainClass="com.student.management.main.Main"
```

---

## 🧪 Testing

To run the unit tests:
```powershell
mvn test
```

