package at.fifthwheel.battleship;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Ship {

    private final IntegerProperty length;
    private final IntegerProperty width;

    private int hitCount;
    private boolean isSunk;

    public int getHitCount() {
        return hitCount;
    }

    public void incrementHitCount() {
        if (hitCount == length.get()) {
            isSunk = true;
            return;
        }
            hitCount++;
    }

    public boolean getIsSunk() {
        return isSunk;
    }

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

    private final List<Point> boardIndices = new ArrayList<>();

    public List<Point> getBoardIndices() {
        return boardIndices;
    }

    public Ship(int length, int width) {
        this.length = new SimpleIntegerProperty(length);
        this.width = new SimpleIntegerProperty(width);

        for (int i = 0; i < length * width; i++) {
            this.boardIndices.add(new Point(Integer.MIN_VALUE, Integer.MIN_VALUE));
        }
    }

}
