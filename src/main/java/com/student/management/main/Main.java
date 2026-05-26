package com.student.management.main;

import com.student.management.model.Student;
import com.student.management.service.StudentManager;

import java.util.Scanner;

/**
 * Console entry point for the application.
 * Main only handles the menu and user interaction, then delegates the real work
 * to StudentManager, which talks to the database layer.
 */
public class Main
{

    public static void main(String[] args)
    {
        // Create scanner instance for user input and instantiate the student manager
        Scanner scanner = new Scanner(System.in);
        StudentManager studentManager = new StudentManager();

        // Display application header (runs once on startup)
        showHeader();

        // Main loop: keep showing the menu until the user chooses to exit.
        while (true)
        {
            // Display menu options for user
            displayMenu();

            // Read user input and trim any extra spaces
            String input = scanner.nextLine().trim();

            try
            {
                int choice = Integer.parseInt(input);

                // Check if user wants to exit (option 0)
                if (choice == 0)
                {
                    exitApplication(scanner);
                    return;
                }

                // Main stays small by forwarding each action to StudentManager.
                handleChoice(choice, scanner, studentManager);
            }
            catch (NumberFormatException e)
            {
                // If input is not a valid number, notify user and prompt again
                System.out.println("\n🔴 Invalid input. Please enter a number between 0 and 11.");
            }
        }
    }

    // ======================== Display Methods ========================

    /**
     * Displays the application header.
     */
    private static void showHeader()
    {
        System.out.println("\n");
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║             📱 STUDENT MANAGER            ║");
        System.out.println("╚═══════════════════════════════════════════╝");
    }

    /**
     * Displays the menu options to the user.
     */
    private static void displayMenu()
    {
        System.out.println("\n═════════════════════════════════════════════");
        System.out.println("\n⚠️  0. Exit Application");

        // Student management section
        System.out.println("\n⚙️ STUDENT OPERATIONS:");
        System.out.println("  1. ➕ Add New Student");
        System.out.println("  2. 📃 View All Students");
        System.out.println("  3. 🔍 Search Student by ID");
        System.out.println("  4. 🔍 Search Student by Name");
        System.out.println("  5. 🔍 Search Student by Grade");
        System.out.println("  6. ❌ Remove Student by ID");
        System.out.println("  7. ✏️  Update Student Information");

        // Reporting section
        System.out.println("\n📊 REPORTING FEATURES:");
        System.out.println("  8. 📈 Generate Grade Distribution Report");
        System.out.println("  9. 👥 Generate Age-Range Analysis");
        System.out.println("  10. 🔢 Generate Summary Statistics");
        System.out.println("  11. 🏆 View Top Performers");

        System.out.println("\n═════════════════════════════════════════════");
        System.out.print("Select an option (0-11): ");
    }

    // ======================== Option Handling ========================

    /**
     * Dispatches the user-selected option to the appropriate method.
     *
     * @param choice  The user-selected menu option.
     * @param scanner Scanner object for reading user input.
     * @param manager StudentManager instance handling student operations.
     */
    private static void handleChoice(int choice, Scanner scanner, StudentManager manager)
    {
        switch (choice)
        {
            case 1:
                addStudent(scanner, manager);
                break;
            case 2:
                manager.viewAllStudents();
                break;
            case 3:
                searchStudentById(scanner, manager);
                break;
            case 4:
                manager.searchByName(scanner);
                break;
            case 5:
                manager.searchByGrade(scanner);
                break;
            case 6:
                manager.removeStudentById(scanner);
                break;
            case 7:
                manager.updateStudentById(scanner);
                break;
            case 8:
                manager.generateGradeReport();
                break;
            case 9:
                manager.generateAgeRangeReport(scanner);
                break;
            case 10:
                manager.generateSummaryStatisticsReport();
                break;
            case 11:
                manager.generateTopPerformersReport();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    // ======================== Student Operations ========================

    /**
     * Adds a new student by delegating input collection to the StudentManager.
     *
     * @param scanner Scanner object for user input.
     * @param manager StudentManager instance to add the student.
     */
    private static void addStudent(Scanner scanner, StudentManager manager)
    {
        manager.addStudentFromInput(scanner);
    }

    /**
     * Searches for a student by their unique ID and displays the result.
     *
     * @param scanner Scanner object for user input.
     * @param manager StudentManager instance used to search the student.
     */
    private static void searchStudentById(Scanner scanner, StudentManager manager)
    {
        System.out.print("Enter the ID of the student to search: ");
        int id = StudentManager.validateId(scanner);

        Student student = manager.searchById(id);
        if (student != null)
        {
            // Display found student details
            System.out.println(student);
        }
        else
        {
            // Notify if no student is found with the provided ID
            System.out.println("\n❌ No student found with ID: " + id);
        }
    }

    // ======================== Utility Methods ========================

    /**
     * Closes resources and gracefully exits the application.
     *
     * @param scanner Scanner object to be closed.
     */
    private static void exitApplication(Scanner scanner)
    {
        scanner.close();
        System.out.println("\nExiting the system. Goodbye 👋");
    }
}
