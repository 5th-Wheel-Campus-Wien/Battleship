package at.fifthwheel.battleship;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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

    @FXML
    protected void onContinueButtonClick(ActionEvent event) throws IOException {

    }

    @FXML   // Display Name of Player who won
    public void playerWin(String playerName) {
        winnerLabel.setText(playerName + " has won!");
    }
}