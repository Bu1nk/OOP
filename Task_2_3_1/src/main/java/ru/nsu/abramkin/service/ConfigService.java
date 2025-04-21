package ru.nsu.abramkin.service;

public class ConfigService {
    public boolean validateConfig(int rows, int cols, int food, int winLength) {
        if (rows < 5 || rows > 30 || cols < 5 || cols > 30) return false;
        if (food < 1 || food > 16) return false;
        if (winLength < 2 || winLength > rows * cols) return false;
        return true;
    }
}
