package ru.nsu.abramkin.model;

import javafx.geometry.Point2D;
import java.util.*;

public class GameModel {
    private final int rows;
    private final int cols;
    private final int maxLength;
    private Snake snake;
    private List<Point2D> food = new ArrayList<>();
    private Random rand = new Random();

    public GameModel(int rows, int cols, int foodCount, int maxLength) {
        this.rows = rows;
        this.cols = cols;
        this.maxLength = maxLength;
        this.snake = new Snake(new Point2D(cols / 2, rows / 2));
        for (int i = 0; i < foodCount; i++) spawnFood();
    }

    public Snake getSnake() {
        return snake;
    }

    public List<Point2D> getFood() {
        return food;
    }

    public boolean update() {
        Point2D nextHead = snake.getNextHeadPosition();
        boolean eat = food.remove(nextHead);
        if (isGameOver(nextHead)) return false;
        snake.move(eat);
        if (eat) spawnFood();
        return true;
    }

    public boolean isVictory() {
        return snake.getBody().size() >= maxLength;
    }

    private boolean isGameOver(Point2D newHead) {
        return newHead.getX() < 0 || newHead.getX() >= cols ||
                newHead.getY() < 0 || newHead.getY() >= rows ||
                snake.isColliding(newHead);
    }

    private void spawnFood() {
        Point2D p;
        do {
            p = new Point2D(rand.nextInt(cols), rand.nextInt(rows));
        } while (snake.isColliding(p) || food.contains(p));
        food.add(p);
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getWinLength() {
        return maxLength;
    }
}
