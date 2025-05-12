import ru.nsu.abramkin.dsl.TaskConfig
import ru.nsu.abramkin.dsl.GroupConfig
import ru.nsu.abramkin.dsl.StudentConfig
import ru.nsu.abramkin.dsl.CheckpointConfig
import ru.nsu.abramkin.dsl.SystemConfig

// Tasks configuration
tasks {
    task {
        id = "Task_1_1_1"
        name = "Introduction to OOP"
        maxPoints = 10
        softDeadline = "2024-03-01"
        hardDeadline = "2024-03-15"
    }
    
    task {
        id = "Task_2_1_1"
        name = "Inheritance and Polymorphism"
        maxPoints = 15
        softDeadline = "2024-03-15"
        hardDeadline = "2024-03-30"
    }
}

// Groups configuration
groups {
    group {
        name = "23217"
        students {
            student {
                githubUsername = "Bu1nk"
                fullName = "Абрамкин Никита"
                repositoryUrl = "https://github.com/Bu1nk/OOP.git"
            }
        }
    }
}

// Checkpoints configuration
checkpoints {
    checkpoint {
        name = "Midterm"
        date = "2024-04-15"
    }
    
    checkpoint {
        name = "Final"
        date = "2024-06-01"
    }
}

// System configuration
system {
    gradingScale {
        excellent = 85
        good = 70
        satisfactory = 50
    }
    
    testTimeout = 30 // seconds
    additionalPoints = [
        "Task_1_1_1": [
            "Bu1nk": 2 // Additional points for specific student and task
        ]
    ]
}

// Assignment configuration
assignment {
    tasks = ["Task_1_1_1", "Task_2_1_1"]
    students = ["Bu1nk"]
} 