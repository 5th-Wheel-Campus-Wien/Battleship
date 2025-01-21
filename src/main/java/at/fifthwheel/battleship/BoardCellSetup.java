package at.fifthwheel.battleship;

public class BoardCellSetup {


    private final int x;
    private final int y;

    private Ship ship;

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public Ship getShip(){
        return ship;
    }
    public void setShip(Ship ship){
        this.ship = ship;
    }

    public BoardCellSetup(int x, int y) {
        this.x = x;
        this.y = y;
    }



}
