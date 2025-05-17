package ru.nsu.abramkin;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import ru.nsu.abramkin.dsl.Configuration;
import ru.nsu.abramkin.dsl.TaskConfig;
import ru.nsu.abramkin.dsl.StudentConfig;
import ru.nsu.abramkin.dsl.GroupConfig;
import ru.nsu.abramkin.TaskChecker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class Main {
    private static final String CONFIG_FILE = "config.groovy";
    private static Configuration config;
    private final static  Map<String, Map<String, TaskResult>> results = new HashMap<>();

    public static void main(String[] args) {
        try {
            System.out.println("Starting application...");
            loadConfiguration();
            processTasks();
            generateReport();
            System.out.println("Application finished successfully.");
        } catch (Exception e) {
            System.err.println("Application failed with error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void loadConfiguration() {
        try {
            System.out.println("Loading configuration from " + CONFIG_FILE);
            Binding binding = new Binding();
            GroovyShell shell = new GroovyShell(binding);
            Object result = shell.evaluate(new File(CONFIG_FILE));
            
            if (result instanceof Configuration) {
                config = (Configuration) result;
                System.out.println("Configuration loaded successfully.");
                System.out.println("Tasks to check: " + config.getAssignmentTasks());
                System.out.println("Students to check: " + config.getAssignmentStudents());
            } else {
                throw new IllegalStateException("Configuration script did not return a Configuration object");
            }
        } catch (Exception e) {
            System.err.println("Error loading configuration: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void processTasks() {
        System.out.println("Starting task processing...");
        
        // Copy Checkstyle configuration
        try {
            File checkstyleConfig = new File("google_checks.xml");
            if (!checkstyleConfig.exists()) {
                System.out.println("Copying Checkstyle configuration...");
                Files.copy(
                    new File("src/main/resources/google_checks.xml").toPath(),
                    checkstyleConfig.toPath()
                );
            }
        } catch (IOException e) {
            System.err.println("Error copying Checkstyle configuration: " + e.getMessage());
        }
        
        for (String studentId : config.getAssignmentStudents()) {
            StudentConfig student = findStudent(studentId);
            if (student == null) {
                System.err.println("Student not found: " + studentId);
                continue;
            }

            System.out.println("Processing student: " + student.getFullName() + " (" + student.getGithubUsername() + ")");
            results.put(studentId, new HashMap<>());
            cloneRepository(student);
            
            for (String taskId : config.getAssignmentTasks()) {
                System.out.println("Checking task: " + taskId);
                TaskConfig task = findTask(taskId);
                if (task == null) {
                    System.err.println("Task not found: " + taskId);
                    continue;
                }

                TaskResult result = checkTask(student, task);
                results.get(studentId).put(taskId, result);
                System.out.println("Task " + taskId + " result: " + result.getStatus() + ", points: " + result.getPoints());
            }
        }
        System.out.println("Task processing completed.");
    }

    private static StudentConfig findStudent(String studentId) {
        return config.getGroups().stream()
                .flatMap(group -> group.getStudents().stream())
                .filter(student -> student.getGithubUsername().equals(studentId))
                .findFirst()
                .orElse(null);
    }

    private static TaskConfig findTask(String taskId) {
        return config.getTasks().stream()
                .filter(task -> task.getId().equals(taskId))
                .findFirst()
                .orElse(null);
    }

    private static void cloneRepository(StudentConfig student) {
        try {
            String repoPath = "repos/" + student.getGithubUsername();
            System.out.println("Cloning repository to: " + repoPath);
            
            // Delete directory if exists
            if (Files.exists(Path.of(repoPath))) {
                System.out.println("Repository directory exists, deleting...");
                deleteDirectory(new File(repoPath));
            }
            
            Files.createDirectories(Path.of(repoPath));
            
            ProcessBuilder pb = new ProcessBuilder("git", "clone", student.getRepositoryUrl(), repoPath);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            
            // Print git output
            java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Git: " + line);
            }
            
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Failed to clone repository for student: " + student.getGithubUsername());
            } else {
                System.out.println("Repository cloned successfully.");
            }
        } catch (Exception e) {
            System.err.println("Error cloning repository: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        directory.delete();
    }

    private static TaskResult checkTask(StudentConfig student, TaskConfig task) {
        TaskResult result = new TaskResult();
        String taskPath = "repos/" + student.getGithubUsername() + "/" + task.getId();
        System.out.println("Checking task at path: " + taskPath);

        // Check if task directory exists
        if (!Files.exists(Path.of(taskPath))) {
            System.out.println("Task directory not found: " + taskPath);
            result.setStatus(TaskStatus.NOT_FOUND);
            return result;
        }

        // Check Javadoc and Checkstyle
        TaskChecker taskChecker = new TaskChecker();
        Map<String, String> checkResults = taskChecker.checkTask(new File(taskPath));
        result.setJavadocStatus(checkResults.get("javadocStatus"));
        result.setCheckstyleStatus(checkResults.get("checkstyleStatus"));

        // Compile
        if (!compileTask(taskPath)) {
            System.out.println("Compilation failed for task: " + task.getId());
            result.setStatus(TaskStatus.COMPILATION_FAILED);
            return result;
        }

        // Run tests
        TestResult testResult = runTests(taskPath);
        result.setTestResult(testResult);

        // Calculate points
        calculatePoints(result, task, student);
        
        System.out.println("Task check completed. Status: " + result.getStatus() + ", Points: " + result.getPoints());
        return result;
    }

    private static boolean compileTask(String taskPath) {
        try {
            System.out.println("Compiling task at: " + taskPath);
            
            File taskDir = new File(taskPath);
            String gradlewPath = new File(taskDir, "gradlew.bat").getAbsolutePath();
            System.out.println("Using Gradle wrapper at: " + gradlewPath);
            
            if (!new File(gradlewPath).exists()) {
                System.err.println("Gradle wrapper not found at: " + gradlewPath);
                return false;
            }
            
            ProcessBuilder pb = new ProcessBuilder(gradlewPath, "build", "-x", "test");
            pb.directory(taskDir);
            pb.environment().put("JAVA_HOME", System.getenv("JAVA_HOME"));
            pb.redirectErrorStream(true);
            Process process = pb.start();
            
            // Print compilation output
            java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Compile: " + line);
            }
            
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Compilation failed with exit code: " + exitCode);
                return false;
            }
            
            return true;
        } catch (Exception e) {
            System.err.println("Compilation error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private boolean checkStyle(String taskPath) {
        try {
            File checkstyleJar = new File("checkstyle.jar");
            if (!checkstyleJar.exists()) {
                System.err.println("checkstyle.jar not found. Skipping style check.");
                return true;
            }
            
            System.out.println("Checking code style at: " + taskPath);
            ProcessBuilder pb = new ProcessBuilder("java", "-jar", "checkstyle.jar", 
                "-c", "src/main/resources/google_checks.xml", taskPath);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            
            // Print checkstyle output
            java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.InputStreamReader(process.getInputStream()));
            String line;
            boolean hasErrors = false;
            while ((line = reader.readLine()) != null) {
                System.out.println("Checkstyle: " + line);
                if (line.contains("ERROR")) {
                    hasErrors = true;
                }
            }
            
            return !hasErrors;
        } catch (Exception e) {
            System.err.println("Style check error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private static TestResult runTests(String taskPath) {
        TestResult result = new TestResult();
        try {
            System.out.println("Running tests for: " + taskPath);
            
            File taskDir = new File(taskPath);
            String gradlewPath = new File(taskDir, "gradlew.bat").getAbsolutePath();
            System.out.println("Using Gradle wrapper at: " + gradlewPath);
            
            if (!new File(gradlewPath).exists()) {
                System.err.println("Gradle wrapper not found at: " + gradlewPath);
                return result;
            }
            
            // Check if gradlew.bat is executable
            if (!new File(gradlewPath).canExecute()) {
                System.err.println("Gradle wrapper is not executable at: " + gradlewPath);
                return result;
            }
            
            // Check if test directory exists
            File testDir = new File(taskDir, "src/test/java");
            if (!testDir.exists() || !testDir.isDirectory()) {
                System.out.println("No test directory found at: " + testDir.getAbsolutePath());
                return result;
            }
            
            System.out.println("Current directory: " + taskDir.getAbsolutePath());
            System.out.println("JAVA_HOME: " + System.getenv("JAVA_HOME"));
            
            // Run tests
            ProcessBuilder pb = new ProcessBuilder(gradlewPath, "test");
            pb.directory(taskDir);
            pb.environment().put("JAVA_HOME", System.getenv("JAVA_HOME"));
            pb.redirectErrorStream(true);
            
            System.out.println("Starting test process...");
            Process process = pb.start();
            
            // Set a timeout of 60 seconds for tests
            if (!process.waitFor(60, java.util.concurrent.TimeUnit.SECONDS)) {
                process.destroyForcibly();
                System.err.println("Test execution timed out after 60 seconds");
                return result;
            }
            
            // После выполнения тестов парсим XML-отчёты
            File resultsDir = new File(taskDir, "build/test-results/test");
            if (resultsDir.exists() && resultsDir.isDirectory()) {
                int total = 0, failed = 0;
                File[] files = resultsDir.listFiles((dir, name) -> name.endsWith(".xml"));
                if (files != null) {
                    for (File xml : files) {
                        try {
                            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                            Document doc = dBuilder.parse(xml);
                            doc.getDocumentElement().normalize();
                            Element testSuite = doc.getDocumentElement();
                            if (testSuite.getNodeName().equals("testsuite")) {
                                int tests = Integer.parseInt(testSuite.getAttribute("tests"));
                                int failures = Integer.parseInt(testSuite.getAttribute("failures"));
                                int errors = Integer.parseInt(testSuite.getAttribute("errors"));
                                int skipped = 0;
                                if (testSuite.hasAttribute("skipped")) {
                                    skipped = Integer.parseInt(testSuite.getAttribute("skipped"));
                                }
                                total += tests;
                                failed += failures + errors;
                            }
                        } catch (Exception e) {
                            System.err.println("Error parsing test xml: " + xml.getName() + ": " + e.getMessage());
                        }
                    }
                }
                result.setTotalTests(total);
                result.setFailedTests(failed);
                result.setPassedTests(total - failed);
            }
            System.out.println("Test results: " + result.getPassedTests() + " passed, " + result.getFailedTests() + " failed");
            return result;
        } catch (Exception e) {
            System.err.println("Test execution error: " + e.getMessage());
            e.printStackTrace();
            return result;
        }
    }
    
    private int countTestMethods(File directory) {
        int count = 0;
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    count += countTestMethods(file);
                } else if (file.getName().endsWith("Test.java")) {
                    try {
                        // Read the file and count @Test annotations
                        java.nio.file.Path path = file.toPath();
                        java.util.List<String> lines = java.nio.file.Files.readAllLines(path);
                        for (String line : lines) {
                            if (line.contains("@Test")) {
                                count++;
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("Error counting tests in file: " + file.getName());
                    }
                }
            }
        }
        return count;
    }

    private static void calculatePoints(TaskResult result, TaskConfig task, StudentConfig student) {
        int points = 0;
        // Проверяем дату сдачи (по текущей дате)
        java.time.LocalDate now = java.time.LocalDate.now();
        java.time.LocalDate hardDeadline = task.getHardDeadline();
        if (now.isAfter(hardDeadline)) {
            points = task.getMaxPoints() / 2;
        } else {
            points = task.getMaxPoints();
        }
        result.setPoints(points);
        result.setStatus(TaskStatus.COMPLETED);
        System.out.println("Calculated points for task " + task.getId() + ": " + points);
    }

    private static void generateReport() {
        try {
            System.out.println("Generating HTML report...");
            try (FileWriter writer = new FileWriter("report.html")) {
                writer.write("<!DOCTYPE html>\n<html>\n<head>\n");
                writer.write("<title>OOP Task Check Report</title>\n");
                writer.write("<style>\n");
                writer.write("body { font-family: Arial, sans-serif; margin: 20px; }\n");
                writer.write(".group-block { border: 2px solid #333; margin: 20px 0; padding: 15px; border-radius: 5px; }\n");
                writer.write(".task-block { border: 1px solid #666; margin: 15px 0; padding: 10px; border-radius: 3px; }\n");
                writer.write(".student-block { background-color: #f9f9f9; margin: 10px 0; padding: 10px; border-radius: 3px; }\n");
                writer.write("table { border-collapse: collapse; width: 100%; margin: 10px 0; }\n");
                writer.write("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }\n");
                writer.write("th { background-color: #f2f2f2; }\n");
                writer.write("h1 { color: #333; }\n");
                writer.write("h2 { color: #444; margin-top: 20px; }\n");
                writer.write("h3 { color: #555; margin-top: 15px; }\n");
                writer.write(".status-completed { color: green; }\n");
                writer.write(".status-failed { color: red; }\n");
                writer.write("</style>\n</head>\n<body>\n");
                
                writer.write("<h1>OOP Task Check Report</h1>\n");
                
                // Group blocks
                for (GroupConfig group : config.getGroups()) {
                    writer.write(String.format("<div class=\"group-block\">\n<h2>Group: %s</h2>\n", group.getName()));
                    
                    // Task blocks for each group
                    for (TaskConfig task : config.getTasks()) {
                        writer.write("<div class=\"task-block\">\n");
                        writer.write(String.format("<h3>Task: %s (%s)</h3>\n", task.getName(), task.getId()));
                        
                        // Task details table
                        writer.write("<table>\n");
                        writer.write("<tr><th>Max Points</th><th>Soft Deadline</th><th>Hard Deadline</th></tr>\n");
                        writer.write(String.format("<tr><td>%d</td><td>%s</td><td>%s</td></tr>\n",
                            task.getMaxPoints(), task.getSoftDeadline(), task.getHardDeadline()));
                        writer.write("</table>\n");
                        
                        // Student blocks for each task
                        for (StudentConfig student : group.getStudents()) {
                            String studentId = student.getGithubUsername();
                            if (results.containsKey(studentId) && results.get(studentId).containsKey(task.getId())) {
                                TaskResult result = results.get(studentId).get(task.getId());
                                TestResult testResult = result.getTestResult();
                                
                                writer.write("<div class=\"student-block\">\n");
                                writer.write(String.format("<h4>Student: %s (%s)</h4>\n", 
                                    student.getFullName(), student.getGithubUsername()));
                                
                                writer.write("<table>\n");
                                writer.write("<tr><th>Status</th><th>Points</th><th>Tests</th><th>Javadoc</th><th>Checkstyle</th></tr>\n");
                                writer.write(String.format("<tr><td class=\"status-%s\">%s</td><td>%d</td><td>Passed: %d, Failed: %d, Total: %d</td><td>%s</td><td>%s</td></tr>\n",
                                    result.getStatus().toString().toLowerCase(), result.getStatus(), result.getPoints(),
                                    testResult.getPassedTests(), testResult.getFailedTests(), testResult.getTotalTests(),
                                    result.getJavadocStatus(), result.getCheckstyleStatus()));
                                writer.write("</table>\n");
                                writer.write("</div>\n");
                            }
                        }
                        writer.write("</div>\n");
                    }
                    writer.write("</div>\n");
                }
                
                writer.write("</body>\n</html>");
            }
            System.out.println("Report generated successfully.");
        } catch (IOException e) {
            System.err.println("Error generating report: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 