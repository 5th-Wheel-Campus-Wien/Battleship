package at.fifthwheel.battleship;

import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

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
    public BooleanProperty isHitProperty(){
        return isHit;
    }
    public boolean getIsHit(){
        return isHitProperty().get();
    }
    public void setIsHit(boolean isHit){
        this.isHitProperty().set(isHit);
    }

    public boolean hasShip(){
        return ship != null;
    }

    public BoardCellPlay(int x, int y, Ship ship) {
        this.x = x;
        this.y = y;
        this.ship = ship;
        this.isHit = new SimpleBooleanProperty(false);
    }

}
