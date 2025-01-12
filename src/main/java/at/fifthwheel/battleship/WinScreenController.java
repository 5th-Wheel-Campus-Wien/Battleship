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
    @FXML
    private Button continueButton;
    @FXML
    private Label winnerLabel;

    @FXML
    protected void onContinueButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("titlescreen-view.fxml"));
        Parent gameView = loader.load();

        // Get the current stage
        Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        // Set the new scene
        currentStage.setScene(new Scene(gameView));
    }

    @FXML   // Display
    public void playerWin(String playerName) {
        winnerLabel.setText(playerName + " has won!");
    }
}