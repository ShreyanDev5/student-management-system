-- ====================================================================
-- Database Schema for Student Management System
-- ====================================================================
-- This script initializes the database and creates the students table.
-- ====================================================================

-- Create the database if it does not already exist
CREATE DATABASE IF NOT EXISTS students_db;
USE students_db;

-- Create the students table
CREATE TABLE IF NOT EXISTS students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    grade VARCHAR(5) NOT NULL
);
