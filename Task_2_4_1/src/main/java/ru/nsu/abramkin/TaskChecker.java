package ru.nsu.abramkin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Checks tasks for Javadoc and Checkstyle compliance.
 */
public class TaskChecker {
    private final ReportGenerator reportGenerator;
    
    /**
     * Creates a new TaskChecker with default output directory.
     */
    public TaskChecker() {
        this.reportGenerator = new ReportGenerator("reports");
    }
    
    /**
     * Checks a task directory for Javadoc and Checkstyle compliance.
     * @param taskDir directory containing the task
     * @return map with Javadoc and Checkstyle status
     */
    public Map<String, String> checkTask(File taskDir) {
        File sourceDir = new File(taskDir, "src/main/java");
        if (!sourceDir.exists()) {
            Map<String, String> result = new HashMap<>();
            result.put("javadocStatus", "-");
            result.put("checkstyleStatus", "-");
            return result;
        }
        
        Map<String, String> result = new HashMap<>();
        result.put("javadocStatus", reportGenerator.checkJavadoc(sourceDir));
        result.put("checkstyleStatus", reportGenerator.checkCheckstyle(sourceDir));
        return result;
    }
} 