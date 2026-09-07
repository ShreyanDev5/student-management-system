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
                System.out.println("\nInvalid option. Enter a number (0-11).");
            }
        }
    }

    // ======================== Console Display Methods ========================

    /**
     * Prints a sleek header banner at the start of the program.
     */
    private static void showHeader()
    {
        System.out.println("+--------------------------------------------------------+");
        System.out.println("|               STUDENT MANAGEMENT SYSTEM                |");
        System.out.println("+--------------------------------------------------------+");
    }

    /**
     * Prints the two-column options menu for student operations and reporting features.
     */
    private static void displayMenu()
    {
        System.out.println();
        System.out.println("  [ Records ]                       [ Reports ]");
        System.out.println("   1. Add Student                    8. Grade Distribution");
        System.out.println("   2. View All Students              9. Age Analysis");
        System.out.println("   3. Search by ID                  10. Summary Statistics");
        System.out.println("   4. Search by Name                11. Top Performers");
        System.out.println("   5. Search by Grade");
        System.out.println("   6. Delete Student                 0. Exit");
        System.out.println("   7. Update Student");
        System.out.println();
        System.out.println("----------------------------------------------------------");
        System.out.print("Select option [0-11]: ");
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
                System.out.println("Invalid option. Enter a number (0-11).");
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
        int id = StudentManager.validateId(scanner, "\nEnter student ID: ");

        Student student = manager.searchById(id);
        if (student != null)
        {
            System.out.println(student);
        }
        else
        {
            System.out.println("No student found with ID: " + id);
        }
    }

    // ======================== Lifecycle and Cleanup ========================

    /**
     * Safely closes the Scanner resource and prints an exit message.
     *
     * @param scanner The Scanner tool to close
     */
    private static void exitApplication(Scanner scanner)
    {
        scanner.close();
        System.out.println("\nApplication exited.");
    }
}
