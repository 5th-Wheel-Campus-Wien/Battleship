package at.fifthwheel.battleship;

public class GameSetupP1Controller extends GameSetupControllerBase {

    @Override
    void continueToNextScene() {
        if (!checkShipIndices()) {
            return;
        }

        player.createBoardCellsPlay(player.getBoardCellsSetup());

        if (sceneManager.getGameState().getIsMultiPlayer()) {
            sceneManager.getGameState().switchActivePlayer();
            sceneManager.switchToP2Setup();
        } else {
            sceneManager.switchToComputerScreen();
        }
    }
}
