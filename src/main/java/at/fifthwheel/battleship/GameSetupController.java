package at.fifthwheel.battleship;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class GameSetupController implements ConfigurableUI {

    private SceneManager sceneManager;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    private final int GRID_SIZE = GameConfig.getBoardSize();
    private final int CELL_SIZE = GameConfig.getCellSize();
    public Button rotateShipPlayer1Button;
    public Button rotateShipPlayer2Button;
    public Button continueButton;

    @FXML
    private Pane shipContainerP1;
    @FXML
    private Pane shipContainerP2;

    @FXML
    private GridPane gameSetupGridP1;
    @FXML
    private GridPane gameSetupGridP2;

    private final List<Ship> shipsP1 = GameConfig.getShipsP1();
    private final List<Ship> shipsP2 = GameConfig.getShipsP2();

    private Map<Rectangle, Ship> rectangleShipMapP1;
    private Map<Rectangle, Ship> rectangleShipMapP2;

    private final int shipsMidIndex = 2;
    private final double shipsColumnOffset = 20.0 + CELL_SIZE;
    private final double shipsRowOffset = 20.0;

    private Map<Rectangle, Point> shipOriginsP1;
    private Map<Rectangle, Point> shipOriginsP2;

    private Rectangle lastPlacedShipRect;

    private boolean isMultiplayer;
    private boolean isPlayer1Finished = false;

    private Player.PlayerSetupUI playerUIP1;
    private Player.PlayerSetupUI playerUIP2;
    private Player.PlayerSetupUI activePlayerUI;


    @FXML
    @Override
    public void configureUI(){

        this.isMultiplayer = sceneManager.getGameState().getIsMultiPlayer();

        rectangleShipMapP1 = new HashMap<>();
        rectangleShipMapP2 = new HashMap<>();

        shipOriginsP1 = new HashMap<>();
        shipOriginsP2 = new HashMap<>();

        playerUIP1 = new Player.PlayerSetupUI(shipContainerP1, gameSetupGridP1, shipsP1, shipOriginsP1, rectangleShipMapP1, rotateShipPlayer1Button);
        initializePlayer1();

        if (isMultiplayer) {
            playerUIP2 = new Player.PlayerSetupUI(shipContainerP2, gameSetupGridP2, shipsP2, shipOriginsP2, rectangleShipMapP2, rotateShipPlayer2Button);
            initializePlayer2();
        }
        else{
            centerP1UI();
            rotateShipPlayer2Button.setDisable(true);
            rotateShipPlayer2Button.setVisible(false);
        }

        Stage stage = (Stage) continueButton.getScene().getWindow();
        Scene scene = stage.getScene();
        double sceneWidth = scene.getWidth();
        double sceneHeight = scene.getHeight();

        continueButton.setLayoutX(sceneWidth / 2 - continueButton.getWidth() / 2);
        continueButton.setLayoutY(sceneHeight - sceneHeight / 8);
    }

    private void initializePlayer1(){
        activePlayerUI = playerUIP1;
        initializeGameSetupElements();
    }

    private void initializePlayer2(){
        activePlayerUI = playerUIP2;
        initializeGameSetupElements();
    }

    private void initializeGameSetupElements() {

        List<Ship> ships = activePlayerUI.getShips();
        Pane shipContainer = activePlayerUI.getShipContainer();
        Map<Rectangle, Ship> rectangleShipMap = activePlayerUI.getRectangleShipMap();
        Map<Rectangle, Point> shipOrigins = activePlayerUI.getShipSetupOrigins();
        GridPane gameSetupGrid = activePlayerUI.getGameSetupGrid();
        Button rotateShipButton = activePlayerUI.getRotateShipButton();

        shipContainer.setLayoutX(gameSetupGrid.getLayoutX() - (CELL_SIZE * 3));
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

            if (activePlayerUI.equals(playerUIP2)) {
                shipRect.setDisable(true);
                rotateShipButton.setDisable(true);
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

        rotateShipButton.setLayoutX(gameSetupGrid.getLayoutX() + CELL_SIZE * GRID_SIZE / 2 - rotateShipButton.getWidth() / 2);
        rotateShipButton.setLayoutY(gameSetupGrid.getLayoutY() + CELL_SIZE * GRID_SIZE + CELL_SIZE);
    }

    private void centerP1UI(){
        GridPane gameSetupGrid = playerUIP1.getGameSetupGrid();
        Pane shipContainer = playerUIP1.getShipContainer();
        Button rotateButton = playerUIP1.getRotateShipButton();

        Stage stage = (Stage) gameSetupGrid.getScene().getWindow();
        Scene scene = stage.getScene();
        double sceneWidth = scene.getWidth();
        double sceneHeight = scene.getHeight();

        gameSetupGrid.setLayoutX(sceneWidth / 2 - gameSetupGrid.getWidth() / 2);
        shipContainer.setLayoutX(gameSetupGrid.getLayoutX() - (CELL_SIZE * 3));
        rotateButton.setLayoutX(sceneWidth / 2 - gameSetupGrid.getWidth() / 2);
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
                placeShip(shipRect);
            });
        });
    }

    private int snapToGrid(Rectangle shipRect) {
        double cellAbsoluteX = 0;
        double cellAbsoluteY = 0;

        GridPane gameSetupGrid = isPlayer1Finished ? gameSetupGridP2 : gameSetupGridP1;
        Pane shipContainer = isPlayer1Finished ? shipContainerP2 : shipContainerP1;

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

        GridPane gameSetupGrid = isPlayer1Finished ? gameSetupGridP2 : gameSetupGridP1;
        Pane shipContainer = isPlayer1Finished ? shipContainerP2 : shipContainerP1;

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

            Map<Rectangle, Point> shipOrigins = isPlayer1Finished ? shipOriginsP2 : shipOriginsP1;

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

        Map<Rectangle, Ship> rectShipMap = isPlayer1Finished ? rectangleShipMapP2 : rectangleShipMapP1;

        Ship ship = rectShipMap.get(lastPlacedShipRect);

        if (firstIndex < 0) {
            Arrays.fill(ship.boardIndices, Integer.MIN_VALUE);
            return;
        }

        int indexStep;
        if (lastPlacedShipRect.getWidth() > lastPlacedShipRect.getHeight()) {
            indexStep = 1;
        } else {
            indexStep = GameConfig.getBoardSize();
        }

        ship.boardIndices[0] = firstIndex;
        for (int i = 0; i < ship.boardIndices.length; i++) {
            ship.boardIndices[i] = firstIndex + indexStep;
        }
    }

    @FXML
    private void continueToNextStep(ActionEvent event) throws IOException {
        Map<Rectangle, Ship> rectShipMap = isPlayer1Finished ? rectangleShipMapP2 : rectangleShipMapP1;

        for (Ship ship : rectShipMap.values()) {
            if (Arrays.stream(ship.boardIndices).anyMatch(x -> x < 0)) {
                // TODO: Alert / Label unter Button "alle Schiffe müssen platziert sein" ausgeben ?
                return;
            }
        }

        finishSetup();

        if (isPlayer1Finished || isMultiplayer) {
            sceneManager.switchToGameplay();
        }
        else {
            isPlayer1Finished = true;
        }
    }

    private void finishSetup() {
        Pane shipContainer = isPlayer1Finished ? shipContainerP2 : shipContainerP1;

        for (Node node : shipContainer.getChildren()) {
            if (!(node instanceof Rectangle)) {
                continue;
            }
            var ship = (Rectangle) node;
            ship.setVisible(false);
            ship.setDisable(true);
        }

        if (isPlayer1Finished) {
            for (Node node : shipContainerP2.getChildren()) {
                if (!(node instanceof Rectangle)) {
                    continue;
                }
                var shipRect = (Rectangle) node;
                shipRect.setDisable(false);
            }
        }

        lastPlacedShipRect = null;
    }
}
