package ru.nsu.abramkin.model;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameModelTest {
    private GameModel model;
    private final int rows = 10;
    private final int cols = 10;
    private final int foodCount = 3;
    private final int winLength = 5;

    @BeforeEach
    public void setUp() {
        model = new GameModel(rows, cols, foodCount, winLength);
    }

    @Test
    public void testInitialSnakePosition() {
        Point2D expected = new Point2D(cols / 2, rows / 2);
        assertEquals(expected, model.getSnake().getHead());
    }

    @Test
    public void testFoodSpawnedInitially() {
        List<Point2D> food = model.getFood();
        assertEquals(foodCount, food.size());
    }

    @Test
    public void testUpdateMovesSnake() {
        Point2D before = model.getSnake().getHead();
        model.update();
        Point2D after = model.getSnake().getHead();
        assertNotEquals(before, after);
    }

    @Test
    public void testUpdateEatsFood() {
        Snake snake = model.getSnake();
        Point2D next = snake.getNextHeadPosition();
        model.getFood().clear();
        model.getFood().add(next);

        int sizeBefore = snake.getBody().size();
        boolean alive = model.update();
        int sizeAfter = snake.getBody().size();

        assertTrue(alive);
        assertEquals(sizeBefore + 1, sizeAfter);
    }

    @Test
    public void testUpdateGameOverWhenHitsWall() {
        Snake snake = model.getSnake();
        snake.setDirection(Direction.LEFT);

        while (model.update());

        assertFalse(model.update());
    }

    @Test
    public void testVictoryCondition() {
        Snake snake = model.getSnake();
        for (int i = 0; i < winLength - 1; i++) {
            snake.move(true);
        }
        assertTrue(model.isVictory());
    }

    @Test
    public void testGameFieldDimensions() {
        assertEquals(rows, model.getRows());
        assertEquals(cols, model.getCols());
        assertEquals(winLength, model.getWinLength());
    }

    @Test
    public void testFoodNotOnSnake() {
        for (Point2D food : model.getFood()) {
            assertFalse(model.getSnake().getBody().contains(food));
        }
    }
}
