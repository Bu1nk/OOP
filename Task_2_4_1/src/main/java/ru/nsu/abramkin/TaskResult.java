package ru.nsu.abramkin;

/**
 * Represents the result of checking a task.
 * Contains information about task status, points, test results, and documentation status.
 */
public class TaskResult {
    private TaskStatus status;
    private int points;
    private TestResult testResult;
    private String javadocStatus;
    private String checkstyleStatus;

    /**
     * Creates a new TaskResult with default values.
     */
    public TaskResult() {
        this.status = TaskStatus.NOT_STARTED;
        this.points = 0;
        this.testResult = new TestResult();
        this.javadocStatus = "-";
        this.checkstyleStatus = "-";
    }

    /**
     * Gets the current status of the task.
     * @return task status
     */
    public TaskStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the task.
     * @param status new task status
     */
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    /**
     * Gets the points awarded for the task.
     * @return number of points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Sets the points awarded for the task.
     * @param points number of points
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Gets the test execution results.
     * @return test results
     */
    public TestResult getTestResult() {
        return testResult;
    }

    /**
     * Sets the test execution results.
     * @param testResult test results
     */
    public void setTestResult(TestResult testResult) {
        this.testResult = testResult;
    }

    /**
     * Gets the Javadoc documentation status.
     * @return "+" if documentation is present, "-" otherwise
     */
    public String getJavadocStatus() {
        return javadocStatus;
    }

    /**
     * Sets the Javadoc documentation status.
     * @param javadocStatus "+" if documentation is present, "-" otherwise
     */
    public void setJavadocStatus(String javadocStatus) {
        this.javadocStatus = javadocStatus;
    }

    /**
     * Gets the Checkstyle status.
     * @return "+" if style check passed, "-" otherwise
     */
    public String getCheckstyleStatus() {
        return checkstyleStatus;
    }

    /**
     * Sets the Checkstyle status.
     * @param checkstyleStatus "+" if style check passed, "-" otherwise
     */
    public void setCheckstyleStatus(String checkstyleStatus) {
        this.checkstyleStatus = checkstyleStatus;
    }
} 