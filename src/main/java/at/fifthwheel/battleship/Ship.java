package at.fifthwheel.battleship;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public List<Point> boardIndices = new ArrayList<>();

    public Ship(int length, int width) {
        this.length = new SimpleIntegerProperty(length);
        this.width = new SimpleIntegerProperty(width);

        for (int i = 0; i < length * width; i++) {
            this.boardIndices.add(new Point(Integer.MIN_VALUE, Integer.MIN_VALUE));
        }
    }

}
