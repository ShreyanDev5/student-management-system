# 📱 Student Management System

A console-based Java application for managing student records in a MySQL database. It uses JDBC with parameterized queries for secure database access.

---

## 📸 Application Demos

Here are screenshots of the console interface and reporting features:

### 🎮 Main Interactive Console
<img src="assets/console_demo.png" alt="Console Interface Demo" width="350">

### 🏆 Top Performers Overview
<img src="assets/top_performers_overview.png" alt="Top Performers Overview" width="350">

### 📊 Age-Range Analysis Report
<img src="assets/age_range_analysis.png" alt="Age-Range Analysis Report" width="350">

---

## ✨ Features

- **🎓 Student Management (CRUD):** Add, view, search (by ID, name, or grade), update, and delete student records.
- **📈 Reporting:** Generate reports for grade distribution, age ranges, summary statistics (averages, counts), and the top 10 performers.
- **🛡️ Secure Database Queries:** Uses JDBC `PreparedStatement` to protect against SQL injection.
- **📁 Clean Architecture:** Separates model, service, and database configuration layers.

---

## 🛠️ Requirements

- **Java Development Kit (JDK):** 21 or later
- **Maven:** 3.9+
- **MySQL Server:** 8.0+

---

## 🚀 Setup & Installation

### 1. Database Setup
Start MySQL and initialize the database using the provided script:
```bash
mysql -u root -p < database/schema.sql
```
*(Alternatively, run the SQL commands from `database/schema.sql` inside your SQL client).*

### 2. Configure Properties
1. Go to `src/main/resources`.
2. Copy `database.properties.example` and rename the copy to `database.properties`.
3. Open `database.properties` and update `db.username` and `db.password` with your MySQL credentials.

---

## 🏃 Running the Application

### Using an IDE
1. Open the project in your IDE (e.g., IntelliJ IDEA, Eclipse, or VS Code).
2. Allow the IDE to import the Maven project dependencies.
3. Run the `Main.java` class located in `src/main/java/com/student/management/main/Main.java`.

### Using the Command Line
Compile and run the app with Maven:
```bash
mvn clean compile exec:java -Dexec.mainClass="com.student.management.main.Main"
```

---

## 🧪 Testing

Run the unit tests using Maven:
```bash
mvn test
```

