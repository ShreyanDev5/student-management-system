package com.student.management.model;

/**
 * Simple model object for one student record.
 * StudentManager creates these objects from database query results and then
 * uses
 * them when printing tables or detailed student information.
 */
public class Student {
    // Instance variables to hold student details
    private int id;
    private String name;
    private int age;
    private String grade;

    /**
     * Constructor to initialize a new Student object.
     *
     * @param id    Unique student ID
     * @param name  Student's name
     * @param age   Student's age
     * @param grade Student's grade
     */
    public Student(int id, String name, int age, String grade) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    // ===== Getters: Retrieve student information =====

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGrade() {
        return grade;
    }

    // ===== Setters: Update student information =====

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     * Generates a formatted string displaying the student's details in a box
     * layout.
     *
     * @return Formatted student information
     */
    @Override
    public String toString() {
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
