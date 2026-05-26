package com.student.management.service;

import com.student.management.model.Student;
import com.student.management.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Service class handling student records management, data validation, and database operations.
 * Acts as the intermediate business layer between the CLI interface (Main) and the persistence layer.
 * Performs user input validation, manages JDBC resources using try-with-resources blocks,
 * maps ResultSet records to domain models, and outputs operations outcomes and analytics to the console.
 */
public class StudentManager
{
    // -------------------------------------------------
    // Console Display Methods
    // -------------------------------------------------

    /**
     * Prints a collection of student records to the console in a structured ASCII table.
     *
     * @param students List of Student objects to be formatted and printed
     */
    public void displayStudents(List<Student> students)
    {
        System.out.println("\n");

        // Print table header row
        System.out.println("--------------------------------------------");
        System.out.printf("%-8s | %-18s | %-4s | %-5s%n", "ID", "Name", "Age", "Grade");
        System.out.println("--------------------------------------------");

        // Format and print each student record as a row in the table
        for (Student student : students)
        {
            System.out.printf("%-8d | %-18s | %-4d | %-5s%n",
                    student.getId(),
                    student.getName(),
                    student.getAge(),
                    student.getGrade());
        }
        System.out.println("--------------------------------------------");

        // Inform the user if the list is empty
        if (students.isEmpty())
        {
            System.out.println("\n❌ No student records found.");
        }
    }

    // -------------------------------------------------
    // CRUD Operations (Create, Read, Update, Delete)
    // -------------------------------------------------

    /**
     * Solicits validated student details (name, age, grade) and inserts a new student record.
     * Note: The database automatically generates the unique student ID.
     *
     * @param scanner Scanner instance to capture input fields
     */
    public void addStudentFromInput(Scanner scanner)
    {
        System.out.println("\nPlease enter the student details:");

        // Solicit and validate user input parameters sequentially
        String name = validateName(scanner);
        int age = validateAge(scanner);
        String grade = validateGrade(scanner);

        String query = "INSERT INTO students (name, age, grade) VALUES (?, ?, ?)";

        // Execute parameterized insert statement using try-with-resources for resource cleanup
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query))
        {
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, grade);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0)
            {
                System.out.println("\n✅ Student '" + name + "' added successfully.");
            }
            else
            {
                System.out.println("❌ Failed to add student '" + name + "'.");
            }
        }
        catch (SQLException e)
        {
            System.out.println("❌ Database error: Unable to add student. Please try again.");
            e.printStackTrace(); // TODO: Integrate a professional logging library for production environments
        }
    }

    /**
     * Queries and displays all student records stored in the database.
     */
    public void viewAllStudents()
    {
        String sql = "SELECT * FROM students";
        List<Student> students = new ArrayList<>();

        // Fetch records and automatically release JDBC statements and connection objects
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery())
        {
            // Iterate over the result set to construct the list of student domain models
            while (rs.next())
            {
                students.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("grade")));
            }
            displayStudents(students);
        }
        catch (SQLException e)
        {
            System.out.println("❌ Database error: Unable to fetch students.");
            e.printStackTrace();
        }
    }

    /**
     * Queries the database for a student record matching a unique ID.
     *
     * @param id The unique identifier of the target student
     * @return Student domain model if found, null otherwise
     */
    public Student searchById(int id)
    {
        String sql = "SELECT * FROM students WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                return new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("grade"));
            }
        }
        catch (SQLException e)
        {
            System.out.println("❌ Database error: Unable to search for student.");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Queries and displays student records matching a name filter (supports partial matches via SQL LIKE).
     *
     * @param scanner Scanner instance to capture name substring
     */
    public void searchByName(Scanner scanner)
    {
        System.out.print("\nEnter student name: ");
        String name = validateName(scanner);
        String sql = "SELECT * FROM students WHERE name LIKE ?";

        List<Student> students = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql))
        {
            // Bind input using standard SQL wildcards for partial match filtering
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                students.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("grade")));
            }
            displayStudents(students);
        }
        catch (SQLException e)
        {
            System.out.println("❌ Database error: Unable to search by name.");
            e.printStackTrace();
        }
    }

    /**
     * Queries and displays student records matching a specific academic grade.
     *
     * @param scanner Scanner instance to capture grade code
     */
    public void searchByGrade(Scanner scanner)
    {
        System.out.print("\nEnter grade: ");
        String grade = validateGrade(scanner);
        String sql = "SELECT * FROM students WHERE grade = ?";

        List<Student> students = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, grade);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                students.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("grade")));
            }
            displayStudents(students);
        }
        catch (SQLException e)
        {
            System.out.println("❌ Database error: Unable to search by grade.");
            e.printStackTrace();
        }
    }

    /**
     * Deletes a student record from the database based on a validated ID.
     *
     * @param scanner Scanner instance to capture target ID
     */
    public void removeStudentById(Scanner scanner)
    {
        System.out.print("\nEnter student ID to remove: ");
        int id = validateId(scanner);

        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();

            // Verify if the deletion affected any rows in the database
            if (rowsAffected > 0)
            {
                System.out.println("\n✅ Student removed successfully.");
            }
            else
            {
                System.out.println("❌ No student found with ID " + id + ".");
            }
        }
        catch (SQLException e)
        {
            System.out.println("❌ Database error: Unable to remove student.");
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing student record with new values for name, age, and grade.
     *
     * @param scanner Scanner instance to capture ID and new field parameters
     */
    public void updateStudentById(Scanner scanner)
    {
        System.out.print("\nEnter student ID to update: ");
        int id = validateId(scanner);

        // Fetch current record to ensure target student exists before soliciting updates
        Student student = searchById(id);
        if (student == null)
        {
            System.out.println("❌ No student found with ID " + id + ".");
            return;
        }

        System.out.println("\nEnter new details for the student:");
        String newName = validateName(scanner);
        int newAge = validateAge(scanner);
        String newGrade = validateGrade(scanner);

        String sql = "UPDATE students SET name = ?, age = ?, grade = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, newName);
            ps.setInt(2, newAge);
            ps.setString(3, newGrade);
            ps.setInt(4, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0)
            {
                System.out.println("✅ Student updated successfully.");
            }
            else
            {
                System.out.println("❌ Failed to update student.");
            }
        }
        catch (SQLException e)
        {
            System.out.println("❌ Database error: Unable to update student.");
            e.printStackTrace();
        }
    }

    // -------------------------------------------------
    // Validation Helper Methods
    // -------------------------------------------------

    /**
     * Captures and validates a Student ID, enforcing positive integer inputs.
     * Recursively prompts user until input is valid.
     *
     * @param scanner Scanner instance to capture input
     * @return int a validated positive integer ID
     */
    public static int validateId(Scanner scanner)
    {
        int id;
        while (true)
        {
            try
            {
                System.out.print("Enter Student ID (positive integer): ");
                id = Integer.parseInt(scanner.nextLine().trim());
                if (id <= 0)
                {
                    System.out.println("🔴 Error: ID must be a positive integer.");
                    continue;
                }
                return id;
            }
            catch (NumberFormatException e)
            {
                System.out.println("🔴 Invalid input. Please enter a valid integer.");
            }
        }
    }

    /**
     * Captures and validates a Student Name, enforcing alphabetical characters and spaces only.
     *
     * @param scanner Scanner instance to capture input
     * @return String a validated student name
     */
    private String validateName(Scanner scanner)
    {
        while (true)
        {
            System.out.print("Enter Name (letters only): ");
            String name = scanner.nextLine().trim();
            // Validate name pattern via regular expression (letters and spaces only)
            if (!name.isEmpty() && Pattern.matches("^[a-zA-Z\\s]+$", name))
            {
                return name;
            }
            System.out.println("🔴 Invalid name. Only letters and spaces are allowed.");
        }
    }

    /**
     * Captures and validates a Student Age, enforcing ranges between 5 and 120.
     *
     * @param scanner Scanner instance to capture input
     * @return int a validated student age
     */
    private int validateAge(Scanner scanner)
    {
        int age;
        while (true)
        {
            try
            {
                System.out.print("Enter Age (5 to 120): ");
                age = Integer.parseInt(scanner.nextLine().trim());
                if (age >= 5 && age <= 120)
                {
                    return age;
                }
                System.out.println("🔴 Age must be between 5 and 120.");
            }
            catch (NumberFormatException e)
            {
                System.out.println("🔴 Invalid input. Please enter a valid integer.");
            }
        }
    }

    /**
     * Captures and validates a Student Grade, enforcing allowed grade options.
     *
     * @param scanner Scanner instance to capture input
     * @return String validated uppercase grade
     */
    private String validateGrade(Scanner scanner)
    {
        while (true)
        {
            System.out.print("Enter Grade (O, E, A, B, C, D, or F): ");
            String grade = scanner.nextLine().trim().toUpperCase();
            // Check grade validity against defined pattern
            if (Pattern.matches("^(O|E|A|B|C|D|F)$", grade))
            {
                return grade;
            }
            System.out.println("🔴 Invalid grade. Allowed formats: O, E, A, B, C, D, or F.");
        }
    }

    // -------------------------------------------------
    // Reporting and Analytics Features
    // -------------------------------------------------

    /**
     * Groups students by grade, aggregates the counts, and displays the distribution report.
     */
    public void generateGradeReport()
    {
        System.out.println("\n📊 Grade Distribution Report: ");
        Map<String, Integer> gradeCount = new HashMap<>();
        String sql = "SELECT grade, COUNT(*) AS count FROM students GROUP BY grade";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                String grade = rs.getString("grade");
                int count = rs.getInt("count");
                gradeCount.put(grade, count);
            }

            // Print the distribution totals or error message if empty
            if (gradeCount.isEmpty())
            {
                System.out.println("❌ No students found.");
            }
            else
            {
                gradeCount.forEach((grade, count) -> System.out.println(grade + ": " + count));
            }
        }
        catch (SQLException e)
        {
            System.out.println("❌ Database error: Unable to generate grade report.");
            e.printStackTrace();
        }
    }

    /**
     * Queries and displays all student records within user-specified minimum and maximum age bounds.
     *
     * @param scanner Scanner instance to capture bounds parameters
     */
    public void generateAgeRangeReport(Scanner scanner)
    {
        System.out.println("\nEnter minimum age: ");
        int minAge = validateAge(scanner);

        System.out.println("\nEnter maximum age: ");
        int maxAge = validateAge(scanner);

        // Ensure boundary consistency (minimum age cannot exceed maximum age)
        while (minAge > maxAge)
        {
            System.out.println("\n⚠️ Please enter a valid age range.");
            System.out.println("\nEnter minimum age: ");
            minAge = validateAge(scanner);
            System.out.println("\nEnter maximum age: ");
            maxAge = validateAge(scanner);
        }

        System.out.println("\n📊 Age Range Report (" + minAge + " to " + maxAge + ")");

        String sql = "SELECT id, name, age, grade FROM students WHERE age BETWEEN ? AND ?";
        List<Student> ageFiltered = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setInt(1, minAge);
            ps.setInt(2, maxAge);

            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                ageFiltered.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("grade")));
            }
            displayStudents(ageFiltered);
        }
        catch (SQLException e)
        {
            System.out.println("❌ Database error: Unable to generate age range report.");
            e.printStackTrace();
        }
    }

    /**
     * Computes and prints core student body aggregate statistics (student count, average age, grade distribution).
     */
    public void generateSummaryStatisticsReport()
    {
        System.out.println("\n📊 Summary Statistics:");
        System.out.println("--------------------------------------------");

        String studentCountSql = "SELECT COUNT(*) AS total FROM students";
        String avgAgeSql = "SELECT AVG(age) AS avg_age FROM students";

        try (Connection conn = DBConnection.getConnection())
        {
            // Step 1: Calculate total count of enrolled students
            int totalStudents = 0;
            try (PreparedStatement ps = conn.prepareStatement(studentCountSql);
                    ResultSet rs = ps.executeQuery())
            {
                if (rs.next())
                {
                    totalStudents = rs.getInt("total");
                }
            }
            if (totalStudents == 0)
            {
                System.out.println("\n❌ No students available to generate statistics.");
                return;
            }
            System.out.println("Total Students: " + totalStudents);

            // Step 2: Compute average student age
            double averageAge = 0.0;
            try (PreparedStatement ps = conn.prepareStatement(avgAgeSql);
                    ResultSet rs = ps.executeQuery())
            {
                if (rs.next())
                {
                    averageAge = rs.getDouble("avg_age");
                }
            }
            System.out.printf("Average Age: %.2f%n", averageAge);

            // Step 3: Print grade breakdown by delegating to the grade report method
            System.out.println("\nGrade Distribution:");
            generateGradeReport();
        }
        catch (SQLException e)
        {
            System.out.println("❌ Database error: Unable to generate summary statistics.");
            e.printStackTrace();
        }
    }

    /**
     * Queries and displays top performing students according to a prioritized weight map of grades (up to top 10).
     */
    public void generateTopPerformersReport()
    {
        // SQL query sorting student records descending by explicit grade weight definition
        String sql = """
                SELECT id, name, age, grade
                FROM students
                ORDER BY
                    CASE grade
                        WHEN 'O' THEN 7
                        WHEN 'E' THEN 6
                        WHEN 'A' THEN 5
                        WHEN 'B' THEN 4
                        WHEN 'C' THEN 3
                        WHEN 'D' THEN 2
                        WHEN 'F' THEN 1
                        ELSE 0
                    END DESC
                LIMIT 10;
                """;

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery())
        {
            List<Student> topPerformers = new ArrayList<>();
            while (rs.next())
            {
                topPerformers.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("grade")));
            }
            System.out.println("\n🎖️ Top Performers:");
            displayStudents(topPerformers);
        }
        catch (SQLException e)
        {
            System.out.println("\n❌ Error fetching top performers: " + e.getMessage());
        }
    }
}
