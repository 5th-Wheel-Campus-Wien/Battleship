package at.fifthwheel.battleship;

public class GameSetupP1Controller extends GameSetupControllerBase {

    @Override
    void continueToNextScene() {
        if (!checkShipIndices()) {
            return;
        }

        activePlayer.setBoardCellsPlay(activePlayer.getBoardSetupCells());


        if (sceneManager.getGameState().getIsMultiPlayer()) {
            sceneManager.getGameState().switchActivePlayer();
            sceneManager.switchToP2Setup();
        } else {
            sceneManager.switchToGameplay();
        }
    }
}
