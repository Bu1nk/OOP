package ru.nsu.abramkin

import groovy.text.SimpleTemplateEngine
import java.nio.file.Files
import java.nio.file.Paths

class ReportGenerator {
    def checkJavadoc(File sourceDir) {
        def hasJavadoc = false
        sourceDir.eachFileRecurse { file ->
            if (file.name.endsWith('.java')) {
                def content = file.text
                if (content.contains('/**') && content.contains('*/')) {
                    hasJavadoc = true
                    return
                }
            }
        }
        return hasJavadoc ? '+' : '-'
    }

    def checkCheckstyle(File sourceDir) {
        def checkstyleResult = '-'
        try {
            def process = ['java', '-jar', 'checkstyle.jar', '-c', 'google_checks.xml', sourceDir.absolutePath].execute()
            process.waitFor()
            checkstyleResult = process.exitValue() == 0 ? '+' : '-'
        } catch (Exception e) {
            println "Error running checkstyle: ${e.message}"
        }
        return checkstyleResult
    }

    def generateReport(Map config) {
        def template = '''
<!DOCTYPE html>
<html>
<head>
<title>OOP Task Check Report</title>
<style>
body { font-family: Arial, sans-serif; margin: 20px; }
.group-block { border: 2px solid #333; margin: 20px 0; padding: 15px; border-radius: 5px; }
.task-block { border: 1px solid #666; margin: 15px 0; padding: 10px; border-radius: 3px; }
.student-block { background-color: #f9f9f9; margin: 10px 0; padding: 10px; border-radius: 3px; }
table { border-collapse: collapse; width: 100%; margin: 10px 0; }
th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
th { background-color: #f2f2f2; }
h1 { color: #333; }
h2 { color: #444; margin-top: 20px; }
h3 { color: #555; margin-top: 15px; }
.status-completed { color: green; }
.status-failed { color: red; }
</style>
</head>
<body>
<h1>OOP Task Check Report</h1>
<% config.groups.each { group -> %>
<div class="group-block">
<h2>Group: ${group.name}</h2>
<% group.tasks.each { task -> %>
<div class="task-block">
<h3>Task: ${task.name} (${task.id})</h3>
<table>
<tr><th>Max Points</th><th>Soft Deadline</th><th>Hard Deadline</th></tr>
<tr><td>${task.maxPoints}</td><td>${task.softDeadline}</td><td>${task.hardDeadline}</td></tr>
</table>
<% group.students.each { student -> %>
<div class="student-block">
<h4>Student: ${student.fullName} (${student.githubUsername})</h4>
<table>
<tr><th>Status</th><th>Points</th><th>Tests</th><th>Javadoc</th><th>Checkstyle</th></tr>
<tr>
<td class="status-${task.status}">${task.status.toUpperCase()}</td>
<td>${task.points}</td>
<td>Passed: ${task.passedTests}, Failed: ${task.failedTests}, Total: ${task.totalTests}</td>
<td>${task.javadocStatus}</td>
<td>${task.checkstyleStatus}</td>
</tr>
</table>
</div>
<% } %>
</div>
<% } %>
</div>
<% } %>
</body>
</html>
'''
        def engine = new SimpleTemplateEngine()
        def templateEngine = engine.createTemplate(template)
        def report = templateEngine.make(config)
        Files.write(Paths.get('report.html'), report.toString().bytes)
    }
} 