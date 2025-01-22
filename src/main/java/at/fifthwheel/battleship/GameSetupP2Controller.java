package at.fifthwheel.battleship;

/**
 * Controller class for managing the game setup for Player 2 in a Battleship game.
 * Extends the base class {@link GameSetupControllerBase} and implements the logic for continuing to the next scene after Player 2 has completed their ship setup.
 */
public class GameSetupP2Controller extends GameSetupControllerBase {

    /**
     * Continues to the next scene after ensuring that Player 2's ship indices are valid.
     * If the ship indices are valid, the player's board cells are created, and the game progresses.
     * After Player 2 has completed their setup, the active player is switched, and the game moves to the gameplay scene.
     */
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
