package com.student.management.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

/**
 * Helps our program talk to the database (where all student records are saved).
 * 
 * Think of this class like a telephone operator. When our program wants to save or look up 
 * a student, it asks DBConnection to "connect the call" to the database.
 * 
 * For beginners: This class reads the database login credentials (like the address, username, 
 * and password) from a configuration file called 'database.properties' once when the program 
 * starts, and then creates secure pathways (Connections) to talk to the database.
 */
public class DBConnection
{
    // A dictionary (Properties) that holds the database URL, username, and password.
    private static final Properties props = new Properties();

    // A "static block" that runs automatically exactly once when this class is first loaded.
    // It reads 'database.properties' and loads the login info so we don't have to do it repeatedly.
    static
    {
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("database.properties"))
        {
            if (input != null)
            {
                props.load(input);
            }
            else
            {
                throw new RuntimeException("Failed to load database.properties file.");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException("Error loading database properties.", e);
        }
    }

    /**
     * Establishes and returns a new, active connection to the database.
     * 
     * Think of this like opening a secure pipeline or a door to the database so we can send SQL commands.
     *
     * @return An active Connection object to run queries
     * @throws SQLException If database login details are missing or the database server is offline
     */
    public static Connection getConnection() throws SQLException
    {
        String url = props.getProperty("db.url");
        String username = props.getProperty("db.username");
        String password = props.getProperty("db.password");

        // Make sure we have a valid database URL and credentials in the properties file.
        if (url == null || username == null || password == null)
        {
            throw new SQLException("Database credentials are missing in the properties file.");
        }

        return DriverManager.getConnection(url, username, password);
    }
}
