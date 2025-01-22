package at.fifthwheel.battleship;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Arrays;

/**
 * The Ship class represents a ship in the Battleship game. It handles the ship's size (length and width),
 * its hit count, whether it is sunk, and its corresponding cells on the board.
 */
public class Ship {

    private final IntegerProperty length;
    public IntegerProperty lengthProperty() {
        return length;
    }
    public int getLength() {
        return length.get();
    }
    public void setLength(int length) {
        this.length.set(length);
    }

    private final IntegerProperty width;
    public IntegerProperty widthProperty() {
        return width;
    }
    public int getWidth() {
        return width.get();
    }
    public void setWidth(int width) {
        this.width.set(width);
    }

    private int hitCount;

    private final BoardCellSetup[] boardCellsSetup;
    private final BoardCellPlay[] boardCellsPlay;

    public BoardCellSetup[] getBoardCellsSetup() {
        return boardCellsSetup;
    }

    public BoardCellPlay[] getBoardCellsPlay() {
        return boardCellsPlay;
    }

    /**
     * Increments the hit count of the ship. If the ship has been hit enough times (equal to its length),
     * it will be marked as sunk.
     */
    public void incrementHitCount() {
        if (getIsSunk()) {
            return;
        }
        if (++hitCount >= this.getLength()) {
            setIsSunk(true);
        }
    }

    public void resetShips() {
        hitCount = 0;
        setIsSunk(false);
    }

    /** Indicates whether the ship is sunk. */
    private final BooleanProperty isSunk;

    /**
     * Returns the BooleanProperty representing whether the ship is sunk.
     * @return The sunk status property.
     */
    public BooleanProperty isSunkProperty() {
        return isSunk;
    }
    public boolean getIsSunk() {
        return isSunkProperty().get();
    }
    private void setIsSunk(boolean isSunk) {
        this.isSunkProperty().set(isSunk);
    }

    public void resetBoardCellsSetupToShipMapping() {
        for (BoardCellSetup cell : boardCellsSetup) {
            if (cell != null) {
                cell.setShip(null);
            }
        }
        Arrays.fill(boardCellsSetup, null);
    }
    /**
     *
     * Constructs a new Ship with the specified length and width.
     * @param length The length of the ship.
     * @param width The width of the ship.
     */
    public Ship(int length, int width) {
        this.length = new SimpleIntegerProperty(length);
        this.width = new SimpleIntegerProperty(width);
        this.isSunk = new SimpleBooleanProperty(false);

        this.boardCellsSetup = new BoardCellSetup[length];
        this.boardCellsPlay = new BoardCellPlay[length];
    }

}
