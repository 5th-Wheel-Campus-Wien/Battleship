package at.fifthwheel.battleship;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class BoardCellPlay extends BoardCell {

    private final BooleanProperty hit = new SimpleBooleanProperty(false);

    public BooleanProperty hitProperty() {
        return hit;
    }

    public boolean getHit() {
        return hit.get();
    }

    public void setHit(boolean hit) {
        this.hit.set(hit);
    }

    public BoardCellPlay(boolean hasShip){
        this.hasShip.set(hasShip);
    }

}
