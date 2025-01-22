package at.fifthwheel.battleship;
/**
 * Controller class for managing the game setup for Player 1 in a Battleship game.
 * Extends the base class {@link GameSetupControllerBase} and implements the specific logic for continuing to the next scene.
 */
public class GameSetupP1Controller extends GameSetupControllerBase {

    /**
     * Continues to the next scene after ensuring that the player's ship indices are valid.
     * If the ship indices are valid, the player's board cells are created, and the game progresses.
     * If the game is multiplayer, the active player is switched to Player 2, and the setup scene for Player 2 is displayed.
     * Otherwise, the game switches to the computer's screen for a single-player game.
     */
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
