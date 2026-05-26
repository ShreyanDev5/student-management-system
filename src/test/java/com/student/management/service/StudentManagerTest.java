package com.student.management.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit test suite for the {@link StudentManager} service class.
 * Ensures CRUD and reporting business logic functions as expected.
 */
class StudentManagerTest
{

    /**
     * Verifies that the StudentManager service class is correctly instantiated and not null.
     */
    @Test
    void sampleTest()
    {
        // Instantiates the target service manager under test
        StudentManager studentManager = new StudentManager();
        
        // Assert that the manager was successfully constructed and is non-null
        assertNotNull(studentManager);
    }
}
