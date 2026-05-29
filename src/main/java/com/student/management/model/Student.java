package com.student.management.model;

/**
 * Represents a Student in our school system. 
 * 
 * Think of this class like a digital "student card" or folder. It holds all the basic 
 * information about one single student (their unique ID number, name, age, and grade).
 * 
 * For beginners: This is a "blueprint" (class) that lets us create real "objects" 
 * (individual students) loaded from our database so we can display them on the screen.
 */
public class Student
{
    // The student's unique ID number (like a roll number). The database automatically assigns this.
    private int id;
    
    // The full name of the student.
    private String name;
    
    // How old the student is. Valid ages must be between 5 and 120 years.
    private int age;
    
    // The grade the student earned (can be O, E, A, B, C, D, or F).
    private String grade;

    /**
     * Creates a new Student object with all their details.
     * 
     * For beginners: This is a "Constructor". It is like a cookie cutter that takes the ingredients 
     * (ID, name, age, grade) and creates a brand-new, unchangeable Student "cookie" (object).
     *
     * @param id    The student's unique roll number from the database
     * @param name  The student's full name
     * @param age   How old the student is
     * @param grade The grade the student received
     */
    public Student(int id, String name, int age, String grade)
    {
        this.id = id;
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    // ===== Getters (Retrieve properties) =====
    // For beginners: Getters are helper methods that let other parts of our program 
    // safely "peek" at or read a student's private details without changing them.

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public int getAge()
    {
        return age;
    }

    public String getGrade()
    {
        return grade;
    }


    /**
     * Converts the student's details into a beautiful, styled visual text box.
     * 
     * For beginners: Instead of printing messy text, this overrides the default toString() 
     * method to draw a pretty box (using special box-drawing characters) containing the student's information.
     *
     * @return A pretty, boxed string representing the student's card
     */
    @Override
    public String toString()
    {
        return String.format(
                """
                        ┌─────────────────────────────┐
                        │    📚 Student Information   │
                        ├─────────────────────────────┤
                        │ ID    : %-20d │
                        │ Name  : %-20s │
                        │ Age   : %-20d │
                        │ Grade : %-20s │
                        └─────────────────────────────┘
                        """, id, name, age, grade);
    }
}
