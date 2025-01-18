package at.fifthwheel.battleship;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class BattleShipApp extends Application {

    private SceneManager sceneManager;

    @Override
    public void start(Stage stage) throws IOException {
        SceneManager sceneManager = new SceneManager(stage);

        stage.setTitle("Battleship");
        sceneManager.switchToTitleScreen();
    }

    public static void main(String[] args) {
        launch();
    }

}