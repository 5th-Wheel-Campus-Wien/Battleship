package at.fifthwheel.battleship;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The SceneManager class is responsible for managing the different scenes in the Battleship game application.
 * It handles loading and switching between scenes, as well as storing global data shared between scenes.
 */
public class SceneManager {
    private static final Logger logger = LoggerFactory.getLogger(SceneManager.class);

    private Stage currentStage;
    private Map<String, Object> globalData = new HashMap<>();

    private final GameState gameState = new GameState();

    public GameState getGameState() {
        return gameState;
    }

    public SceneManager(Stage currentStage) {
        this.currentStage = currentStage;
    }


    // Scene switching Methods

    /**
     * Switches to the Title Screen scene.
     */
    public void switchToTitleScreen() {
        try {
            FXMLLoader titleScreenLoader = new FXMLLoader(getClass().getResource("titlescreen-view.fxml"));
            Scene titleScreenScene = new Scene(titleScreenLoader.load());
            TitleScreenController titleScreenController = titleScreenLoader.getController();
            titleScreenController.setSceneManager(this);

            currentStage.setScene(titleScreenScene);
            currentStage.show();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Switches to the Player Name Selection scene.
     */
    public void switchToPlayerNameSelection() {
        try {
            FXMLLoader playerNameLoader = new FXMLLoader(getClass().getResource("playername-view.fxml"));
            Scene playerNameScene = new Scene(playerNameLoader.load());
            PlayerNameController playerNameController = playerNameLoader.getController();
            playerNameController.setSceneManager(this);

            currentStage.setScene(playerNameScene);
            currentStage.show();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Switches to the Player 1 Setup scene.
     */
    public void switchToP1Setup() {
        try {
            FXMLLoader gameSetupLoader = new FXMLLoader(getClass().getResource("game-setup-P1-view.fxml"));
            Scene gameSetupScene = new Scene(gameSetupLoader.load());
            GameSetupP1Controller gameSetupP1Controller = gameSetupLoader.getController();
            gameSetupP1Controller.setSceneManager(this);

            currentStage.setScene(gameSetupScene);
            gameSetupP1Controller.initializeUI();
            currentStage.show();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Switches to the Player 2 Setup scene.
     */
    public void switchToP2Setup() {
        try {
            FXMLLoader gameSetupLoader = new FXMLLoader(getClass().getResource("game-setup-P2-view.fxml"));
            Scene gameSetupP2Scene = new Scene(gameSetupLoader.load());
            GameSetupP2Controller gameSetupP2Controller = gameSetupLoader.getController();
            gameSetupP2Controller.setSceneManager(this);

            currentStage.setScene(gameSetupP2Scene);
            gameSetupP2Controller.initializeUI();
            currentStage.show();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Switches to the Gameplay scene.
     */
    public void switchToGameplay() {
        try {
            FXMLLoader gameplayLoader = new FXMLLoader(getClass().getResource("gameplay-view.fxml"));
            Scene gameplayScene = new Scene(gameplayLoader.load());
            GameplayController gameplayController = gameplayLoader.getController();
            gameplayController.setSceneManager(this);

            currentStage.setScene(gameplayScene);
            gameplayController.initializeUI();
            currentStage.show();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Switches to the End Screen scene.
     */
    public void switchToEndScreen() {
        try {
            FXMLLoader endScreenLoader = new FXMLLoader(getClass().getResource("endscreen-view.fxml"));
            Scene endScreenScene = new Scene(endScreenLoader.load());
            EndScreenController endScreenController = endScreenLoader.getController();
            endScreenController.setSceneManager(this);

            currentStage.setScene(endScreenScene);
            currentStage.show();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Switches to the Computer Setup screen.
     */
    public void switchToComputerScreen() {
        try {
            FXMLLoader computerScreenLoader = new FXMLLoader(getClass().getResource("computer-setup-view.fxml"));
            Scene computerScreenScene = new Scene(computerScreenLoader.load());
            ComputerSetupController computerScreenController = computerScreenLoader.getController();
            computerScreenController.setSceneManager(this);

            currentStage.setScene(computerScreenScene);
            computerScreenController.initializeUI();
            currentStage.show();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    // Global Data Manipulation

    /**
     * Sets a global data key-value pair.
     * @param key The key to store the value under.
     * @param value The value to store.
     */
    public void setGlobalData(String key, Object value) {
        globalData.put(key, value);
    }

    public Object getGlobalData(String key) {
        return globalData.get(key);
    }
}
