package at.fifthwheel.battleship;

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
    public void setActivePlayer(Player player) {
        activePlayer = activePlayer;
    }

    public void switchActivePlayer() {
        if (activePlayer == null) {
            throw new IllegalStateException("No active player selected");
        }
        if (activePlayer.equals(player1)) {
            activePlayer = player2;
            return;
        }
        activePlayer = player1;
    }

    public GameState() {
        this.player1 = new Player("", true);
        this.player2 = new Player("", false);
        this.activePlayer = this.player1;
    }


}
