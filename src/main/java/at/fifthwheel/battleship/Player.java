package at.fifthwheel.battleship;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.util.Map;
import java.util.List;

public class Player {
    private String name;
    private final boolean isP1;

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public boolean isP1(){
        return isP1;
    }

    public Player(String name, boolean isP1) {
        this.name = name;
        this.isP1 = isP1;
    }



    public static class PlayerSetupUI {
        private final Pane shipContainer;
        private final GridPane gameSetupGrid;
        private final List<Ship> ships;
        private final Map<Rectangle, Ship> rectangleShipMap;
        private final Map<Rectangle, Point> shipSetupOrigins;
        private final javafx.scene.control.Button rotateShipButton;

        public PlayerSetupUI(Pane shipContainer, GridPane gameSetupGrid, List<Ship> ships, Map<Rectangle, Point> shipOrigins, Map<Rectangle, Ship> rectangleShipMap, javafx.scene.control.Button btnRotateShip) {
            this.shipContainer = shipContainer;
            this.gameSetupGrid = gameSetupGrid;
            this.ships = ships;
            this.shipSetupOrigins = shipOrigins;
            this.rectangleShipMap = rectangleShipMap;
            this.rotateShipButton = btnRotateShip;
        }

        public Pane getShipContainer() {
            return shipContainer;
        }

        public GridPane getGameSetupGrid() {
            return gameSetupGrid;
        }

        public List<Ship> getShips() {
            return ships;
        }

        public Map<Rectangle, Ship> getRectangleShipMap() {
            return rectangleShipMap;
        }

        public Map<Rectangle, Point> getShipSetupOrigins() {
            return shipSetupOrigins;
        }

        public Button getRotateShipButton() {
            return rotateShipButton;
        }
    }
}
