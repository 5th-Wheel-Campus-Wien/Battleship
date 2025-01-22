package at.fifthwheel.battleship;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

/**
 * The TitleScreenController class is responsible for handling user interactions on the title screen of the Battleship game.
 * It listens for events triggered by UI elements (e.g., buttons) and switches to the next scene in the game.
 */
public class TitleScreenController {

    private SceneManager sceneManager;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    /**
     * Handles the click event of the Start button on the title screen. When the button is clicked,
     * the application switches to the player name selection screen.
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If an error occurs while switching scenes.
     */
    @FXML
    protected void onStartButtonClick(ActionEvent event) throws IOException {
        sceneManager.switchToPlayerNameSelection();
    }
}