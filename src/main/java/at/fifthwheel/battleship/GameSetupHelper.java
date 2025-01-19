package at.fifthwheel.battleship;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.util.Map;


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


    public GameSetupHelper(GridPane gameSetupGrid, Pane shipContainer, Map<Rectangle, Ship> shipRectanglesToShipMap, Map<Rectangle, Point> shipOrigins, Player player) {
        this.gameSetupGrid = gameSetupGrid;
        this.shipContainer = shipContainer;
        this.shipRectanglesToShipMap = shipRectanglesToShipMap;
        this.shipOrigins = shipOrigins;
        this.player = player;
    }


    public static boolean areRectanglesOverlapping(Rectangle rect1, Rectangle rect2) {
        if (rect1.equals(rect2)) {
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

    private boolean isValidPlacement(Rectangle shipRect) {
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


    public void rotateShip() {
        if (lastPlacedShipRect == null) {
            return;
        }

        double origHeight = lastPlacedShipRect.getHeight();
        double origWidth = lastPlacedShipRect.getWidth();
        lastPlacedShipRect.setHeight(origWidth);
        lastPlacedShipRect.setWidth(origHeight);

        // remove old indices from boardCells
        Ship ship = shipRectanglesToShipMap.get(lastPlacedShipRect);
        for (Point p : ship.getBoardIndices()) {
            player.getBoardSetupCell(p.x, p.y).setHasShip(false);
        }

        placeShip(lastPlacedShipRect);
    }

    public boolean placeShip(Rectangle shipRect) {

        if (isValidPlacement(shipRect)) {
            int gridPaneRectIndex = snapToGrid(shipRect);
            setShipIndices(gridPaneRectIndex);
            return true;
        } else {

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

        Ship ship = shipRectanglesToShipMap.get(lastPlacedShipRect);

        if (firstIndex < 0) {
            for (Point p : ship.getBoardIndices()) {
                player.getBoardSetupCell(p.x, p.y).setHasShip(false);
                p.x = Integer.MIN_VALUE;
                p.y = Integer.MIN_VALUE;
            }
            return;
        }

        boolean horizontal = lastPlacedShipRect.getWidth() > lastPlacedShipRect.getHeight();

        int x = firstIndex % GRID_SIZE;
        int y = firstIndex / GRID_SIZE;

        ship.getBoardIndices().set(0, new Point(x, y));
        player.getBoardSetupCell(x, y).setHasShip(true);

        for (int i = 1; i < ship.getBoardIndices().size(); i++) {
            if (horizontal) {
                ship.getBoardIndices().set(i, new Point(++x, y));
            } else {
                ship.getBoardIndices().set(i, new Point(x, ++y));
            }
            player.getBoardSetupCell(x, y).setHasShip(true);
        }

    }
}
