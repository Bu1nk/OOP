package ru.nsu.abramkin.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ru.nsu.abramkin.Main;

/**
 * Контроллер для экрана конфигурации игры.
 * Обрабатывает ввод пользователя и запускает игру с заданными параметрами.
 */
public class GameConfigController {
    @FXML private TextField rowsField;
    @FXML private TextField colsField;
    @FXML private TextField foodField;
    @FXML private TextField winLengthField;

    /**
     * Обрабатывает нажатие кнопки "Старт".
     * Считывает значения из текстовых полей, проверяет их корректность
     * и запускает игру, если параметры валидны.
     * В случае ошибок выводит сообщения в консоль.
     */
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

    /**
     * Проверяет, являются ли заданные параметры конфигурации допустимыми.
     *
     * @param rows количество строк поля (5–30)
     * @param cols количество столбцов поля (5–30)
     * @param food количество еды (1–16)
     * @param winLength длина змейки для победы (2–rows*cols)
     * @return true, если конфигурация валидна; false — в противном случае
     */
    public static boolean isValidConfig(int rows, int cols, int food, int winLength) {
        if (rows < 5 || rows > 30 || cols < 5 || cols > 30) return false;
        if (food < 1 || food > 16) return false;
        if (winLength < 2 || winLength > rows * cols) return false;
        return true;
    }
}
