package at.fifthwheel.battleship;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {
    private Stage currentStage;
    private Map<String, Object> globalData = new HashMap<>();

    private GameState gameState = new GameState();

    public GameState getGameState() {
        return gameState;
    }

    public SceneManager(Stage currentStage) {
        this.currentStage = currentStage;
        this.globalData.put("gameState", gameState);
    }

    /**
     * Find the corresponding registered .fxml file associated with the name
     * and sets the current Stage to that scene.
     */
//    public void switchToScene(String name) {
//        Scene scene = scenes.get(name);
//        if (scene != null) {
//            currentStage.setScene(scene);
//            currentStage.show();
//
//            // Get the controller from the scene and call configureUI() if it exists
//            if (scene.getRoot() != null) {
//                FXMLLoader loader = (FXMLLoader) scene.getRoot().getUserData();
//                if (loader != null) {
//                    Object controller = loader.getController();
//                    if (controller instanceof ConfigurableUI) {
//                        // Call setup only after the scene is displayed
//                        ((ConfigurableUI) controller).configureUI();
//                    }
//                }
//            }
//
//        } else {
//            System.err.println("Scene not found: " + name);
//        }
//    }

    // Scene switching Methods

    public void switchToTitleScreen() {
        try {
            FXMLLoader titleScreenLoader = new FXMLLoader(getClass().getResource("titlescreen-view.fxml"));
            Scene titleScreenScene = new Scene(titleScreenLoader.load());
            TitleScreenController titleScreenController = titleScreenLoader.getController();
            titleScreenController.setSceneManager(this);

            currentStage.setScene(titleScreenScene);
            currentStage.show();
        } catch (IOException e) {
            System.err.println("Scene [titleScreen] failed loading: " + e.getMessage());
            e.printStackTrace(); // Optionally log the stack trace for debugging
        }
    }

    public void switchToPlayerNameSelection() {
        try {
            FXMLLoader playerNameLoader = new FXMLLoader(getClass().getResource("playername-view.fxml"));
            Scene playerNameScene = new Scene(playerNameLoader.load());
            PlayerNameController playerNameController = playerNameLoader.getController();
            playerNameController.setSceneManager(this);

            currentStage.setScene(playerNameScene);
            currentStage.show();
        } catch (IOException e) {
            System.err.println("Scene [playerNameSelection] failed loading: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void switchToGameSetup() {
        try {
            FXMLLoader gameSetupLoader = new FXMLLoader(getClass().getResource("game-setup-view.fxml"));
            Scene gameSetupScene = new Scene(gameSetupLoader.load());
            GameSetupController gameSetupController = gameSetupLoader.getController();
            gameSetupScene.getRoot().setUserData(gameSetupLoader);
            gameSetupController.setSceneManager(this);

            currentStage.setScene(gameSetupScene);
            currentStage.show();
        } catch (IOException e) {
            System.err.println("Scene [gameSetup] failed loading: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void switchToGameplay() {
        try {
            FXMLLoader gameplayLoader = new FXMLLoader(getClass().getResource("gameplay-view.fxml"));
            Scene gameplayScene = new Scene(gameplayLoader.load());
            GameplayController gameplayController = gameplayLoader.getController();
            gameplayController.setSceneManager(this);

            currentStage.setScene(gameplayScene);
            currentStage.show();
        } catch (IOException e) {
            System.err.println("Scene [gameplay] failed loading: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void switchToEndScreen() {
        try {
            FXMLLoader endScreenLoader = new FXMLLoader(getClass().getResource("endscreen-view.fxml"));
            Scene endScreenScene = new Scene(endScreenLoader.load());
            EndScreenController endScreenController = endScreenLoader.getController();
            endScreenController.setSceneManager(this);

            currentStage.setScene(endScreenScene);
            currentStage.show();
        } catch (IOException e) {
            System.err.println("Scene [endScreen] failed loading: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void switchToComputerScreen() {
        try {
            FXMLLoader computerScreenLoader = new FXMLLoader(getClass().getResource("computer-setup-view.fxml"));
            Scene computerScreenScene = new Scene(computerScreenLoader.load());
            ComputerSetupController computerScreenController = computerScreenLoader.getController();
            computerScreenController.setSceneManager(this);

            currentStage.setScene(computerScreenScene);
            currentStage.show();
        } catch (IOException e) {
            System.err.println("Scene [computerScreen] failed loading: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Global Data Manipulation

    public void setGlobalData(String key, Object value) {
        globalData.put(key, value);
    }

    public Object getGlobalData(String key) {
        return globalData.get(key);
    }
}
