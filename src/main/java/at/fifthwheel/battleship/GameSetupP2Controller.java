package at.fifthwheel.battleship;

import java.util.List;

public class GameSetupP2Controller extends GameSetupControllerBase {

    @Override
    void continueToNextScene() {
        if (!checkShipIndices()) {
            return;
        }

        player.setBoardCellsPlay(player.getBoardSetupCells());

        sceneManager.getGameState().switchActivePlayer();

        sceneManager.switchToGameplay();
    }

}
