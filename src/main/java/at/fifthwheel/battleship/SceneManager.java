package at.fifthwheel.battleship;

import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {
    private Stage currentStage;
    private Map<String, Scene> scenes = new HashMap<>();
    private Map<String, Object> globalData = new HashMap<>();

    public SceneManager(Stage currentStage) {
        this.currentStage = currentStage;
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
