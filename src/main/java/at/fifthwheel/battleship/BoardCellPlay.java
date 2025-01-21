package at.fifthwheel.battleship;

public class BoardCellPlay {


    private final int x;
    private final int y;

    private final Ship ship;

    private boolean isHit = false;

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public Ship getShip(){
        return ship;
    }

    public boolean hasShip(){
        return ship != null;
    }

    public void setHit(){
        isHit = true;
    }

    public boolean isHit(){
        return isHit;
    }

    public BoardCellPlay(int x, int y, Ship ship) {
        this.x = x;
        this.y = y;
        this.ship = ship;
    }

}
