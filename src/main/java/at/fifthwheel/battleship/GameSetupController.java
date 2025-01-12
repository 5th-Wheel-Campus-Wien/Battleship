package at.fifthwheel.battleship;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GameSetupController {

    private final int GRID_SIZE = 10;
    private final int CELL_SIZE = 35;
    public Button btnRotateShip_P1;
    public Button btnRotateShip_P2;
    public Button btnContinue;

    @FXML
    private Pane shipContainer_P1;
    @FXML
    private Pane shipContainer_P2;

    @FXML
    private GridPane gameSetupGrid_P1;
    @FXML
    private GridPane gameSetupGrid_P2;

    private ObservableList<Ship> shipsP1;
    private ObservableList<Ship> shipsP2;

    private Map<Rectangle, Ship> rectangleShipMap_P1;
    private Map<Rectangle, Ship> rectangleShipMap_P2;

    private final int shipsMidIndex = 2;
    private final double shipsColumnOffset = 20.0 + CELL_SIZE;
    private final double shipsRowOffset = 20.0;

    private Map<Rectangle, Point> shipOrigins_P1;
    private Map<Rectangle, Point> shipOrigins_P2;

    private Rectangle lastPlacedShipRect;

    private boolean isMultiPlayer = true;
    private boolean p1Done = false;

    private Player p1;
    private Player p2;
    private Player.PlayerUI playerUI_P1;
    private Player.PlayerUI playerUI_P2;
    private Player.PlayerUI activePlayerUI;


    @FXML
    public void initialize() {

        shipsP1 = FXCollections.observableArrayList(
                new Ship(5, 1),
                new Ship(4, 1),
                new Ship(3, 1),
                new Ship(3, 1),
                new Ship(2, 1)
        );
        shipsP2 = FXCollections.observableArrayList(
                new Ship(5, 1),
                new Ship(4, 1),
                new Ship(3, 1),
                new Ship(3, 1),
                new Ship(2, 1)
        );

        rectangleShipMap_P1 = new HashMap<>();
        rectangleShipMap_P2 = new HashMap<>();

        shipOrigins_P1 = new HashMap<>();
        shipOrigins_P2 = new HashMap<>();

        playerUI_P1 = new Player.PlayerUI(shipContainer_P1, gameSetupGrid_P1, shipsP1, shipOrigins_P1, rectangleShipMap_P1, btnRotateShip_P1);
        initialize(true);

        if (isMultiPlayer) {
            playerUI_P2 = new Player.PlayerUI(shipContainer_P2, gameSetupGrid_P2, shipsP2, shipOrigins_P2, rectangleShipMap_P2, btnRotateShip_P2);
            initialize(false);
        }

        btnContinue.setLayoutX(630);
        btnContinue.setLayoutY(650);

    }

    private void initialize(boolean isPlayer1) {

        if (isPlayer1) {
            activePlayerUI = playerUI_P1;
        } else {
            activePlayerUI = playerUI_P2;
        }

        ObservableList<Ship> ships = activePlayerUI.getShips();
        Pane shipContainer = activePlayerUI.getShipContainer();
        Map<Rectangle, Ship> rectangleShipMap = activePlayerUI.getRectangleShipMap();
        Map<Rectangle, Point> shipOrigins = activePlayerUI.getShipOrigins();
        GridPane gameSetupGrid = activePlayerUI.getGameSetupGrid();
        Button btnRotateShip = activePlayerUI.getBtnRotateShip();

        shipContainer.setLayoutX(gameSetupGrid.getLayoutX() - 105);
        shipContainer.setLayoutY(gameSetupGrid.getLayoutY());

        double nextY = 0;

        for (int i = 0; i < ships.size(); i++) {
            Ship ship = ships.get(i);
            Rectangle shipRect = new Rectangle();
            rectangleShipMap.put(shipRect, ship);

            shipRect.widthProperty().set(ship.getWidth() * CELL_SIZE); // Adjusting for grid size
            shipRect.heightProperty().set(ship.getLength() * CELL_SIZE);

            shipRect.setStyle("-fx-fill: gray; -fx-stroke: #7090c0; -fx-stroke-width: 4");

            double x = (i < shipsMidIndex) ? 0 : shipsColumnOffset;

            shipRect.setLayoutX(x); // Set X position based on column
            shipRect.setLayoutY(nextY); // Set Y position for stacking vertically
            nextY += (i == (shipsMidIndex - 1)) ? -nextY : shipRect.getHeight() + shipsRowOffset; // Update position for the next shipRect

            shipContainer.getChildren().add(shipRect); // Add the rectangle to the container

            setDragAndDrop(shipRect);

            shipOrigins.put(shipRect, new Point((int) shipRect.getLayoutX() - 2, (int) shipRect.getLayoutY() - 2));

            if (!isPlayer1) {
                shipRect.setDisable(true);
                btnRotateShip.setDisable(true);
            }

        }

        // Create grid cells
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
                cell.setFill(Color.LIGHTBLUE); // Default water color
                cell.setStroke(Color.BLACK);  // Cell border

                gameSetupGrid.add(cell, col, row);
            }
        }

        btnRotateShip.setLayoutX(gameSetupGrid.getLayoutX() + CELL_SIZE * GRID_SIZE / 2 - CELL_SIZE / 2);
        btnRotateShip.setLayoutY(gameSetupGrid.getLayoutY() + CELL_SIZE * GRID_SIZE + CELL_SIZE);
    }

//    public void handleDragOver(DragEvent dragEvent) {
//    }
//
//    public void handleDragDropped(DragEvent dragEvent) {
//    }
//
//    public void handleDragDetected(MouseEvent mouseEvent) {
//    }

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
                placeShip(shipRect);
            });
        });
    }

    private int snapToGrid(Rectangle shipRect) {
        double cellAbsoluteX = 0;
        double cellAbsoluteY = 0;

        GridPane gameSetupGrid = p1Done ? gameSetupGrid_P2 : gameSetupGrid_P1;
        Pane shipContainer = p1Done ? shipContainer_P2 : shipContainer_P1;

        int gridIndex = 0;
        for (Node node : gameSetupGrid.getChildren()) {
            if (!(node instanceof Rectangle)) {
                continue;
            }
            var cell = (Rectangle) node;

            cellAbsoluteX = cell.localToScene(0, 0).getX();
            cellAbsoluteY = cell.localToScene(0, 0).getY();
            double shipAbsoluteX = shipRect.localToScene(0, 0).getX();
            double shipAbsoluteY = shipRect.localToScene(0, 0).getY();

            double distanceX = Math.abs(cellAbsoluteX - shipAbsoluteX);
            double distanceY = Math.abs(cellAbsoluteY - shipAbsoluteY);

            if (distanceX < ((double) CELL_SIZE / 2 + 1) && distanceY < ((double) CELL_SIZE / 2 + 1)) {
                break;
            }
            gridIndex++;
        }

        shipRect.relocate(cellAbsoluteX - shipContainer.getLayoutX() - 2, cellAbsoluteY - shipContainer.getLayoutY() - 1);
        lastPlacedShipRect = shipRect;

        return gridIndex;
    }

//    private void snapToGrid(Rectangle shipRect) {
//        // Define the snapping grid size (assuming your grid uses equal-sized cells)
//        double cellWidth = gameSetupGrid.getWidth() / GRID_SIZE;  // assuming square grid cells
//        double cellHeight = gameSetupGrid.getHeight() / GRID_SIZE;
//
//        // Get the shipRect's current position
//        double shipX = shipRect.localToScene(shipRect.getLayoutX(), shipRect.getLayoutY()).getX();
//        double shipY = shipRect.localToScene(shipRect.getLayoutX(), shipRect.getLayoutY()).getY();
//
//        // Find the closest grid cell based on shipRect's position
//        int closestCellX = (int) Math.round(shipX / cellWidth);
//        int closestCellY = (int) Math.round(shipY / cellHeight);
//
//        // Make sure the closest cell is within bounds of the grid
//        closestCellX = Math.max(0, Math.min(closestCellX, GRID_SIZE - 1));
//        closestCellY = Math.max(0, Math.min(closestCellY, GRID_SIZE - 1));
//
//        // Calculate the position to snap the shipRect to (the top-left corner of the cell)
//        double snappedX = closestCellX * cellWidth;
//        double snappedY = closestCellY * cellHeight;
//
//        // Relocate the shipRect to the snapped position
//        shipRect.relocate(snappedX, snappedY);
//    }

//    private void snapToGrid(Rectangle shipRect) {
//        // Define grid properties
//        double cellWidth = gameSetupGrid.getWidth() / GRID_SIZE;
//        double cellHeight = gameSetupGrid.getHeight() / GRID_SIZE;
//
//        // Get the shipRect's position relative to the GridPane
//        Bounds gridBounds = gameSetupGrid.localToScene(gameSetupGrid.getLayoutBounds());
//        double shipX = shipRect.localToScene(shipRect.getLayoutX(), shipRect.getLayoutY()).getX();
//        double shipY = shipRect.localToScene(shipRect.getLayoutX(), shipRect.getLayoutY()).getY();
//
//        // Convert shipRect's position to grid-relative coordinates
//        double relativeX = shipX - gridBounds.getMinX();
//        double relativeY = shipY - gridBounds.getMinY();
//
//        // Calculate the closest grid cell
//        int closestCellX = (int) Math.round(relativeX / cellWidth);
//        int closestCellY = (int) Math.round(relativeY / cellHeight);
//
//        // Ensure snapping stays within grid bounds
//        closestCellX = Math.max(0, Math.min(closestCellX, GRID_SIZE - 1));
//        closestCellY = Math.max(0, Math.min(closestCellY, GRID_SIZE - 1));
//
//        // Calculate snapped position (relative to GridPane)
//        double snappedX = closestCellX * cellWidth + gridBounds.getMinX();
//        double snappedY = closestCellY * cellHeight + gridBounds.getMinY();
//
//        // Move the shipRect to the snapped position
//        shipRect.relocate(shipRect.sceneToLocal(snappedX, snappedY).getX(), shipRect.sceneToLocal(snappedX, snappedY).getY());
//    }


    private boolean isValidPlacement(Rectangle ship) {
        var shipPositionScene = new Point((int) ship.localToScene(0, 0).getX(), (int) ship.localToScene(0, 0).getY());

        GridPane gameSetupGrid = p1Done ? gameSetupGrid_P2 : gameSetupGrid_P1;
        Pane shipContainer = p1Done ? shipContainer_P2 : shipContainer_P1;

        if (shipPositionScene.x < (gameSetupGrid.getLayoutX() - CELL_SIZE / 4)
                || shipPositionScene.y < (gameSetupGrid.getLayoutY() - CELL_SIZE / 4)
                || shipPositionScene.x > (gameSetupGrid.getLayoutX() + gameSetupGrid.getWidth() - ship.widthProperty().get() + CELL_SIZE / 4)
                || shipPositionScene.y > (gameSetupGrid.getLayoutY() + gameSetupGrid.getHeight() - ship.heightProperty().get() + CELL_SIZE / 4)) {

            return false;
        }

        for (var node : shipContainer.getChildren()) {
            if (!(node instanceof Rectangle)) {
                continue;
            }
            if (areRectanglesOverlapping(ship, (Rectangle) node)) {
                return false;
            }
        }
        return true;
    }

    public static boolean areRectanglesOverlapping(Rectangle rect1, Rectangle rect2) {
        if (rect1.equals(rect2)) {
            return false;
        }

        double tolerance = 9.0;

        // Get the bounds of both rectangles
        Bounds bounds1 = rect1.getBoundsInParent();
        Bounds bounds2 = rect2.getBoundsInParent();

        // Expand the bounds by the tolerance value
        Bounds expandedBounds1 = new BoundingBox(
                bounds1.getMinX() - tolerance,
                bounds1.getMinY() - tolerance,
                bounds1.getWidth() - tolerance,
                bounds1.getHeight() - tolerance
        );

        Bounds expandedBounds2 = new BoundingBox(
                bounds2.getMinX() - tolerance,
                bounds2.getMinY() - tolerance,
                bounds2.getWidth() - tolerance,
                bounds2.getHeight() - tolerance
        );

        // Check if the smaller bounds intersect
        return expandedBounds1.intersects(expandedBounds2);
    }

    public void rotateShip() {
        if (lastPlacedShipRect == null) {
            return;
        }

        double origHeight = lastPlacedShipRect.getHeight();
        double origWidth = lastPlacedShipRect.getWidth();
        lastPlacedShipRect.setHeight(origWidth);
        lastPlacedShipRect.setWidth(origHeight);

        placeShip(lastPlacedShipRect);
    }

    public boolean placeShip(Rectangle shipRect) {
        if (isValidPlacement(shipRect)) {
            int firstIndex = snapToGrid(shipRect);
            setShipIndices(firstIndex);
            return true;
        } else {

            Map<Rectangle, Point> shipOrigins = p1Done ? shipOrigins_P2 : shipOrigins_P1;

            shipRect.relocate(shipOrigins.get(shipRect).x, shipOrigins.get(shipRect).y);
            if (shipRect.getWidth() > shipRect.getHeight()) {
                double origHeight = shipRect.getHeight();
                double origWidth = shipRect.getWidth();
                shipRect.setHeight(origWidth);
                shipRect.setWidth(origHeight);
            }
            setShipIndices(Integer.MIN_VALUE);
            lastPlacedShipRect = null;
            return false;
        }
    }

    private void setShipIndices(int firstIndex) {
        if (lastPlacedShipRect == null) {
            return;
        }

        Map<Rectangle, Ship> rectShipMap = p1Done ? rectangleShipMap_P2 : rectangleShipMap_P1;

        Ship ship = rectShipMap.get(lastPlacedShipRect);

        if (firstIndex < 0) {
            Arrays.fill(ship.boardIndices, Integer.MIN_VALUE);
            return;
        }

        int indexStep;
        if (lastPlacedShipRect.getWidth() > lastPlacedShipRect.getHeight()) {
            indexStep = 1;
        } else {
            indexStep = 10;
        }

        ship.boardIndices[0] = firstIndex;
        for (int i = 0; i < ship.boardIndices.length; i++) {
            ship.boardIndices[i] = firstIndex + indexStep;
        }
    }

    @FXML
    private void continueToNextStep(ActionEvent event) {
        Map<Rectangle, Ship> rectShipMap = p1Done ? rectangleShipMap_P2 : rectangleShipMap_P1;

        for (Ship ship : rectShipMap.values()) {
            if (Arrays.stream(ship.boardIndices).anyMatch(x -> x < 0)) {
                // TODO: Alert / Label unter Button "alle Schiffe müssen platziert sein" ausgeben ?
                return;
            }
        }

        finishSetup();

        // TODO: continue to next scene (gameplay)

        if (p1Done || isMultiPlayer) {
            try {
                // Load the new FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("gameplay-view.fxml"));
                Parent newRoot = loader.load();

                // Create a new scene
                Scene newScene = new Scene(newRoot);

                // Get the current stage (window)
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Set the new scene on the current stage
                currentStage.setScene(newScene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            p1Done = true;
        }
    }

    private void finishSetup() {
        Pane shipContainer = p1Done ? shipContainer_P2 : shipContainer_P1;

        for (Node node : shipContainer.getChildren()) {
            if (!(node instanceof Rectangle)) {
                continue;
            }
            var ship = (Rectangle) node;
            ship.setVisible(false);
            ship.setDisable(true);
        }

        if (p1Done) {
            for (Node node : shipContainer_P2.getChildren()) {
                if (!(node instanceof Rectangle)) {
                    continue;
                }
                var ship = (Rectangle) node;
                ship.setDisable(false);
            }
        }

        lastPlacedShipRect = null;
    }
}
