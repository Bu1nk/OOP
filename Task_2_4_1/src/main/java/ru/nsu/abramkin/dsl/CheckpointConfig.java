package ru.nsu.abramkin.dsl;

import java.time.LocalDate;

public class CheckpointConfig {
    private String name;
    private LocalDate date;

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = LocalDate.parse(date);
    }

    // Getters
    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }
} 