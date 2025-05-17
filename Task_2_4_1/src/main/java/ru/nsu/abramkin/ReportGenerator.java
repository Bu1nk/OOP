package ru.nsu.abramkin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.Map;

/**
 * Generates HTML reports for task checking results.
 * Creates a detailed report with task statuses, points, and test results.
 */
public class ReportGenerator {
    private final String outputDir;

    /**
     * Creates a new ReportGenerator with specified output directory.
     * @param outputDir directory where reports will be saved
     */
    public ReportGenerator(String outputDir) {
        this.outputDir = outputDir;
    }

    /**
     * Generates an HTML report for task checking results.
     * @param results map of task results to include in the report
     * @param studentName name of the student whose tasks were checked
     * @return path to the generated report file
     */
    public String generateReport(Map<String, TaskResult> results, String studentName) {
        StringBuilder html = new StringBuilder();
        html.append(createHeader());
        html.append("<h1>Task Check Results for ").append(studentName).append("</h1>");
        html.append("<table>");
        html.append("<tr><th>Task</th><th>Status</th><th>Points</th><th>Tests</th><th>Javadoc</th><th>Checkstyle</th></tr>");
        
        for (Map.Entry<String, TaskResult> entry : results.entrySet()) {
            html.append(createTaskRow(entry.getKey(), entry.getValue()));
        }
        
        html.append("</table>");
        html.append(createFooter());
        
        try {
            Files.createDirectories(Path.of(outputDir));
            String reportPath = outputDir + "/report.html";
            Files.writeString(Path.of(reportPath), html.toString());
            return reportPath;
        } catch (IOException e) {
            System.err.println("Error generating report: " + e.getMessage());
            return null;
        }
    }

    /**
     * Creates the HTML header with styles.
     * @return HTML header as string
     */
    private String createHeader() {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; margin: 20px; }
                    table { border-collapse: collapse; width: 100%; }
                    th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
                    th { background-color: #f2f2f2; }
                    .success { color: green; }
                    .error { color: red; }
                    .warning { color: orange; }
                </style>
            </head>
            <body>
            """;
    }

    /**
     * Creates the HTML footer.
     * @return HTML footer as string
     */
    private String createFooter() {
        return "</body></html>";
    }

    /**
     * Creates a table row for a task result.
     * @param taskName name of the task
     * @param result result of the task check
     * @return HTML table row as string
     */
    private String createTaskRow(String taskName, TaskResult result) {
        return String.format("""
            <tr>
                <td>%s</td>
                <td class="%s">%s</td>
                <td>%d</td>
                <td class="%s">%d/%d</td>
                <td class="%s">%s</td>
                <td class="%s">%s</td>
            </tr>
            """,
            taskName,
            getStatusClass(result.getStatus()),
            result.getStatus(),
            result.getPoints(),
            getTestResultClass(result.getTestResult()),
            result.getTestResult().getPassedTests(),
            result.getTestResult().getTotalTests(),
            getJavadocStatusClass(result.getJavadocStatus()),
            result.getJavadocStatus(),
            getCheckstyleStatusClass(result.getCheckstyleStatus()),
            result.getCheckstyleStatus()
        );
    }

    /**
     * Gets CSS class for task status.
     * @param status status of the task
     * @return CSS class name
     */
    private String getStatusClass(TaskStatus status) {
        return switch (status) {
            case COMPLETED -> "success";
            case NOT_FOUND, COMPILATION_FAILED, STYLE_CHECK_FAILED -> "error";
            default -> "warning";
        };
    }

    /**
     * Gets CSS class for test result.
     * @param result test result
     * @return CSS class name
     */
    private String getTestResultClass(TestResult result) {
        if (result.getTotalTests() == 0) return "warning";
        return result.getFailedTests() == 0 ? "success" : "error";
    }

    /**
     * Gets CSS class for Javadoc status.
     * @param status Javadoc status
     * @return CSS class name
     */
    private String getJavadocStatusClass(String status) {
        return "+".equals(status) ? "success" : "error";
    }

    /**
     * Gets CSS class for Checkstyle status.
     * @param status Checkstyle status
     * @return CSS class name
     */
    private String getCheckstyleStatusClass(String status) {
        return "+".equals(status) ? "success" : "error";
    }

    /**
     * Checks if Java files in the source directory have Javadoc comments.
     * @param sourceDir directory containing Java source files
     * @return "+" if Javadoc is present, "-" otherwise
     */
    public String checkJavadoc(File sourceDir) {
        try {
            long javaFileCount = Files.walk(sourceDir.toPath())
                .filter(path -> path.toString().endsWith(".java"))
                .count();
            if (javaFileCount == 0) {
                return "-";
            }
            return Files.walk(sourceDir.toPath())
                .filter(path -> path.toString().endsWith(".java"))
                .map(path -> {
                    try {
                        String content = new String(Files.readAllBytes(path));
                        return content.contains("/**") && content.contains("*/");
                    } catch (IOException e) {
                        return false;
                    }
                })
                .anyMatch(hasJavadoc -> hasJavadoc) ? "+" : "-";
        } catch (IOException e) {
            System.err.println("Error checking Javadoc: " + e.getMessage());
            return "-";
        }
    }

    /**
     * Runs Checkstyle on Java files in the source directory.
     * @param sourceDir directory containing Java source files
     * @return "+" if style check passed, "-" otherwise
     */
    public String checkCheckstyle(File sourceDir) {
        String checkstyleResult = "-";
        try {
            File checkstyleJar = new File("checkstyle.jar");
            if (!checkstyleJar.exists()) {
                System.err.println("checkstyle.jar not found");
                return "-";
            }

            File configFile = new File("google_checks.xml");
            if (!configFile.exists()) {
                System.err.println("google_checks.xml not found");
                return "-";
            }

            ProcessBuilder pb = new ProcessBuilder("java", "-jar", "checkstyle.jar", "-c", "google_checks.xml", sourceDir.getAbsolutePath());
            Process process = pb.start();
            
            if (!process.waitFor(60, TimeUnit.SECONDS)) {
                process.destroyForcibly();
                System.err.println("Checkstyle check timed out after 60 seconds");
                return "-";
            }
            
            java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.InputStreamReader(process.getInputStream()));
            String line;
            boolean hasErrors = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains("ERROR")) {
                    hasErrors = true;
                    break;
                }
            }
            
            checkstyleResult = !hasErrors ? "+" : "-";
        } catch (Exception e) {
            System.err.println("Error running checkstyle: " + e.getMessage());
        }
        return checkstyleResult;
    }
} 