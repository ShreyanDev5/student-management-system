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
 * The "Brain" or "Manager" of our application. It handles all business logic, data validation, 
 * and directly communicates with the database using SQL commands.
 * 
 * Think of this class like a helpful school office manager:
 * 1. It asks the user for information (and checks if they typed it correctly, like checking if age is a valid number).
 * 2. It writes, reads, updates, and deletes records in our database filing cabinet (CRUD operations).
 * 3. It generates beautiful reports (like showing who the top students are or showing age statistics).
 * 
 * For beginners: This class sits between our main menu screen (Main.java) and the database (DBConnection). 
 * It does all the work of turning database rows into Java Student objects.
 */
public class StudentManager
{
    // -------------------------------------------------
    // Console Display Methods
    // -------------------------------------------------

    /**
     * Takes a list of student objects and draws them as a clean, easy-to-read table on the screen.
     * 
     * If there are no students in the list, it lets the user know with a message.
     *
     * @param students The list of Student objects to print
     */
    public void displayStudents(List<Student> students)
    {
        System.out.println("\n");

        // Print the header row of our table
        System.out.println("--------------------------------------------");
        System.out.printf("%-8s | %-18s | %-4s | %-5s%n", "ID", "Name", "Age", "Grade");
        System.out.println("--------------------------------------------");

        // Loop through each student in our list and print their details as a row in our table
        for (Student student : students)
        {
            System.out.printf("%-8d | %-18s | %-4d | %-5s%n",
                    student.getId(),
                    student.getName(),
                    student.getAge(),
                    student.getGrade());
        }
        System.out.println("--------------------------------------------");

        // If the student list is empty, print a friendly warning message
        if (students.isEmpty())
        {
            System.out.println("\n❌ No student records found.");
        }
    }

    // -------------------------------------------------
    // CRUD Operations (Create, Read, Update, Delete)
    // -------------------------------------------------

    /**
     * Asks the user to type in a new student's details, validates the input, and saves them to the database.
     * 
     * For beginners: 
     * 1. It calls helper methods to make sure the name has only letters, the age is valid, etc.
     * 2. It runs an "INSERT INTO" SQL query to save the student.
     * 3. We use a "try-with-resources" block (the try (...) part) which automatically closes our 
     *    database connection when we are done so we don't leak memory.
     *
     * @param scanner The tool we use to read what the user types in the console
     */
    public void addStudentFromInput(Scanner scanner)
    {
        System.out.println("\nPlease enter the student details:");

        // Ask the user for name, age, and grade, and make sure their input is completely valid!
        String name = validateName(scanner);
        int age = validateAge(scanner);
        String grade = validateGrade(scanner);

        String query = "INSERT INTO students (name, age, grade) VALUES (?, ?, ?)";

        // Try to connect to the database and insert the new student record using placeholders (?) to prevent SQL injection.
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
            e.printStackTrace();
        }
    }

    /**
     * Fetches every single student record from the database and prints them in a table.
     * 
     * For beginners: It runs a "SELECT * FROM students" SQL query, loops through each result, 
     * creates a new Student object for each row, and then displays the complete list.
     */
    public void viewAllStudents()
    {
        String sql = "SELECT * FROM students";
        List<Student> students = new ArrayList<>();

        // Fetch records and automatically close the connection and statement objects when done.
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery())
        {
            // Loop through the database query results one by one and turn them into Student objects
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
     * Searches for a student by their unique ID number.
     *
     * @param id The unique roll number we want to find
     * @return The Student object if we find them, or null if no student has that ID
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
     * Searches for students whose names contain the text the user types.
     * 
     * For beginners: It uses the SQL "LIKE" operator with wildcards (e.g. "%John%") to find partial matches, 
     * so searching for "ann" will find "Anna", "Danny", and "Julianne".
     *
     * @param scanner The tool to read the name search query
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
            // Search using wildcards (%) so we find names containing the input anywhere
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
     * Searches the database for all students who achieved a specific grade (like 'A' or 'O').
     *
     * @param scanner The tool to read the grade input
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
     * Removes a student permanently from the database using their unique ID.
     * 
     * For beginners: It runs a "DELETE FROM students WHERE id = ?" SQL query. If the number of 
     * affected rows is more than 0, it means the student was successfully found and removed.
     *
     * @param scanner The tool to read the ID of the student to remove
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

            // Verify if the deletion affected any rows in the database (meaning the ID existed)
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
     * Changes the name, age, or grade of an existing student in the database.
     * 
     * For beginners: 
     * 1. It first checks if the student exists by searching for their ID.
     * 2. If they exist, it asks the user for the new information and validates it.
     * 3. It runs an "UPDATE students SET..." SQL query to modify their record in the database.
     *
     * @param scanner The tool to read the ID and new student information
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
     * Asks the user to enter a student ID and makes sure it is a valid positive number.
     * 
     * For beginners: This method uses an infinite loop (while(true)) that keeps running until the 
     * user types a correct positive number. If they type letters or negative numbers, it catches 
     * the error (NumberFormatException) and asks them to try again.
     *
     * @param scanner The tool to read the ID
     * @return A valid positive integer student ID
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
     * Asks the user to enter a name and ensures it contains only letters and spaces.
     * 
     * For beginners: It uses a regular expression (regex) pattern "^[a-zA-Z\\s]+$" to check that 
     * the name only contains uppercase/lowercase letters and spaces. Numbers or symbols are not allowed!
     *
     * @param scanner The tool to read the name
     * @return A clean, validated name string
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
     * Asks the user to enter an age and ensures it is a number between 5 and 120.
     * 
     * For beginners: This keeps the data high-quality, because a student can't be 0 years old or 999 years old!
     *
     * @param scanner The tool to read the age
     * @return A validated age between 5 and 120
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
     * Asks the user to enter a grade and ensures it is one of the allowed options: O, E, A, B, C, D, or F.
     * 
     * For beginners: It automatically converts the input to uppercase (like "a" becomes "A") and 
     * matches it against the allowed list of grades.
     *
     * @param scanner The tool to read the grade
     * @return A validated grade string in uppercase
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
     * Counts how many students received each grade and prints a handy breakdown report.
     * 
     * For beginners: It runs a group-by SQL query: "SELECT grade, COUNT(*) AS count ... GROUP BY grade".
     * This is like sorting all students into piles based on their grades, counting each pile, and 
     * storing the results in a Map (which works like a dictionary) before printing them.
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
     * Generates a report showing all students who are between a minimum and maximum age.
     * 
     * For beginners: 
     * 1. It asks for a minimum age and a maximum age.
     * 2. It ensures the minimum age is not larger than the maximum age (it will ask again if it is).
     * 3. It queries the database using "SELECT ... WHERE age BETWEEN ? AND ?" and prints the matching list.
     *
     * @param scanner The tool to read the min and max ages
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
     * Calculates and prints the total number of students, their average age, and the grade distribution.
     * 
     * For beginners: This combines multiple database calculations. It first finds the total student count, 
     * then computes the mathematical average age (using SQL's AVG function), and finally prints the grade breakdown.
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
     * Finds and displays the top 10 students with the best grades.
     * 
     * For beginners: Since grades are letters, we can't just sort them alphabetically (e.g. 'O' might be 
     * better than 'A'). This method uses a SQL "CASE WHEN" statement to assign a numeric weight to each grade 
     * (O = 7, E = 6, A = 5, etc.), sorts the students from highest weight to lowest, and limits the list to 10.
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
