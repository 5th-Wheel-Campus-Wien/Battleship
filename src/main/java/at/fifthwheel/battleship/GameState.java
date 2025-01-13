package at.fifthwheel.battleship;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    private final int CELL_SIZE = 35;
    private final int BOARD_SIZE = 10;

    private final List<Ship> shipsP1;
    private final List<Ship> shipsP2;
    private final BoardCell[][] cells;

    private Player player1;
    private Player player2;
    private Player activePlayer;

    private boolean isMultiPlayer;

    public List<Ship> getShipsP1() {
        return shipsP1;
    }
    public List<Ship> getShipsP2() {
        return shipsP2;
    }

    public BoardCell[][] getCells() {
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
        // Initialize with empty ship list or predefined ships
        this.shipsP1 = new ArrayList<>();
        this.shipsP2 = new ArrayList<>();

        cells = new BoardCell[10][10];
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {

                cells[row][col] = new BoardCell(false);  // initialize each cell
            }
        }
    }


}
