package at.fifthwheel.battleship;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class BoardCell {
    private final BooleanProperty hit = new SimpleBooleanProperty(false);

    public BooleanProperty hitProperty() {
        return hit;
    }

    public boolean isHit() {
        return hit.get();
    }

    public void setHit(boolean hit) {
        this.hit.set(hit);
    }

    private final BooleanProperty hasShip;

    public BooleanProperty hasShipProperty() {
        return hasShip;
    }
    public boolean hasShip() {
        return hasShip.get();
    }
    public void setHasShip(boolean hasShip) {
        this.hasShip.set(hasShip);
    }

    public BoardCell(boolean hasShip) {
        this.hasShip = new SimpleBooleanProperty(hasShip);
    }
}
