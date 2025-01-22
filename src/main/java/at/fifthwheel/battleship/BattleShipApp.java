package at.fifthwheel.battleship;

import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * The main class for the Battleship game application.
 * This class initializes and starts the JavaFX application,
 * sets up the primary stage, and switches to the title screen.
 * It also logs the startup process using SLF4J.
 */
public class BattleShipApp extends Application {

    /** Logger instance for logging application events. */
    private static final Logger logger = LoggerFactory.getLogger(BattleShipApp.class);

    /** The scene manager responsible for managing scenes in the application. */
    private SceneManager sceneManager;

    /**
     * Starts the JavaFX application.
     * @param stage The primary stage for this application, provided by the JavaFX framework.
     * @throws IOException If an I/O error occurs during initialization.
     */
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