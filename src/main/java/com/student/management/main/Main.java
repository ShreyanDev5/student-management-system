package com.student.management.main;

import com.student.management.model.Student;
import com.student.management.service.StudentManager;

import java.util.Scanner;

/**
 * The entry point and control center of our program.
 * 
 * Think of this class like a friendly receptionist or a TV menu:
 * 1. It welcomes the user with a nice banner.
 * 2. It continuously displays a list of options (like adding a student or viewing reports).
 * 3. It waits for the user to type their choice, converts it to a number, and delegates 
 *    the actual work to the StudentManager service layer.
 * 
 * For beginners: This class contains the special "main" method, which is where Java starts 
 * running our application. It uses a "while(true)" loop to keep the menu active so the program 
 * doesn't close after doing just one thing.
 */
public class Main
{

    public static void main(String[] args)
    {
        // A Scanner reads what the user types. StudentManager is the brain that does the actual work.
        Scanner scanner = new Scanner(System.in);
        StudentManager studentManager = new StudentManager();

        // Draws the beautiful welcome title card on the screen.
        showHeader();

        // An infinite loop! This keeps the menu running over and over until the user chooses to exit (0).
        while (true)
        {
            // Show the numbered menu of student operations and reports.
            displayMenu();

            // Read what the user typed and remove any accidental leading or trailing spaces.
            String input = scanner.nextLine().trim();

            try
            {
                // Convert the typed text (String) into a whole number (int).
                int choice = Integer.parseInt(input);

                // If the user selects 0, close the program nicely.
                if (choice == 0)
                {
                    exitApplication(scanner);
                    return; // Exits the main method, stopping the program!
                }

                // Route the number chosen by the user to the correct operation.
                handleChoice(choice, scanner, studentManager);
            }
            catch (NumberFormatException e)
            {
                // If the user typed letters instead of a number, print an error instead of crashing!
                System.out.println("\n🔴 Invalid input. Please enter a number between 0 and 11.");
            }
        }
    }

    // ======================== Console Display Methods ========================

    /**
     * Prints a beautiful, decorated welcome banner at the very start of the program.
     */
    private static void showHeader()
    {
        System.out.println("\n");
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║             📱 STUDENT MANAGER            ║");
        System.out.println("╚═══════════════════════════════════════════╝");
    }

    /**
     * Prints the numbered options list (0 to 11) for student operations and reporting features.
     */
    private static void displayMenu()
    {
        System.out.println("\n═════════════════════════════════════════════");
        System.out.println("\n⚠️ 0. Exit Application");

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
     * Acts as a switcher/router. It takes the number chosen by the user and calls the correct 
     * action in our StudentManager brain.
     *
     * @param choice  The menu option number chosen by the user (1 to 11)
     * @param scanner The Scanner object to read subsequent inputs inside the actions
     * @param manager Our StudentManager service instance
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
     * Tells the student manager to start the steps for adding a new student.
     *
     * @param scanner Scanner to read input
     * @param manager Service instance
     */
    private static void addStudent(Scanner scanner, StudentManager manager)
    {
        manager.addStudentFromInput(scanner);
    }

    /**
     * Asks the user for an ID, searches for that student, and prints their details if found.
     *
     * @param scanner Scanner to read the ID
     * @param manager Service instance to search the database
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
     * Safely closes the Scanner resource and prints a warm farewell message.
     * 
     * For beginners: It is always good practice to close resources like Scanners or database 
     * connections when we are done using them to keep our computer's memory clean and happy.
     *
     * @param scanner The Scanner tool to close
     */
    private static void exitApplication(Scanner scanner)
    {
        scanner.close();
        System.out.println("\nExiting the system. Goodbye 👋");
    }
}
