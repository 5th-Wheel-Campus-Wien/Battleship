package at.fifthwheel.battleship;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class PlayerNameController {

    private SceneManager sceneManager;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @FXML
    private RadioButton singlePlayerButton;
    @FXML
    private RadioButton multiPlayerButton;
    @FXML
    private TextField player1NameField;
    @FXML
    private TextField player2NameField;
    @FXML
    private Button startButton;

    private final ToggleGroup modeGroup = new ToggleGroup();

    @FXML
    public void initialize() {
        singlePlayerButton.setToggleGroup(modeGroup);
        multiPlayerButton.setToggleGroup(modeGroup);

        singlePlayerButton.setOnAction(e -> {
            player2NameField.setDisable(true);
            player2NameField.clear();
        });

        multiPlayerButton.setOnAction(e -> {
            player2NameField.setDisable(false);
        });

        startButton.setOnAction(e -> handleStart());
    }

    private void handleStart() {
        String player1Name = player1NameField.getText().trim();
        String player2Name = player2NameField.getText().trim();

        if (multiPlayerButton.isSelected() && player2Name.isEmpty()) {
            showAlert("Error!", "Player 2 Name is required");
            return;
        }

        GameState gameState = sceneManager.getGameState();
        gameState.setIsMultiPlayer(multiPlayerButton.isSelected());
        gameState.getPlayer1().setName(player1Name);

        if (multiPlayerButton.isSelected()) {
            gameState.getPlayer2().setName(player2Name);
        }

        sceneManager.switchToGameSetup();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
