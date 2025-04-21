package ru.nsu.abramkin.model;

import javafx.geometry.Point2D;
import java.util.*;

/**
 * Модель игры "Змейка". Управляет логикой игры: движением змейки, генерацией еды,
 * проверкой условий победы и проигрыша.
 */
public class GameModel {
    private final int rows;
    private final int cols;
    private final int maxLength;
    private Snake snake;
    private List<Point2D> food = new ArrayList<>();
    private Random rand = new Random();

    /**
     * Создаёт игровую модель с указанными параметрами.
     *
     * @param rows       количество строк игрового поля
     * @param cols       количество столбцов игрового поля
     * @param foodCount  количество единиц еды, размещаемых на поле
     * @param maxLength  длина змейки, при которой наступает победа
     */
    public GameModel(int rows, int cols, int foodCount, int maxLength) {
        this.rows = rows;
        this.cols = cols;
        this.maxLength = maxLength;
        this.snake = new Snake(new Point2D(cols / 2, rows / 2));
        for (int i = 0; i < foodCount; i++) spawnFood();
    }

    /**
     * @return объект змейки
     */
    public Snake getSnake() {
        return snake;
    }

    /**
     * @return список точек, где расположена еда
     */
    public List<Point2D> getFood() {
        return food;
    }

    /**
     * Обновляет состояние игры: двигает змейку, проверяет столкновения и еду.
     *
     * @return {@code true}, если игра продолжается; {@code false}, если наступил проигрыш
     */
    public boolean update() {
        Point2D nextHead = snake.getNextHeadPosition();
        boolean eat = food.remove(nextHead);
        if (isGameOver(nextHead)) return false;
        snake.move(eat);
        if (eat) spawnFood();
        return true;
    }

    /**
     * @return {@code true}, если длина змейки достигла победного значения
     */
    public boolean isVictory() {
        return snake.getBody().size() >= maxLength;
    }

    /**
     * Проверяет, наступил ли проигрыш (выход за границы или столкновение).
     *
     * @param newHead новая позиция головы змейки
     * @return {@code true}, если наступил проигрыш
     */
    private boolean isGameOver(Point2D newHead) {
        return newHead.getX() < 0 || newHead.getX() >= cols ||
                newHead.getY() < 0 || newHead.getY() >= rows ||
                snake.isColliding(newHead);
    }

    /**
     * Создаёт новую еду в случайной позиции, не занятой змейкой или другой едой.
     */
    private void spawnFood() {
        Point2D p;
        do {
            p = new Point2D(rand.nextInt(cols), rand.nextInt(rows));
        } while (snake.isColliding(p) || food.contains(p));
        food.add(p);
    }

    /**
     * @return количество строк игрового поля
     */
    public int getRows() {
        return rows;
    }

    /**
     * @return количество столбцов игрового поля
     */
    public int getCols() {
        return cols;
    }

    /**
     * @return длина змейки, необходимая для победы
     */
    public int getWinLength() {
        return maxLength;
    }
}
