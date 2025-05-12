package ru.nsu.abramkin

class TaskChecker {
    def reportGenerator = new ReportGenerator()
    
    def checkTask(File taskDir) {
        def sourceDir = new File(taskDir, 'src/main/java')
        if (!sourceDir.exists()) {
            return [javadocStatus: '-', checkstyleStatus: '-']
        }
        
        return [
            javadocStatus: reportGenerator.checkJavadoc(sourceDir),
            checkstyleStatus: reportGenerator.checkCheckstyle(sourceDir)
        ]
    }
    
    def processTasks(Map config) {
        config.groups.each { group ->
            group.tasks.each { task ->
                def taskDir = new File("repos/${group.students[0].githubUsername}/${task.id}")
                if (taskDir.exists()) {
                    def checkResults = checkTask(taskDir)
                    task.javadocStatus = checkResults.javadocStatus
                    task.checkstyleStatus = checkResults.checkstyleStatus
                } else {
                    task.javadocStatus = '-'
                    task.checkstyleStatus = '-'
                }
            }
        }
        
        reportGenerator.generateReport(config)
    }
} 