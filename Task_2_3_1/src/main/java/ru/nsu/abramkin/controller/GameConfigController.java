package ru.nsu.abramkin.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ru.nsu.abramkin.Main;

public class GameConfigController {
    @FXML private TextField rowsField;
    @FXML private TextField colsField;
    @FXML private TextField foodField;
    @FXML private TextField winLengthField;

    @FXML
    private void startGame() {
        try {
            int rows = Integer.parseInt(rowsField.getText());
            int cols = Integer.parseInt(colsField.getText());
            int food = Integer.parseInt(foodField.getText());
            int winLength = Integer.parseInt(winLengthField.getText());

            if (rows < 5 || rows > 30 || cols < 5 || cols > 30) {
                System.out.println("Строки и колонки должны быть от 5 до 30.");
                return;
            }

            if (food < 1 || food > 16) {
                System.out.println("Количество еды должно быть от 1 до 16.");
                return;
            }

            if (winLength < 2 || winLength > rows * cols) {
                System.out.println("Длина победы должна быть от 2 до " + (rows * cols));
                return;
            }
            Main.startGameWithConfig(rows, cols, food, winLength);
        } catch (NumberFormatException e) {
            System.out.println("Введите корректные числа.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isValidConfig(int rows, int cols, int food, int winLength) {
        if (rows < 5 || rows > 30 || cols < 5 || cols > 30) return false;
        if (food < 1 || food > 16) return false;
        if (winLength < 2 || winLength > rows * cols) return false;
        return true;
    }
}
