package at.fifthwheel.battleship;

import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class BattleShipApp extends Application {

    private static final Logger logger = LoggerFactory.getLogger(BattleShipApp.class);

    private SceneManager sceneManager;

    @Override
    public void start(Stage stage) throws IOException {
        SceneManager sceneManager = new SceneManager(stage);

        stage.setTitle("Battleship");

        logger.info("Starting Battleship");

        sceneManager.switchToTitleScreen();
    }

    public static void main(String[] args) {
        launch();
    }

}