package at.fifthwheel.battleship;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Arrays;

public class Ship {

    private final IntegerProperty length;
    private final IntegerProperty width;

    public IntegerProperty lengthProperty() {
        return length;
    }
    public IntegerProperty widthProperty() {
        return width;
    }

    public int getLength() {
        return length.get();
    }

    public int getWidth() {
        return width.get();
    }

    public int[] boardIndices;

    public Ship(int length, int width) {
        this.length = new SimpleIntegerProperty(length);
        this.width = new SimpleIntegerProperty(width);
        this.boardIndices = new int[length * width];

        Arrays.fill(boardIndices, Integer.MIN_VALUE);
    }

}
