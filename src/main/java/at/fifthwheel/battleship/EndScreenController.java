package at.fifthwheel.battleship;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class EndScreenController {

    private SceneManager sceneManager;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @FXML
    protected void onNewGameButtonClicked(ActionEvent event) {
        for (Ship ship : sceneManager.getGameState().getPlayer1().getShips()){
            ship.resetHitCount();
        }
        for (Ship ship : sceneManager.getGameState().getPlayer2().getShips()){
            ship.resetHitCount();
        }
        sceneManager.switchToPlayerNameSelection();
    }
}
