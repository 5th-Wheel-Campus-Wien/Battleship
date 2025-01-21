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

    public Player switchActivePlayer() {
        if (activePlayer == null) {
            throw new IllegalStateException("No active player selected");
        }
        if (activePlayer.equals(player1)) {
            activePlayer = player2;
            System.out.println("Active player switched - isP1 = " + activePlayer.isPlayer1());
            return activePlayer;
        }
        activePlayer = player1;
        System.out.println("Active player switched - isP1 = " + activePlayer.isPlayer1());
        return activePlayer;
    }

    public GameState() {
        this.player1 = new Player("", true);
        this.player2 = new Player("", false);
        this.activePlayer = this.player1;
    }


}
