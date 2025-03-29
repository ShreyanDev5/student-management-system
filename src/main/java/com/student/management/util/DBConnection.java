package com.student.management.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class DBConnection
{
    // Properties to hold database configurations
    private static final Properties props = new Properties();

    // Static block to load properties from the file once when the class is loaded
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
     * Establishes and returns a connection to the database.
     *
     * @return Connection object
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException
    {
        String url = props.getProperty("db.url");
        String username = props.getProperty("db.username");
        String password = props.getProperty("db.password");

        // Validate properties
        if (url == null || username == null || password == null)
        {
            throw new SQLException("Database credentials are missing in the properties file.");
        }

        return DriverManager.getConnection(url, username, password);
    }
}
