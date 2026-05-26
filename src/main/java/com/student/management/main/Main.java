package com.student.management.main;

import com.student.management.model.Student;
import com.student.management.service.StudentManager;

import java.util.Scanner;

/**
 * Console entry point and user interaction controller for the application.
 * Manages the CLI menu loop, accepts and processes user inputs, and delegates
 * functional operations and database transactions to the {@link StudentManager} service.
 */
public class Main
{

    public static void main(String[] args)
    {
        // Setup console resources and the service manager instance
        Scanner scanner = new Scanner(System.in);
        StudentManager studentManager = new StudentManager();

        // Print the welcome header splash once on application launch
        showHeader();

        // Continuous application run loop: displays the menu and handles user commands
        while (true)
        {
            // Print available menu choices
            displayMenu();

            // Read the raw user selection and strip whitespace
            String input = scanner.nextLine().trim();

            try
            {
                int choice = Integer.parseInt(input);

                // Gracefully shut down and exit if user enters 0
                if (choice == 0)
                {
                    exitApplication(scanner);
                    return;
                }

                // Dispatch the valid choice to the handler method to delegate execution
                handleChoice(choice, scanner, studentManager);
            }
            catch (NumberFormatException e)
            {
                // Handle non-numeric console input elegantly without crashing the loop
                System.out.println("\n🔴 Invalid input. Please enter a number between 0 and 11.");
            }
        }
    }

    // ======================== Console Display Methods ========================

    /**
     * Displays the decorative application welcome header.
     */
    private static void showHeader()
    {
        System.out.println("\n");
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║             📱 STUDENT MANAGER            ║");
        System.out.println("╚═══════════════════════════════════════════╝");
    }

    /**
     * Displays the interactive main menu options to the console.
     */
    private static void displayMenu()
    {
        System.out.println("\n═════════════════════════════════════════════");
        System.out.println("\n⚠️  0. Exit Application");

        // Operations section
        System.out.println("\n⚙️ STUDENT OPERATIONS:");
        System.out.println("  1. ➕ Add New Student");
        System.out.println("  2. 📃 View All Students");
        System.out.println("  3. 🔍 Search Student by ID");
        System.out.println("  4. 🔍 Search Student by Name");
        System.out.println("  5. 🔍 Search Student by Grade");
        System.out.println("  6. ❌ Remove Student by ID");
        System.out.println("  7. ✏️  Update Student Information");

        // Analytics and reports section
        System.out.println("\n📊 REPORTING FEATURES:");
        System.out.println("  8. 📈 Generate Grade Distribution Report");
        System.out.println("  9. 👥 Generate Age-Range Analysis");
        System.out.println("  10. 🔢 Generate Summary Statistics");
        System.out.println("  11. 🏆 View Top Performers");

        System.out.println("\n═════════════════════════════════════════════");
        System.out.print("Select an option (0-11): ");
    }

    // ======================== Menu Dispatcher ========================

    /**
     * Routes the user's numeric choice to corresponding methods or service calls.
     *
     * @param choice  The verified numeric command chosen by the user
     * @param scanner Scanner for scanning dynamic parameters inside helper methods
     * @param manager Service layer instance to execute business commands
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

    // ======================== Student Actions ========================

    /**
     * Initiates student creation by delegating to the StudentManager service class.
     *
     * @param scanner Scanner for reading student properties
     * @param manager Service instance handling database inserts
     */
    private static void addStudent(Scanner scanner, StudentManager manager)
    {
        manager.addStudentFromInput(scanner);
    }

    /**
     * Solicits a Student ID from user input and queries the database via the service layer.
     *
     * @param scanner Scanner for user ID entry
     * @param manager Service instance handling query lookups
     */
    private static void searchStudentById(Scanner scanner, StudentManager manager)
    {
        System.out.print("Enter the ID of the student to search: ");
        int id = StudentManager.validateId(scanner);

        Student student = manager.searchById(id);
        if (student != null)
        {
            // Print the styled box layout of the found student card
            System.out.println(student);
        }
        else
        {
            // Notify the user if no record matches the given ID
            System.out.println("\n❌ No student found with ID: " + id);
        }
    }

    // ======================== Lifecycle and Cleanup ========================

    /**
     * Closes the terminal Scanner resource and prints a goodbye message before exiting.
     *
     * @param scanner The Scanner reference to close
     */
    private static void exitApplication(Scanner scanner)
    {
        scanner.close();
        System.out.println("\nExiting the system. Goodbye 👋");
    }
}
