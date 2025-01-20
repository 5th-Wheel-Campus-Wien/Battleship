package at.fifthwheel.battleship;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class WinScreenController {

    private SceneManager sceneManager;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @FXML
    private Button continueButton;
    @FXML
    private Label winnerLabel;

    public void initializeUI(){
        String name = sceneManager.getGameState().getActivePlayer().getName();
        winnerLabel.setText(name + " has won!");
    }

    @FXML
    protected void onContinueButtonClick(ActionEvent event) throws IOException {

    }

}