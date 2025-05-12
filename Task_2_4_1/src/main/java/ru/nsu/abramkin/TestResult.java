package ru.nsu.abramkin;

/**
 * Represents the results of test execution for a task.
 * Contains information about passed, failed, and total number of tests.
 */
public class TestResult {
    private int passedTests;
    private int failedTests;
    private int totalTests;

    /**
     * Creates a new TestResult with zero values.
     */
    public TestResult() {
        this.passedTests = 0;
        this.failedTests = 0;
        this.totalTests = 0;
    }

    /**
     * Gets the number of passed tests.
     * @return number of passed tests
     */
    public int getPassedTests() {
        return passedTests;
    }

    /**
     * Sets the number of passed tests.
     * @param passedTests number of passed tests
     */
    public void setPassedTests(int passedTests) {
        this.passedTests = passedTests;
    }

    /**
     * Gets the number of failed tests.
     * @return number of failed tests
     */
    public int getFailedTests() {
        return failedTests;
    }

    /**
     * Sets the number of failed tests.
     * @param failedTests number of failed tests
     */
    public void setFailedTests(int failedTests) {
        this.failedTests = failedTests;
    }

    /**
     * Gets the total number of tests.
     * @return total number of tests
     */
    public int getTotalTests() {
        return totalTests;
    }

    /**
     * Sets the total number of tests.
     * @param totalTests total number of tests
     */
    public void setTotalTests(int totalTests) {
        this.totalTests = totalTests;
    }
} 