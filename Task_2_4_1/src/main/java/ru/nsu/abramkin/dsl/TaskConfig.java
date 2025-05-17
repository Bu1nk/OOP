package ru.nsu.abramkin.dsl;

import java.time.LocalDate;

public class TaskConfig {
    private String id;
    private String name;
    private int maxPoints;
    private LocalDate softDeadline;
    private LocalDate hardDeadline;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public void setSoftDeadline(String softDeadline) {
        this.softDeadline = LocalDate.parse(softDeadline);
    }

    public void setHardDeadline(String hardDeadline) {
        this.hardDeadline = LocalDate.parse(hardDeadline);
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public LocalDate getSoftDeadline() {
        return softDeadline;
    }

    public LocalDate getHardDeadline() {
        return hardDeadline;
    }
} 