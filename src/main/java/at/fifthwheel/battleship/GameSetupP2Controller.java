package at.fifthwheel.battleship;

public class GameSetupP2Controller extends GameSetupControllerBase {

    @Override
    void continueToNextScene() {
        if (!checkShipIndices()) {
            return;
        }

        sceneManager.getGameState().switchActivePlayer();
        sceneManager.switchToGameplay();
    }

}
