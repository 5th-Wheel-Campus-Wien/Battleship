package at.fifthwheel.battleship;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Arrays;

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

    public void incrementHitCount() {
        if (getIsSunk()) {
            return;
        }
        if (hitCount++ >= this.getLength()) {
            setIsSunk(true);
            return;
        }
        hitCount++;
    }

    public void resetHitCount() {
        hitCount = 0;
    }

    private final BooleanProperty isSunk;
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

    public Ship(int length, int width) {
        this.length = new SimpleIntegerProperty(length);
        this.width = new SimpleIntegerProperty(width);
        this.isSunk = new SimpleBooleanProperty(false);

        this.boardCellsSetup = new BoardCellSetup[length];
        this.boardCellsPlay = new BoardCellPlay[length];
    }

}
