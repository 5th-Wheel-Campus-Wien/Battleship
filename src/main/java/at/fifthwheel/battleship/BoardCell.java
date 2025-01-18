package at.fifthwheel.battleship;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public abstract class BoardCell {

    final BooleanProperty hasShip = new SimpleBooleanProperty();

    public BooleanProperty hasShipProperty() {
        return hasShip;
    }

    public boolean hasShip() {
        return hasShip.get();
    }

}
