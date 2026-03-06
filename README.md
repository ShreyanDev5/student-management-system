# Student Management System

Console-based Java application for managing student records stored in MySQL.

## Features

- Add, view, search, update, and delete student records
- Search by ID, name, or grade
- Generate grade, age-range, summary, and top-performer reports
- Use JDBC `PreparedStatement` queries for database access

## Project Flow

- `Main` shows the menu and reads user input
- `StudentManager` validates input, runs SQL queries, and prints results
- `Student` represents one student record in memory
- `DBConnection` loads database settings and opens JDBC connections

## Requirements

- Java 21
- Maven 3.9+
- MySQL running locally

## Setup

1. Create a database named `students_db`.
2. Create the `students` table:

```sql
CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    grade VARCHAR(5) NOT NULL
);
```

3. Update `src/main/resources/database.properties` with your own MySQL credentials.
4. Run `Main.java` from your IDE.

## Test

```powershell
mvn test
```
