package at.fifthwheel.battleship;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * Controller class for managing the player name input screen.
 * It allows the user to enter names for Player 1 and Player 2,
 * select between single-player or multiplayer modes, and start the game setup.
 */
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

    /**
     * Initializes the controller by setting up the toggle group for the mode selection,
     * defining actions for the radio buttons, and setting the start button's action.
     */
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

    /**
     * Handles the start button action by validating the player names,
     * updating the game state, and transitioning to the Player 1 setup screen.
     */
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
        else{
            gameState.getPlayer2().setName("Computer");
        }

        sceneManager.switchToP1Setup();
    }

    /**
     * Displays an alert with the specified title and message.
     * @param title the title of the alert.
     * @param message the message to be displayed in the alert.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
