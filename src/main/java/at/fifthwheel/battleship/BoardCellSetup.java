package at.fifthwheel.battleship;

public class BoardCellSetup {

    /**
     * Represents a single cell on the game board during the setup phase.
     * Each cell has fixed coordinates (x, y) and can optionally hold a ship.
     */
    private final int x;
    private final int y;

    private Ship ship;

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    /**
     * Gets the ship assigned to this cell.
     * @return The ship in this cell, or null if no ship is present.
     */
    public Ship getShip(){
        return ship;
    }

    /**
     * Sets the ship for this cell.
     * @param ship The ship to assign to this cell.
     */
    public void setShip(Ship ship){
        this.ship = ship;
    }

    /**
     * Constructs a new BoardCellSetup instance with the specified coordinates.
     * @param x The x-coordinate of the cell.
     * @param y The y-coordinate of the cell.
     */
    public BoardCellSetup(int x, int y) {
        this.x = x;
        this.y = y;
    }



}
