package ru.nsu.abramkin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.abramkin.controller.GameController;

public class Main extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        showConfigScene();
    }

    public static void showConfigScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/nsu/abramkin/view/game_config.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setTitle("Настройка Змейки");
        primaryStage.setScene(scene);

        primaryStage.setWidth(400);
        primaryStage.setHeight(300);

        primaryStage.show();
    }

    public static void startGameWithConfig(int rows, int cols, int food, int winLength) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/nsu/abramkin/view/game.fxml"));
        Scene scene = new Scene(loader.load());
        GameController controller = loader.getController();
        controller.initGame(rows, cols, food, winLength);
        primaryStage.setScene(scene);

        int width = cols * 20 + 16;
        int height = rows * 20 + 39 + 100;

        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
