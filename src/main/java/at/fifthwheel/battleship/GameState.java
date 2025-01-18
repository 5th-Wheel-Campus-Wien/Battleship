package at.fifthwheel.battleship;

public class GameState {

    private final BoardCellForPlay[][] cells;

    private Player player1;
    private Player player2;
    private Player activePlayer;

    private boolean isMultiPlayer;

    public BoardCellForPlay[][] getCells() {
        return cells;
    }

    public boolean getIsMultiPlayer() {
        return isMultiPlayer;
    }
    public void setIsMultiPlayer(boolean isMultiPlayer) {
        this.isMultiPlayer = isMultiPlayer;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public GameState() {
        this.player1 = new Player("", true);
        this.player2 = new Player("", false);

        int boardSize = GameConfig.getBoardSize();
        this.cells = new BoardCellForPlay[boardSize][boardSize];
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {

                cells[row][col] = new BoardCellForPlay();
            }
        }
    }


}
