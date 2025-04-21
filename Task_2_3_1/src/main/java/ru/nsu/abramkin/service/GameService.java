package ru.nsu.abramkin.service;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import ru.nsu.abramkin.controller.GameController;
import ru.nsu.abramkin.model.Direction;
import ru.nsu.abramkin.model.GameModel;

public class GameService {
    private Timeline timeline;

    private GameModel model;
    private GameController gameController;

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setDirection(Direction direction) {
        model.getSnake().setDirection(direction);
    }

    public GameModel getModel() {
        return model;
    }

    /**
     * Основной игровой цикл.
     * Обновляет модель, проверяет условия завершения и перерисовывает поле.
     */
    private void gameLoop() {
        boolean alive = model.update();
        gameController.draw();
        if (!alive) {
            gameController.endGame("Game Over!");
        } else if (model.isVictory()) {
            gameController.endGame("You Win!");
        }
    }

    public void stop() {
        timeline.stop();
    }

    public void initGame(int rows, int cols, int foodCount, int winLength) {
        model = new GameModel(rows, cols, foodCount, winLength);
        timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> gameLoop()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
