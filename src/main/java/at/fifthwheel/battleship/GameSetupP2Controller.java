package at.fifthwheel.battleship;

public class GameSetupP2Controller extends GameSetupControllerBase {

    @Override
    void continueToNextScene() {
        if (!checkShipIndices()) {
            return;
        }

        player.createBoardCellsPlay(player.getBoardCellsSetup());

        sceneManager.getGameState().switchActivePlayer();

        sceneManager.switchToGameplay();
    }

}
