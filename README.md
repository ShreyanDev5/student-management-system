# 🎓 Student Management System

A beginner-friendly Java-based project that lets you manage student records via a console interface. It’s a great way to learn key concepts like JDBC, Maven, and basic console UI development.

> 💡 *My first Java project started small and has grown steadily in features and robustness.*

---

## 🚀 Overview

- **Purpose:** Manage student records using a console-based application.
- **Core Features:**
    - CRUD operations (Create, Read, Update, Delete)
    - **MySQL Database Integration:** Upgraded from file-based storage for better data management.
    - Input validation and error handling for a smoother user experience.

---

## 📸 Demo

Experience a quick look at the console interface:

<div align="center">
  <img src="assets/console_demo.png" width="300">
  <br>
  <em>Figure 1: Console-based student management interface</em>
</div>

---

## 🛠️ Technologies Used

- ![Java](https://img.shields.io/badge/Java-21-blue)
- ![MySQL](https://img.shields.io/badge/MySQL-9.2.0-orange)
- ![Maven](https://img.shields.io/badge/Maven-3.9-red)

---

## ✅ Prerequisites

Before you start, make sure you have:

- **Java 21:** Check with:
    ```powershell
    java -version
    ```
- **Maven 3.9+:** Check with:
    ```powershell
    mvn -version
    ```
- **MySQL 9.2.0:** Ensure it’s installed and running.
- **IDE:** IntelliJ IDEA is recommended for seamless development.

---

## ⚙️ Features & Usage

### Basic Operations

- **Adding a Student:**
    - The system will prompt you for:
        ```
        Enter student name: John Doe
        Enter age: 20
        Enter grade: A
        ```

- **Displaying Students:**
    - See a list of students with details:
        ```
        ID: 1 | Name: John Doe | Age: 20 | Grade: A
        ```

### Advanced Features

- **Database Integration:**
    - Uses JDBC with `PreparedStatement` for secure SQL operations.
    - Upgraded from a file-based system to a robust MySQL database.
- **Input Validation & Error Handling:**
    - Checks for valid entries (e.g., proper age ranges, correct grade formats) and handles exceptions gracefully.
- **Reporting Tools:**
    - 📈 **Grade Distribution Report:**  
      Visualizes student grade patterns and trends.

    - 👥 **Age-Range Analysis:**
      
      Displays the distribution of students across different age groups.
      
      <div align="center">
        <img src="assets/age_range_analysis.png" width="250">
        <br>
        <em>Figure 2: Age-range analysis of students</em>
      </div>  

    - 🔢 **Summary Statistics:**  
      Provides aggregate data like average age, total students, etc.

    - 🏆 **Top Performers Overview:**
      
      Highlights students with the highest academic performance.
      
      <div align="center">
        <img src="assets/top_performers_overview.png" width="250">
        <br>
        <em>Figure 3: Top performers overview</em>
      </div>  

---

## 📂 Project Structure

```
StudentManagementSystem
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.student.management
│   │   │       ├── main
│   │   │       │   └── Main.java           // Entry point
│   │   │       ├── model
│   │   │       │   └── Student.java        // Student entity
│   │   │       ├── service
│   │   │       │   └── StudentManager.java // Core logic
│   │   │       └── util
│   │   │           └── DBConnection.java   // Database utility
│   │   └── resources
│   │       └── database.properties         // DB configuration
│   ├── test
│   │   └── java
│   │       └── com.student.management      // Unit tests
├── pom.xml                                 // Maven configuration
└── (other project files)
```

---

## 🗓️ Milestones & Development Journey

- **Feb 22, 2025:** Project foundation established.
- **Mar 2, 2025:** Completed advanced reporting features.
- **Mar 20, 2025:** Implemented database connection (upgraded from file-based storage).
- **Mar 21, 2025:** Configured JDBC integration & Maven dependencies.

> ✅ Key takeaway: Develop incrementally, validate your code frequently, and build upon your successes!
