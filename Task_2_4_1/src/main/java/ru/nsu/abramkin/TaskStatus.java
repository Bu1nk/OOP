package ru.nsu.abramkin;

/**
 * Represents possible statuses of a task during checking process.
 */
public enum TaskStatus {
    /** Task has not been started yet */
    NOT_STARTED,
    /** Task directory was not found */
    NOT_FOUND,
    /** Task failed to compile */
    COMPILATION_FAILED,
    /** Task failed style check */
    STYLE_CHECK_FAILED,
    /** Task was successfully checked */
    COMPLETED
} 