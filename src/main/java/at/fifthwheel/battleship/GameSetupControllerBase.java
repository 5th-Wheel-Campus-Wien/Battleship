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

    private List<Ship> ships;
    private final Map<Rectangle, Ship> shipRectToShipMap = new HashMap<>();
    private final Map<Rectangle, Point> shipOrigins = new HashMap<>();

    //private final Map<Rectangle, BoardSetupCell> rectangleBoardCellMap = new HashMap<>();

    private GameSetupHelper gameSetupHelper;

    Player activePlayer;


    @FXML
    public void initializeUI() {

        this.activePlayer = sceneManager.getGameState().getActivePlayer();
        this.gameSetupHelper = new GameSetupHelper(gameSetupGrid, shipContainer, shipRectToShipMap, shipOrigins, activePlayer);
        ships = activePlayer.getIsP1() ? GameConfig.getShipsP1() : GameConfig.getShipsP2();

        // Create grid cells
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
                rect.setFill(javafx.scene.paint.Color.LIGHTBLUE); // Default water color
                rect.setStroke(Color.BLACK);  // Cell border

                gameSetupGrid.add(rect, col, row);

                //BoardSetupCell boardCell = activePlayer.getBoardSetupCell(row, col);
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

            setDragAndDrop(shipRect);

            shipOrigins.put(shipRect, new Point((int) shipRect.getLayoutX() - 2, (int) shipRect.getLayoutY() - 2));
        }

        positionUIElements();
    }

    public void setDragAndDrop(Rectangle shipRect) {
        shipRect.setOnMousePressed(event -> {
            // Save the initial position of the shipRect when dragging starts (relative to parent Pane)
            double relativeShipX = shipRect.getLayoutX();
            double relativeShipY = shipRect.getLayoutY();
            double mouseOffsetX = event.getSceneX() - relativeShipX;
            double mouseOffsetY = event.getSceneY() - relativeShipY;

            // Set shipRect to front so it can be dragged in front of the game board
            shipRect.toFront();

            // Set up dragging behavior
            shipRect.setOnMouseDragged(dragEvent -> {
                shipRect.relocate(dragEvent.getSceneX() - mouseOffsetX, dragEvent.getSceneY() - mouseOffsetY);
            });

            // Handle mouse release to place the shipRect
            shipRect.setOnMouseReleased(releaseEvent -> {
                gameSetupHelper.placeShip(shipRect);    // TODO: Verursacht wahrscheinlich den "nochmal drauf klicken" Bug beim platzieren
            });
        });
    }

    private void positionUIElements() {

        double sceneWidth = gameSetupGrid.getScene().getWidth();
        double sceneHeight = gameSetupGrid.getScene().getHeight();
        double gridSize = CELL_SIZE * GRID_SIZE;

        gameSetupGrid.setLayoutX(sceneWidth / 2 - gridSize / 2);
        gameSetupGrid.setLayoutY(sceneHeight / 2 - gridSize / 2);

        shipContainer.setLayoutX(gameSetupGrid.getLayoutX() - (CELL_SIZE * 3));
        shipContainer.setLayoutY(gameSetupGrid.getLayoutY());

        rotateShipButton.setLayoutX(sceneWidth / 2 - (rotateShipButton.getWidth() / 2));
        rotateShipButton.setLayoutY(gameSetupGrid.getLayoutY() + gridSize + CELL_SIZE);

        continueButton.setLayoutX(sceneWidth / 2 - continueButton.getWidth() / 2);
        continueButton.setLayoutY(sceneHeight - sceneHeight / 8);
    }

    @FXML
    private void rotateShip(){
        gameSetupHelper.rotateShip();
    }

    boolean checkShipIndices(){
        for (Ship ship : shipRectToShipMap.values()) {
            if (ship.getBoardIndices().stream().anyMatch(p -> p.x < 0 || p.y < 0)) {
                // TODO: Alert / Label unter Button "alle Schiffe müssen platziert sein" ausgeben ?
                System.out.println("Ship Length: " + ship.getLength() + ", Indices: " + ship.getBoardIndices() + " is not placed!"); // TODO Debugging (remove later)
                return false;
            }
        }
        return true;
    }

    @FXML
    abstract void continueToNextScene();

}
