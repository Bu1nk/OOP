package ru.nsu.abramkin.model;

import java.util.Deque;
import java.util.LinkedList;
import javafx.geometry.Point2D;

/**
 * Класс, представляющий змейку в игре. Хранит её тело, текущее направление и управляет движением.
 */
public class Snake {
    private Deque<Point2D> body = new LinkedList<>();
    private Direction currentDirection = Direction.RIGHT;

    /**
     * Создаёт змейку, помещая голову в начальную позицию.
     *
     * @param startPosition начальная позиция головы змейки
     */
    public Snake(Point2D startPosition) {
        body.add(startPosition);
    }

    /**
     * @return позиция головы змейки
     */
    public Point2D getHead() {
        return body.peekFirst();
    }

    /**
     * @return текущее тело змейки в виде очереди точек (от головы к хвосту)
     */
    public Deque<Point2D> getBody() {
        return body;
    }

    /**
     * Устанавливает новое направление, если оно не противоположно текущему.
     *
     * @param direction новое направление движения
     */
    public void setDirection(Direction direction) {
        if (!isOpposite(direction)) {
            currentDirection = direction;
        }
    }

    /**
     * Двигает змейку в текущем направлении. Если {@code grow == true}, хвост не удаляется.
     *
     * @param grow указывает, должна ли змейка вырасти (например, после съедания еды)
     */
    public void move(boolean grow) {
        Point2D newHead = getNextHeadPosition();
        body.addFirst(newHead);
        if (!grow) {
            body.removeLast();
        }
    }

    /**
     * Проверяет, занимает ли змейка указанную точку.
     *
     * @param point точка для проверки
     * @return {@code true}, если точка входит в тело змейки
     */
    public boolean isColliding(Point2D point) {
        return body.contains(point);
    }

    /**
     * Проверяет, является ли переданное направление противоположным текущему.
     *
     * @param newDir новое направление
     * @return {@code true}, если направление противоположное текущему
     */
    private boolean isOpposite(Direction newDir) {
        return (currentDirection == Direction.UP && newDir == Direction.DOWN) ||
                (currentDirection == Direction.DOWN && newDir == Direction.UP) ||
                (currentDirection == Direction.LEFT && newDir == Direction.RIGHT) ||
                (currentDirection == Direction.RIGHT && newDir == Direction.LEFT);
    }

    /**
     * @return координаты следующей позиции головы в зависимости от текущего направления
     */
    Point2D getNextHeadPosition() {
        Point2D head = getHead();
        switch (currentDirection) {
            case UP: return head.add(0, -1);
            case DOWN: return head.add(0, 1);
            case LEFT: return head.add(-1, 0);
            case RIGHT: return head.add(1, 0);
            default: return head;
        }
    }

    /**
     * @return текущее направление движения змейки
     */
    public Direction getDirection() {
        return currentDirection;
    }
}
