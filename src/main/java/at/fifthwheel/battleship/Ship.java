package at.fifthwheel.battleship;

import java.util.Arrays;

public class Ship {

    private final int length;
    private final int width;

    private int hitCount;

    public void incrementHitCount() {
        if (isSunk()) {
            return;
        }
        hitCount++;
    }

    public void resetHitCount() {
        hitCount = 0;
    }

    public boolean isSunk() {
        return hitCount >= length;
    }


    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    private final BoardCellSetup[] boardCellsSetup;
    private final BoardCellPlay[] boardCellsPlay;

    public BoardCellSetup[] getBoardCellsSetup() {
        return boardCellsSetup;
    }

    public BoardCellPlay[] getBoardCellsPlay() {
        return boardCellsPlay;
    }

    public void resetBoardCellsSetupToShipMapping() {
        for (BoardCellSetup cell : boardCellsSetup) {
            if (cell != null) {
                cell.setShip(null);
            }
        }
        Arrays.fill(boardCellsSetup, null);
    }

    public Ship(int length, int width) {
        this.length = length;
        this.width = width;
        this.boardCellsSetup = new BoardCellSetup[length];
        this.boardCellsPlay = new BoardCellPlay[length];
    }

}
