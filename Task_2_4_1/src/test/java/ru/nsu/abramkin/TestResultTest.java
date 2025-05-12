package ru.nsu.abramkin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for TestResult class.
 * Verifies the functionality of test result tracking and calculations.
 */
public class TestResultTest {
    
    /**
     * Tests the default constructor initialization.
     * Verifies that a new TestResult has zero values.
     */
    @Test
    public void testDefaultConstructor() {
        TestResult result = new TestResult();
        
        assertEquals(0, result.getPassedTests());
        assertEquals(0, result.getFailedTests());
        assertEquals(0, result.getTotalTests());
    }
    
    /**
     * Tests setting and getting passed tests.
     * Verifies that passed tests count can be changed and retrieved correctly.
     */
    @Test
    public void testSetAndGetPassedTests() {
        TestResult result = new TestResult();
        result.setPassedTests(3);
        assertEquals(3, result.getPassedTests());
    }
    
    /**
     * Tests setting and getting failed tests.
     * Verifies that failed tests count can be changed and retrieved correctly.
     */
    @Test
    public void testSetAndGetFailedTests() {
        TestResult result = new TestResult();
        result.setFailedTests(2);
        assertEquals(2, result.getFailedTests());
    }
    
    /**
     * Tests setting and getting total tests.
     * Verifies that total tests count can be changed and retrieved correctly.
     */
    @Test
    public void testSetAndGetTotalTests() {
        TestResult result = new TestResult();
        result.setTotalTests(5);
        assertEquals(5, result.getTotalTests());
    }
    
    /**
     * Tests the relationship between passed, failed, and total tests.
     * Verifies that total tests equals the sum of passed and failed tests.
     */
    @Test
    public void testTestCountsRelationship() {
        TestResult result = new TestResult();
        result.setPassedTests(3);
        result.setFailedTests(2);
        result.setTotalTests(5);
        
        assertEquals(5, result.getTotalTests());
        assertEquals(3, result.getPassedTests());
        assertEquals(2, result.getFailedTests());
        assertEquals(result.getPassedTests() + result.getFailedTests(), result.getTotalTests());
    }
} 