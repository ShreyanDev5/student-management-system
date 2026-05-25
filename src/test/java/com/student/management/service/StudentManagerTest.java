package com.student.management.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StudentManagerTest {

    @Test
    void sampleTest() {
        StudentManager studentManager = new StudentManager();
        assertNotNull(studentManager);
    }
}

