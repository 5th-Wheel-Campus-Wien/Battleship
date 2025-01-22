package at.fifthwheel.battleship;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.util.Map;

/**
 * Helper class for placing ships on the game grid.
 * It provides methods to handle ship placement, rotation, and validation of ship positions.
 */
public final class GameSetupHelper {

    private final int CELL_SIZE = GameConfig.getCellSize();
    private final int GRID_SIZE = GameConfig.getBoardSize();

    private final GridPane gameSetupGrid;
    private final Pane shipContainer;

    private Rectangle lastPlacedShipRect;

    private final Map<Rectangle, Ship> shipRectanglesToShipMap;
    private final Map<Rectangle, Point> shipOrigins;

    //private final Map<Rectangle, BoardSetupCell> rectangleBoardCellMap;
    private final Player player;

    /**
     * Constructor to initialize the GameSetupHelper.
     * @param gameSetupGrid        The GridPane containing the game grid for ship placement.
     * @param shipContainer        The Pane where ship rectangles are added and moved.
     * @param shipRectanglesToShipMap A map that links ship rectangles to the respective ships.
     * @param shipOrigins          A map linking ship rectangles to their origin points.
     * @param player               The player whose ships are being placed.
     */
    public GameSetupHelper(GridPane gameSetupGrid, Pane shipContainer, Map<Rectangle, Ship> shipRectanglesToShipMap, Map<Rectangle, Point> shipOrigins, Player player) {
        this.gameSetupGrid = gameSetupGrid;
        this.shipContainer = shipContainer;
        this.shipRectanglesToShipMap = shipRectanglesToShipMap;
        this.shipOrigins = shipOrigins;
        this.player = player;
    }

    /**
     * Checks whether two rectangles are overlapping.
     * The rectangles are expanded by a tolerance value before checking for overlap.
     * @param rect1 The first rectangle to check.
     * @param rect2 The second rectangle to check.
     * @return true if the rectangles overlap; false otherwise.
     */
    public static boolean areRectanglesOverlapping(Rectangle rect1, Rectangle rect2) {
        if (rect1 == rect2) {
            return false;
        }

        double tolerance = GameConfig.getCellSize() / 4.0;

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

    /**
     * Snaps the ship rectangle to the nearest grid cell.
     * It calculates the closest grid cell by comparing the ship's position with the grid cells.
     * @param shipRect The rectangle representing the ship to snap to the grid.
     * @return The index of the grid cell where the ship is placed.
     */
    private int snapToGrid(Rectangle shipRect) {
        double cellAbsoluteX = 0;
        double cellAbsoluteY = 0;

        int gridIndex = 0;
        for (Node node : gameSetupGrid.getChildren()) {
            if (!(node instanceof Rectangle cell)) {
                continue;
            }

            cellAbsoluteX = cell.localToScene(0, 0).getX();
            cellAbsoluteY = cell.localToScene(0, 0).getY();
            double shipAbsoluteX = shipRect.localToScene(0, 0).getX();
            double shipAbsoluteY = shipRect.localToScene(0, 0).getY();

            double distanceX = Math.abs(cellAbsoluteX - shipAbsoluteX);
            double distanceY = Math.abs(cellAbsoluteY - shipAbsoluteY);

            if (distanceX <= ((double) CELL_SIZE / 2 + 1) && distanceY <= ((double) CELL_SIZE / 2 + 1)) {
                break;
            }
            gridIndex++;
        }

        shipRect.relocate(cellAbsoluteX - shipContainer.getLayoutX() - 1, cellAbsoluteY - shipContainer.getLayoutY() - 1);
        lastPlacedShipRect = shipRect;

        return gridIndex;
    }

    /**
     * Validates whether the given ship rectangle can be placed in the current position on the grid.
     * It checks for bounds violations and any overlaps with other ships.
     * @param shipRect The rectangle representing the ship to be validated.
     * @return true if the ship can be placed; false otherwise.
     */
    private boolean validateShipPlacement(Rectangle shipRect) {
        var shipRectPositionScene = new Point((int) shipRect.localToScene(0, 0).getX(), (int) shipRect.localToScene(0, 0).getY());

        if (shipRectPositionScene.x < (gameSetupGrid.getLayoutX() - CELL_SIZE / 4)
                || shipRectPositionScene.y < (gameSetupGrid.getLayoutY() - CELL_SIZE / 4)
                || shipRectPositionScene.x > (gameSetupGrid.getLayoutX() + gameSetupGrid.getWidth() - shipRect.widthProperty().get() + CELL_SIZE / 4)
                || shipRectPositionScene.y > (gameSetupGrid.getLayoutY() + gameSetupGrid.getHeight() - shipRect.heightProperty().get() + CELL_SIZE / 4)) {

            return false;
        }

        for (var node : shipContainer.getChildren()) {
            if (!(node instanceof Rectangle)) {
                continue;
            }
            if (GameSetupHelper.areRectanglesOverlapping(shipRect, (Rectangle) node)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Rotates the last placed ship by swapping its width and length.
     * After rotating, the ship is re-placed on the grid.
     */
    public void rotateShip() {
        if (lastPlacedShipRect == null) {
            return;
        }

        Ship ship = shipRectanglesToShipMap.get(lastPlacedShipRect);
        int origHeight = ship.getLength();
        int origWidth = ship.getWidth();

        ship.setLength(origWidth);
        ship.setWidth(origHeight);

        placeShip(lastPlacedShipRect);
    }

    /**
     * Places a ship at the specified rectangle position on the grid if the placement is valid.
     * If placement is invalid, the ship is moved back to its original position and may be rotated.
     * @param shipRect The rectangle representing the ship to be placed.
     * @return true if the ship was successfully placed; false otherwise.
     */
    public boolean placeShip(Rectangle shipRect) {

        if (validateShipPlacement(shipRect)) {

            int gridPaneIndex = snapToGrid(shipRect);
            setShipIndices(gridPaneIndex);
            return true;

        } else {

            shipRect.relocate(shipOrigins.get(shipRect).x, shipOrigins.get(shipRect).y);
            if (shipRect.getWidth() > shipRect.getHeight()) {
                rotateShip();
            }
            setShipIndices(Integer.MIN_VALUE);
            lastPlacedShipRect = null;
            return false;
        }
    }

    /**
     * Sets the board cell indices for the ship, marking the ship's placement on the player's board.
     * @param firstIndex The index of the first grid cell where the ship is placed.
     */
    private void setShipIndices(int firstIndex) {
        if (lastPlacedShipRect == null) {
            return;
        }

        Ship ship = shipRectanglesToShipMap.get(lastPlacedShipRect);

        ship.resetBoardCellsSetupToShipMapping();

        if (firstIndex < 0) {
            return;
        }

        boolean horizontal = lastPlacedShipRect.getWidth() > lastPlacedShipRect.getHeight();

        int x = firstIndex % GRID_SIZE;
        int y = firstIndex / GRID_SIZE;

        for (int i = 0; i < ship.getBoardCellsSetup().length; i++) {

            ship.getBoardCellsSetup()[i] = player.getBoardCellSetup(x, y);
            player.getBoardCellSetup(x, y).setShip(ship);

            if (horizontal) {
                x++;
            } else {
                y++;
            }
        }

    }
}
