package at.fifthwheel.battleship;

public class GameSetupP2Controller extends GameSetupControllerBase {

    @Override
    void continueToNextScene() {
        if (!checkShipIndices()) {
            return;
        }

        activePlayer.setBoardCellsPlay(activePlayer.getBoardSetupCells());

        sceneManager.getGameState().switchActivePlayer();

        sceneManager.switchToGameplay();
    }

}
