package at.fifthwheel.battleship;

import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Represents a single cell on the game board during gameplay.
 * Each cell has coordinates (x, y), an optional ship, and a property indicating whether it has been hit.
 */
public class BoardCellPlay {


    private final int x;
    public int getX(){
        return x;
    }

    private final int y;
    public int getY(){
        return y;
    }

    private final Ship ship;
    public Ship getShip(){
        return ship;
    }

    public final BooleanProperty isHit;

    /**
     * Gets the BooleanProperty that tracks whether the cell has been hit.
     * @return The isHit property.
     */
    public BooleanProperty isHitProperty(){
        return isHit;
    }

    /**
     * Gets whether the cell has been hit.
     * @return True if the cell has been hit, otherwise false.
     */
    public boolean getIsHit(){
        return isHitProperty().get();
    }


    public void setIsHit(boolean isHit){
        this.isHitProperty().set(isHit);
    }

    /**
     * Checks if the cell contains a ship.
     * @return True if a ship is present in the cell, otherwise false.
     */
    public boolean hasShip(){
        return ship != null;
    }

    /**
     * Constructs a new BoardCellPlay instance.
     * @param x    The x-coordinate of the cell.
     * @param y    The y-coordinate of the cell.
     * @param ship The ship in this cell, or null if no ship is present.
     */
    public BoardCellPlay(int x, int y, Ship ship) {
        this.x = x;
        this.y = y;
        this.ship = ship;
        this.isHit = new SimpleBooleanProperty(false);
    }

}
