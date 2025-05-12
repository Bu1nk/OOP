import ru.nsu.abramkin.dsl.TaskConfig
import ru.nsu.abramkin.dsl.GroupConfig
import ru.nsu.abramkin.dsl.StudentConfig
import ru.nsu.abramkin.dsl.CheckpointConfig
import ru.nsu.abramkin.dsl.SystemConfig

def config = new ru.nsu.abramkin.dsl.Configuration()

// Define tasks
config.tasks {
    task {
        id = "Task_1_1_1"
        name = "Пирамидальная сортировка"
        maxPoints = 1
        softDeadline = "2024-09-01"
        hardDeadline = "2024-09-08"
    }
    
    task {
        id = "Task_1_1_2"
        name = "Консольный блэкджек"
        maxPoints = 2
        softDeadline = "2024-09-08"
        hardDeadline = "2024-09-22"
    }
    
    task {
        id = "Task_1_1_3"
        name = "Операции с уравнениями"
        maxPoints = 2
        softDeadline = "2024-09-22"
        hardDeadline = "2024-10-06"
    }
    
    task {
        id = "Task_1_2_1"
        name = "Граф"
        maxPoints = 2
        softDeadline = "2024-10-06"
        hardDeadline = "2024-10-20"
    }
    
    task {
        id = "Task_1_2_2"
        name = "Хеш-таблица"
        maxPoints = 2
        softDeadline = "2024-10-20"
        hardDeadline = "2024-11-03"
    }
    
    task {
        id = "Task_1_3_1"
        name = "Поиск подстроки"
        maxPoints = 2
        softDeadline = "2024-11-03"
        hardDeadline = "2024-11-17"
    }
    
    task {
        id = "Task_1_4_1"
        name = "Зачетная книжка"
        maxPoints = 1
        softDeadline = "2024-11-17"
        hardDeadline = "2024-11-24"
    }
    
    task {
        id = "Task_1_5_1"
        name = "Генератор markdown"
        maxPoints = 4
        softDeadline = "2024-11-24"
        hardDeadline = "2024-12-22"
    }

    task {
        id = "Task_2_1_1"
        name = "Простые числа"
        maxPoints = 1
        softDeadline = "2025-02-10"
        hardDeadline = "2025-03-10"
    }
    
    task {
        id = "Task_2_2_1"
        name = "Пиццерия"
        maxPoints = 1
        softDeadline = "2025-03-24"
        hardDeadline = "2025-03-31"
    }
    
    task {
        id = "Task_2_3_1"
        name = "Змейка"
        maxPoints = 1
        softDeadline = "2025-04-21"
        hardDeadline = "2025-04-28"
    }
    
    task {
        id = "Task_2_4_1"
        name = "Автоматическая проверка задач по ООП"
        maxPoints = 1
        softDeadline = "2025-05-12"
        hardDeadline = "2025-05-19"
    }

    task {
        id = "Task_2_1_2"
        name = "Простые числа*"
        maxPoints = 1
        softDeadline = "2025-05-26"
        hardDeadline = "2025-06-02"
    }
}

// Define groups
config.groups {
    group {
        name = "23217"
        students {
            student {
                githubUsername = "Bu1nk"
                fullName = "Абрамкин Никита"
                repositoryUrl = "https://github.com/Bu1nk/OOP.git"
            }
            student {
                githubUsername = "Hom4ikTop4ik"
                fullName = "Мартынов Богдан"
                repositoryUrl = "https://github.com/Hom4ikTop4ik/OOP.git"
            }
        }
    }
    group {
        name = "23215"
        students {
            student {
                githubUsername = "ArseniyLyskov"
                fullName = "Арсений Лысков"
                repositoryUrl = "https://github.com/ArseniyLyskov/OOP.git"
            }
        }
    }
}

// Define checkpoints
config.checkpoints {
    checkpoint {
        name = "Midterm"
        date = "2024-04-15"
    }
    
    checkpoint {
        name = "Final"
        date = "2024-06-01"
    }
}

// Define system settings
config.system {
    gradingScale {
        excellent = 85
        good = 70
        satisfactory = 50
    }
    
    testTimeout = 30 // seconds
}

// Set assignment tasks and students
config.setAssignmentTasks(["Task_1_1_1", "Task_2_1_1", "Task_2_2_1", "Task_1_1_2", "Task_1_1_3", "Task_1_2_1", "Task_1_2_2", "Task_1_3_1", "Task_1_4_1", "Task_1_5_1", "Task_2_1_2", "Task_2_3_1", "Task_2_4_1"])
config.setAssignmentStudents(["Bu1nk", "Hom4ikTop4ik", "ArseniyLyskov"])

return config 