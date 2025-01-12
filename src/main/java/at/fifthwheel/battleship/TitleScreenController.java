package org.example.battleship;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class TitleScreenController {
    @FXML
    private Button startButton;

    @FXML
    protected void onStartButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("winscreen-view.fxml"));
        Parent gameView = loader.load();

        // Get the current stage
        Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        // Set the new scene
        currentStage.setScene(new Scene(gameView));
    }
}