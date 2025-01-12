package at.fifthwheel.battleship;

public class GameBoard {
    private final BoardCell[][] cells;

    public GameBoard() {
        cells = new BoardCell[10][10];
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {

                cells[row][col] = new BoardCell(false);  // initialize each cell
            }
        }
    }

    // methods for placing ships, marking hits/misses, etc.
}
