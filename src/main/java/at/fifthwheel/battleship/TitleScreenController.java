package at.fifthwheel.battleship;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class TitleScreenController {
    @FXML
    private Button startButton;

    private SceneManager sceneManager;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @FXML
    protected void onStartButtonClick(ActionEvent event) throws IOException {
        sceneManager.switchToPlayerNameSelection();
    }
}