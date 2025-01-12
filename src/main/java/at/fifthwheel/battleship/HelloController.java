package at.fifthwheel.battleship;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HelloController {


    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void onBeginButtonClick(ActionEvent event) {
        try {
            // Load the game setup scene
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("game-setup-view.fxml"));
            Scene gameSetupScene = new Scene(fxmlLoader.load(), 1280, 720);

            // Switch to the game setup scene
            stage.setScene(gameSetupScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
