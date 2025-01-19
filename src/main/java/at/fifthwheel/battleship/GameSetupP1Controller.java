package at.fifthwheel.battleship;

public class GameSetupP1Controller extends GameSetupControllerBase {

    @Override
    void continueToNextScene() {
        if (!checkShipIndices()){
            return;
        }

        if (sceneManager.getGameState().getIsMultiPlayer()) {
            sceneManager.getGameState().switchActivePlayer();
            sceneManager.switchToGameplay();
        } else {
            sceneManager.switchToP2Setup();
        }
    }
}
