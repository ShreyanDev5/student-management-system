package com.student.management.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

/**
 * Utility class providing centralized database connectivity.
 * Loads JDBC connection properties from 'database.properties' once on class initialization
 * and supplies active database connection instances to the application.
 */
public class DBConnection
{
    // Caches loaded database configuration properties (URL, username, password)
    private static final Properties props = new Properties();

    // Statically loads database credentials from resource properties on class load
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
     * Creates and returns a new connection to the configured database.
     *
     * @return Connection active JDBC connection to the target database
     * @throws SQLException if connection properties are missing or database connection fails
     */
    public static Connection getConnection() throws SQLException
    {
        String url = props.getProperty("db.url");
        String username = props.getProperty("db.username");
        String password = props.getProperty("db.password");

        // Ensure database credentials and URL are present in config properties
        if (url == null || username == null || password == null)
        {
            throw new SQLException("Database credentials are missing in the properties file.");
        }

        return DriverManager.getConnection(url, username, password);
    }
}
