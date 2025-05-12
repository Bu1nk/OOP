package ru.nsu.abramkin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for TaskChecker class.
 * Verifies the functionality of task checking and documentation validation.
 */
public class TaskCheckerTest {
    
    @TempDir
    Path tempDir;
    
    /**
     * Tests checking a non-existent task directory.
     * Verifies that appropriate status is returned for missing directory.
     */
    @Test
    public void testCheckNonExistentTask() {
        TaskChecker checker = new TaskChecker();
        File nonExistentDir = new File(tempDir.toFile(), "non_existent");
        
        Map<String, String> result = checker.checkTask(nonExistentDir);
        
        assertEquals("-", result.get("javadocStatus"));
        assertEquals("-", result.get("checkstyleStatus"));
    }
    
    /**
     * Tests checking a task directory with a Java file without Javadoc.
     * Verifies that Javadoc status is correctly reported.
     */
    @Test
    public void testCheckTaskWithoutJavadoc() throws IOException {
        TaskChecker checker = new TaskChecker();
        File taskDir = tempDir.toFile();
        File sourceDir = new File(taskDir, "src/main/java");
        sourceDir.mkdirs();
        
        // Create a Java file without Javadoc
        File javaFile = new File(sourceDir, "Test.java");
        Files.writeString(javaFile.toPath(), "public class Test {}");
        
        Map<String, String> result = checker.checkTask(taskDir);
        
        assertEquals("-", result.get("javadocStatus"));
    }
    
    /**
     * Tests checking a task directory with a Java file with Javadoc.
     * Verifies that Javadoc status is correctly reported.
     */
    @Test
    public void testCheckTaskWithJavadoc() throws IOException {
        TaskChecker checker = new TaskChecker();
        File taskDir = tempDir.toFile();
        File sourceDir = new File(taskDir, "src/main/java");
        sourceDir.mkdirs();
        
        // Create a Java file with Javadoc
        File javaFile = new File(sourceDir, "Test.java");
        Files.writeString(javaFile.toPath(), 
            "/**\n * Test class.\n */\npublic class Test {}");
        
        Map<String, String> result = checker.checkTask(taskDir);
        
        assertEquals("+", result.get("javadocStatus"));
    }
} 