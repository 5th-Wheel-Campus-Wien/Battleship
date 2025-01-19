package at.fifthwheel.battleship;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GameSetupControllerBase {

    SceneManager sceneManager;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    private final int GRID_SIZE = GameConfig.getBoardSize();
    private final int CELL_SIZE = GameConfig.getCellSize();

    @FXML
    private Pane shipContainer;
    @FXML
    private GridPane gameSetupGrid;
    @FXML
    private Button rotateShipButton;
    @FXML
    private Button continueButton;

    private final List<Ship> ships = GameConfig.getShipsP1();
    private final Map<Rectangle, Ship> shipRectToShipMap = new HashMap<>();
    private final Map<Rectangle, Point> shipOrigins = new HashMap<>();

    //private final Map<Rectangle, BoardSetupCell> rectangleBoardCellMap = new HashMap<>();

    private GameSetupHelper gameSetupHelper;
    private Player activePlayer;


    @FXML
    public void initializeUI() {

        this.activePlayer = sceneManager.getGameState().getActivePlayer();
        this.gameSetupHelper = new GameSetupHelper(gameSetupGrid, shipContainer, rotateShipButton, continueButton, shipRectToShipMap, shipOrigins, activePlayer);

        // Create grid cells
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
                cell.setFill(javafx.scene.paint.Color.LIGHTBLUE); // Default water color
                cell.setStroke(Color.BLACK);  // Cell border

                gameSetupGrid.add(cell, col, row);

                BoardSetupCell boardCell = activePlayer.getBoardSetupCell(row, col);
                //rectangleBoardCellMap.put(cell, boardCell);
            }
        }

        int shipsMidIndex = 2;
        double shipsRowOffset = 20.0;
        double nextY = 0;

        // create rectangles (representing ships), map them to ships and position in ship-container (Pane)
        for (int i = 0; i < ships.size(); i++) {
            Ship ship = ships.get(i);
            Rectangle shipRect = new Rectangle();
            shipRectToShipMap.put(shipRect, ship);

            shipRect.widthProperty().set(ship.getWidth() * CELL_SIZE); // Adjusting for grid size
            shipRect.heightProperty().set(ship.getLength() * CELL_SIZE);

            shipRect.setStyle("-fx-fill: gray; -fx-stroke: #7090c0; -fx-stroke-width: 3");

            double posX = (i < shipsMidIndex) ? 0 : (20 + CELL_SIZE);

            shipRect.setLayoutX(posX); // Set X position based on column
            shipRect.setLayoutY(nextY); // Set Y position for stacking vertically
            nextY += (i == (shipsMidIndex - 1)) ? -nextY : shipRect.getHeight() + shipsRowOffset; // Update position for the next shipRect

            shipContainer.getChildren().add(shipRect); // Add the rectangle to the container

            gameSetupHelper.setDragAndDrop(shipRect);

            shipOrigins.put(shipRect, new Point((int) shipRect.getLayoutX() - 2, (int) shipRect.getLayoutY() - 2));
        }

        gameSetupHelper.positionUIElements();
    }

    @FXML
    private void rotateShip(){
        gameSetupHelper.rotateShip();
    }

    boolean checkShipIndices(){
        for (Ship ship : shipRectToShipMap.values()) {
            if (ship.boardIndices.stream().anyMatch(p -> p.x < 0 || p.y < 0)) {
                // TODO: Alert / Label unter Button "alle Schiffe müssen platziert sein" ausgeben ?
                return false;
            }
        }
        return true;
    }

    @FXML
    abstract void continueToNextScene();

}
