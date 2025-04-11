package ru.nsu.abramkin.model;

import java.util.Deque;
import java.util.LinkedList;
import javafx.geometry.Point2D;

public class Snake {
    private Deque<Point2D> body = new LinkedList<>();
    private Direction currentDirection = Direction.RIGHT;

    public Snake(Point2D startPosition) {
        body.add(startPosition);
    }

    public Point2D getHead() {
        return body.peekFirst();
    }

    public Deque<Point2D> getBody() {
        return body;
    }

    public void setDirection(Direction direction) {
        if (!isOpposite(direction)) {
            currentDirection = direction;
        }
    }

    public void move(boolean grow) {
        Point2D newHead = getNextHeadPosition();
        body.addFirst(newHead);
        if (!grow) {
            body.removeLast();
        }
    }

    public boolean isColliding(Point2D point) {
        return body.contains(point);
    }

    private boolean isOpposite(Direction newDir) {
        return (currentDirection == Direction.UP && newDir == Direction.DOWN) ||
                (currentDirection == Direction.DOWN && newDir == Direction.UP) ||
                (currentDirection == Direction.LEFT && newDir == Direction.RIGHT) ||
                (currentDirection == Direction.RIGHT && newDir == Direction.LEFT);
    }

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

    public Direction getDirection() {
        return currentDirection;
    }
}
