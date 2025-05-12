package ru.nsu.abramkin.dsl;

import groovy.lang.Closure;
import java.util.ArrayList;
import java.util.List;

public class GroupConfig {
    private String name;
    private List<StudentConfig> students = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public void students(Closure closure) {
        closure.setDelegate(this);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
    }

    public void student(Closure closure) {
        StudentConfig student = new StudentConfig();
        closure.setDelegate(student);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
        students.add(student);
    }

    // Getters
    public String getName() {
        return name;
    }

    public List<StudentConfig> getStudents() {
        return students;
    }
} 