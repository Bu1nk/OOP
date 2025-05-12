package ru.nsu.abramkin.dsl;

import groovy.lang.Closure;
import java.util.HashMap;
import java.util.Map;

public class SystemConfig {
    private Map<String, Integer> gradingScale = new HashMap<>();
    private int testTimeout;
    private Map<String, Map<String, Integer>> additionalPoints = new HashMap<>();

    public void gradingScale(Closure closure) {
        closure.setDelegate(this);
        closure.call();
    }

    public void setExcellent(int points) {
        gradingScale.put("excellent", points);
    }

    public void setGood(int points) {
        gradingScale.put("good", points);
    }

    public void setSatisfactory(int points) {
        gradingScale.put("satisfactory", points);
    }

    public void setTestTimeout(int seconds) {
        this.testTimeout = seconds;
    }

    public void additionalPoints(Closure closure) {
        closure.setDelegate(this);
        closure.call();
    }

    public void methodMissing(String taskId, Object args) {
        if (args instanceof Closure) {
            Map<String, Integer> studentPoints = new HashMap<>();
            additionalPoints.put(taskId, studentPoints);
            ((Closure) args).setDelegate(new Object() {
                public void methodMissing(String studentId, Object points) {
                    studentPoints.put(studentId, (Integer) points);
                }
            });
            ((Closure) args).call();
        }
    }

    // Getters
    public Map<String, Integer> getGradingScale() {
        return gradingScale;
    }

    public int getTestTimeout() {
        return testTimeout;
    }

    public Map<String, Map<String, Integer>> getAdditionalPoints() {
        return additionalPoints;
    }
} 