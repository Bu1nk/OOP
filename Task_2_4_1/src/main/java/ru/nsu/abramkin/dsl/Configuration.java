package ru.nsu.abramkin.dsl;

import groovy.lang.Closure;
import ru.nsu.abramkin.dsl.data.Task;

import java.util.ArrayList;
import java.util.List;

public class Configuration {
    private final List<TaskConfig> tasks = new ArrayList<>();
    private final List<GroupConfig> groups = new ArrayList<>();
    private final List<CheckpointConfig> checkpoints = new ArrayList<>();
    private final SystemConfig system = new SystemConfig();
    private List<String> assignmentTasks = new ArrayList<>();
    private List<String> assignmentStudents = new ArrayList<>();

    public void tasks(Closure<Task> closure) {
        closure.setDelegate(this);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
    }

    public void task(Closure closure) {
        TaskConfig task = new TaskConfig();
        closure.setDelegate(task);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
        tasks.add(task);
    }

    public void groups(Closure closure) {
        closure.setDelegate(this);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
    }

    public void group(Closure closure) {
        GroupConfig group = new GroupConfig();
        closure.setDelegate(group);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
        groups.add(group);
    }

    public void checkpoints(Closure closure) {
        closure.setDelegate(this);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
    }

    public void checkpoint(Closure closure) {
        CheckpointConfig checkpoint = new CheckpointConfig();
        closure.setDelegate(checkpoint);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
        checkpoints.add(checkpoint);
    }

    public void system(Closure closure) {
        closure.setDelegate(system);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
    }

    public void assignment(Closure closure) {
        closure.setDelegate(this);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
    }

    public void setTasks(List<String> tasks) {
        this.assignmentTasks = new ArrayList<>(tasks);
    }

    public void setStudents(List<String> students) {
        this.assignmentStudents = new ArrayList<>(students);
    }

    public void setAssignmentTasks(List<String> tasks) {
        this.assignmentTasks = new ArrayList<>(tasks);
    }

    public void setAssignmentStudents(List<String> students) {
        this.assignmentStudents = new ArrayList<>(students);
    }

    // Getters
    public List<TaskConfig> getTasks() {
        return tasks;
    }

    public List<GroupConfig> getGroups() {
        return groups;
    }

    public List<CheckpointConfig> getCheckpoints() {
        return checkpoints;
    }

    public SystemConfig getSystem() {
        return system;
    }

    public List<String> getAssignmentTasks() {
        return assignmentTasks;
    }

    public List<String> getAssignmentStudents() {
        return assignmentStudents;
    }
} 