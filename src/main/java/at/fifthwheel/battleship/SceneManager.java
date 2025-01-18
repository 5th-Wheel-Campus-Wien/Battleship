package at.fifthwheel.battleship;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {
    private Stage currentStage;
    private Map<String, Scene> scenes = new HashMap<>();
    private Map<String, Object> globalData = new HashMap<>();

    private GameState gameState = new GameState();

    public GameState getGameState() {
        return gameState;
    }

    public SceneManager(Stage currentStage) {
        this.currentStage = currentStage;
        this.globalData.put("gameState", gameState);
    }

    public void registerScene(String name, Scene scene) {
        scenes.put(name, scene);
    }

    /**
     * Find the corresponding registered .fxml file associated with the name
     * and sets the current Stage to that scene.
     */
    public void switchToScene(String name) {
        Scene scene = scenes.get(name);
        if (scene != null) {
            currentStage.setScene(scene);
            currentStage.show();

            // Get the controller from the scene and call configureUI() if it exists
            if (scene.getRoot() != null) {
                FXMLLoader loader = (FXMLLoader) scene.getRoot().getUserData();
                if (loader != null) {
                    Object controller = loader.getController();
                    if (controller instanceof ConfigurableUI) {
                        // Call setup only after the scene is displayed
                        ((ConfigurableUI) controller).configureUI();
                    }
                }
            }

        } else {
            System.err.println("Scene not found: " + name);
        }
    }

    public void setGlobalData(String key, Object value) {
        globalData.put(key, value);
    }

    public Object getGlobalData(String key) {
        return globalData.get(key);
    }
}
