package ru.nsu.abramkin.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import ru.nsu.abramkin.model.*;
import javafx.scene.control.Label;
import java.util.ArrayList;

/**
 * Контроллер основного игрового экрана.
 * Управляет игровым циклом, отрисовкой, вводом и завершением игры.
 */
public class GameController {
    @FXML Canvas canvas;
    @FXML Label statusLabel;
    @FXML VBox endOverlay;

    private int rows, cols, foodCount, winLength;

    private final int CELL_SIZE = 20;
    private GameModel model;
    private Timeline timeline;

    /**
     * Инициализирует игру с заданными параметрами.
     * Настраивает модель, запускает игровой цикл и фокус на canvas.
     *
     * @param rows количество строк игрового поля
     * @param cols количество столбцов
     * @param foodCount количество еды на поле
     * @param winLength длина змейки для победы
     */
    public void initGame(int rows, int cols, int foodCount, int winLength) {
        this.rows = rows;
        this.cols = cols;
        this.foodCount = foodCount;
        this.winLength = winLength;

        model = new GameModel(rows, cols, foodCount, winLength);
        timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> gameLoop()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        endOverlay.setVisible(false);
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(this::handleKey);
        statusLabel.setText("");
    }

    /**
     * Обрабатывает нажатие клавиш и меняет направление движения змейки.
     *
     * @param e событие клавиши
     */
    private void handleKey(KeyEvent e) {
        switch (e.getCode()) {
            case UP, W -> model.getSnake().setDirection(Direction.UP);
            case DOWN, S -> model.getSnake().setDirection(Direction.DOWN);
            case LEFT, A -> model.getSnake().setDirection(Direction.LEFT);
            case RIGHT, D -> model.getSnake().setDirection(Direction.RIGHT);
        }
    }

    /**
     * Основной игровой цикл.
     * Обновляет модель, проверяет условия завершения и перерисовывает поле.
     */
    private void gameLoop() {
        boolean alive = model.update();
        draw();
        if (!alive) {
            endGame("Game Over!");
        } else if (model.isVictory()) {
            endGame("You Win!");
        }
    }

    /**
     * Завершает игру, останавливает таймер и показывает сообщение.
     *
     * @param message текст сообщения о завершении
     */
    private void endGame(String message) {
        timeline.stop();
        statusLabel.setText(message);
        endOverlay.setVisible(true);
    }

    /**
     * Перезапускает игру с текущими параметрами.
     */
    @FXML
    void restartGame() {
        initGame(rows, cols, foodCount, winLength);
    }

    /**
     * Отрисовывает текущее состояние поля, змейки и еды.
     */
    private void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        int width = cols * CELL_SIZE;
        int height = rows * CELL_SIZE;
        canvas.setWidth(width);
        canvas.setHeight(height);

        gc.setStroke(Color.LIGHTGRAY);
        for (int x = 0; x <= cols; x++) {
            gc.strokeLine(x * CELL_SIZE, 0, x * CELL_SIZE, canvas.getHeight());
        }
        for (int y = 0; y <= rows; y++) {
            gc.strokeLine(0, y * CELL_SIZE, canvas.getWidth(), y * CELL_SIZE);
        }

        var body = new ArrayList<>(model.getSnake().getBody());

        for (int i = 0; i < body.size(); i++) {
            Point2D part = body.get(i);
            if (i == 0) {
                gc.setFill(Color.DARKGREEN);
            } else if (i == body.size() - 1) {
                gc.setFill(Color.LIGHTGREEN);
            } else {
                gc.setFill(Color.GREEN);
            }
            gc.fillRect(part.getX() * CELL_SIZE, part.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        gc.setFill(Color.RED);
        for (var food : model.getFood()) {
            gc.fillOval(food.getX() * CELL_SIZE, food.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }

    /**
     * Возвращает пользователя на экран настроек игры.
     */
    @FXML
    private void goToSettings() {
        try {
            ru.nsu.abramkin.Main.showConfigScene();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
