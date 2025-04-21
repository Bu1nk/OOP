package ru.nsu.abramkin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.abramkin.controller.GameConfigController;
import ru.nsu.abramkin.controller.GameController;
import ru.nsu.abramkin.service.ConfigService;
import ru.nsu.abramkin.service.GameService;

/**
 * Главный класс JavaFX-приложения. Отвечает за запуск и переключение между сценами.
 */
public class Main extends Application {
    private static Stage primaryStage;

    public static final ConfigService configService = new ConfigService();
    public static final GameService gameService = new GameService();

    /**
     * Точка входа в JavaFX-приложение. Загружает экран настроек игры.
     *
     * @param stage основное окно приложения
     * @throws Exception при ошибке загрузки FXML
     */
    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        showConfigScene();
    }

    /**
     * Загружает и отображает сцену настроек игры (game_config.fxml).
     *
     * @throws Exception при ошибке загрузки FXML
     */
    public static void showConfigScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/nsu/abramkin/view/game_config.fxml"));
        Scene scene = new Scene(loader.load());

        primaryStage.setTitle("Настройка Змейки");
        primaryStage.setScene(scene);

        // Установка стандартных размеров окна
        primaryStage.setWidth(400);
        primaryStage.setHeight(300);

        primaryStage.show();
    }

    /**
     * Запускает игру с заданными параметрами, инициализируя контроллер.
     *
     * @param rows       количество строк игрового поля
     * @param cols       количество столбцов игрового поля
     * @param food       количество еды на поле
     * @param winLength  длина змейки, при которой считается победа
     * @throws Exception при ошибке загрузки FXML
     */
    public static void startGameWithConfig(int rows, int cols, int food, int winLength) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/nsu/abramkin/view/game.fxml"));
        Scene scene = new Scene(loader.load());
        GameController controller = loader.getController();
        gameService.setGameController(controller);
        controller.initGame(rows, cols, food, winLength);
        primaryStage.setScene(scene);

        int width = cols * 20 + 16;
        int height = rows * 20 + 39 + 100;

        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
    }

    /**
     * Запускает приложение.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
