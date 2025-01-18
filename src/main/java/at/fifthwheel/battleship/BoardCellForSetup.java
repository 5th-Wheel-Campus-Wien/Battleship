package at.fifthwheel.battleship;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class BoardCellForSetup extends BoardCell {

    public void setHasShip(boolean hasShip) {
        this.hasShip.set(hasShip);
    }

}
