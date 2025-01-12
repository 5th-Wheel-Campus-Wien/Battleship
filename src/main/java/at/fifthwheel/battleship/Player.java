package at.fifthwheel.battleship;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.util.Map;

public class Player {
    private final String name;
    private final boolean isP1;

    public String getName(){
        return name;
    }
    public boolean isP1(){
        return isP1;
    }

    public Player(String name, boolean isP1) {
        this.name = name;
        this.isP1 = isP1;
    }




    public static class PlayerUI {
        private final Pane shipContainer;
        private final GridPane gameSetupGrid;
        private final ObservableList<Ship> ships;
        private final Map<Rectangle, Ship> rectangleShipMap;
        private final Map<Rectangle, Point> shipOrigins;
        private final javafx.scene.control.Button btnRotateShip;

        public PlayerUI(Pane shipContainer, GridPane gameSetupGrid, ObservableList<Ship> ships, Map<Rectangle, Point> shipOrigins, Map<Rectangle, Ship> rectangleShipMap, javafx.scene.control.Button btnRotateShip) {
            this.shipContainer = shipContainer;
            this.gameSetupGrid = gameSetupGrid;
            this.ships = ships;
            this.shipOrigins = shipOrigins;
            this.rectangleShipMap = rectangleShipMap;
            this.btnRotateShip = btnRotateShip;
        }

        public Pane getShipContainer() {
            return shipContainer;
        }

        public GridPane getGameSetupGrid() {
            return gameSetupGrid;
        }

        public ObservableList<Ship> getShips() {
            return ships;
        }

        public Map<Rectangle, Ship> getRectangleShipMap() {
            return rectangleShipMap;
        }

        public Map<Rectangle, Point> getShipOrigins() {
            return shipOrigins;
        }

        public Button getBtnRotateShip() {
            return btnRotateShip;
        }
    }
}
