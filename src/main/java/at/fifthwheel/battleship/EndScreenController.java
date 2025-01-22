package at.fifthwheel.battleship;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * The controller for the end screen of the game. It manages interactions when the user reaches
 * the end screen, particularly starting a new game.
 */
public class EndScreenController {

    private SceneManager sceneManager;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    /**
     * Called when the user clicks the "New Game" button. It resets the hit count of all ships
     * for both players and switches to the player name selection screen.
     * @param event The event triggered by clicking the button.
     */
    @FXML
    protected void onNewGameButtonClicked(ActionEvent event) {
        for (Ship ship : sceneManager.getGameState().getPlayer1().getShips()){
            ship.resetShips();
            ship.resetBoardCellsSetupToShipMapping();
        }
        for (Ship ship : sceneManager.getGameState().getPlayer2().getShips()){
            ship.resetShips();
            ship.resetBoardCellsSetupToShipMapping();
        }

        sceneManager.switchToPlayerNameSelection();
    }
}
