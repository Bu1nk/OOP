package ru.nsu.abramkin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for TaskResult class.
 * Verifies the functionality of task result status, points, and documentation checks.
 */
public class TaskResultTest {
    
    /**
     * Tests the default constructor initialization.
     * Verifies that a new TaskResult has correct default values.
     */
    @Test
    public void testDefaultConstructor() {
        TaskResult result = new TaskResult();
        
        assertEquals(TaskStatus.NOT_STARTED, result.getStatus());
        assertEquals(0, result.getPoints());
        assertEquals("-", result.getJavadocStatus());
        assertEquals("-", result.getCheckstyleStatus());
        assertNotNull(result.getTestResult());
    }
    
    /**
     * Tests setting and getting task status.
     * Verifies that status can be changed and retrieved correctly.
     */
    @Test
    public void testSetAndGetStatus() {
        TaskResult result = new TaskResult();
        result.setStatus(TaskStatus.COMPLETED);
        assertEquals(TaskStatus.COMPLETED, result.getStatus());
    }
    
    /**
     * Tests setting and getting points.
     * Verifies that points can be changed and retrieved correctly.
     */
    @Test
    public void testSetAndGetPoints() {
        TaskResult result = new TaskResult();
        result.setPoints(5);
        assertEquals(5, result.getPoints());
    }
    
    /**
     * Tests setting and getting Javadoc status.
     * Verifies that Javadoc status can be changed and retrieved correctly.
     */
    @Test
    public void testSetAndGetJavadocStatus() {
        TaskResult result = new TaskResult();
        result.setJavadocStatus("+");
        assertEquals("+", result.getJavadocStatus());
    }
    
    /**
     * Tests setting and getting Checkstyle status.
     * Verifies that Checkstyle status can be changed and retrieved correctly.
     */
    @Test
    public void testSetAndGetCheckstyleStatus() {
        TaskResult result = new TaskResult();
        result.setCheckstyleStatus("+");
        assertEquals("+", result.getCheckstyleStatus());
    }
    
    /**
     * Tests setting and getting test result.
     * Verifies that test result can be changed and retrieved correctly.
     */
    @Test
    public void testSetAndGetTestResult() {
        TaskResult result = new TaskResult();
        TestResult testResult = new TestResult();
        testResult.setPassedTests(5);
        testResult.setTotalTests(5);
        result.setTestResult(testResult);
        
        assertEquals(5, result.getTestResult().getPassedTests());
        assertEquals(5, result.getTestResult().getTotalTests());
    }
} 