package at.fifthwheel.battleship;

/**
 * Represents the state of the game, including information about the players and the active player.
 * It also manages the multiplayer status and the switching of the active player during the game.
 */
public class GameState {

    private final Player player1;
    private final Player player2;
    private Player activePlayer;


    private boolean isMultiplayer;

    public boolean getIsMultiPlayer() {
        return isMultiplayer;
    }

    public void setIsMultiPlayer(boolean isMultiPlayer) {
        this.isMultiplayer = isMultiPlayer;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    /**
     * Switches the active player between Player 1 and Player 2.
     * If the active player is Player 1, it switches to Player 2 and vice versa.
     * @return the new active player.
     * @throws IllegalStateException if no active player has been set.
     */
    public Player switchActivePlayer() {
        if (activePlayer == null) {
            throw new IllegalStateException("No active player selected");
        }
        if (activePlayer.equals(player1)) {
            activePlayer = player2;
            return activePlayer;
        }
        activePlayer = player1;
        return activePlayer;
    }

    /**
     * Constructor to initialize the game state with two players.
     * Player 1 is set as the active player by default.
     */
    public GameState() {
        this.player1 = new Player("", true);
        this.player2 = new Player("", false);
        this.activePlayer = this.player1;
    }


}
