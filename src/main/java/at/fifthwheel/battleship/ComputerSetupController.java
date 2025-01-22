package at.fifthwheel.battleship;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Controller responsible for setting up the computer's grid in the Battleship game.
 * This class initializes the computer's game board, places ships randomly,
 * and transitions to the gameplay phase.
 */
public class ComputerSetupController {

    private SceneManager sceneManager;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    /** The GridPane representing the computer's game board in the UI. */
    @FXML
    private GridPane grid;

    private static final List<Ship> shipsComputer = GameConfig.getShipsP2();
    private static final int GRID_SIZE = GameConfig.getBoardSize();
    private static final int CELL_SIZE = GameConfig.getCellSize();

    private final int[][] shipIDs = new int[GRID_SIZE][GRID_SIZE];

    /** Map linking UI rectangles to logical board cells. */
    private final Map<Rectangle, BoardCellSetup> rectangleBoardCellSetupMap = new HashMap<>();

    /**
     * Initializes the UI for the computer's game board.
     * Creates a grid of rectangles representing the game board, assigns ships to the board randomly,
     * and transitions to the gameplay phase.
     */
    @FXML
    public void initializeUI() {

        // Create the Computer Grid (remove later)
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                shipIDs[row][col] = 0;
                Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
                rect.setFill(Color.LIGHTBLUE);
                rect.setStroke(Color.BLACK);

                rectangleBoardCellSetupMap.put(rect, sceneManager.getGameState().getPlayer2().getBoardCellSetup(row, col));

                grid.add(rect, col, row);
            }
        }
        placeShipsRandom();

        Player player = sceneManager.getGameState().getPlayer2();
        player.createBoardCellsPlay(player.getBoardCellsSetup());
        sceneManager.switchToGameplay();
    }

    /**
     * Places ships randomly on the grid.
     * Ships are positioned on the grid, ensuring they fit within bounds and do not overlap.
     */
    private void placeShipsRandom() {
        int row = 0;
        int col = 0;
        for (Ship ship : shipsComputer) {
            boolean shipPlaced = false;

            // Look for suitable Start position
            while (!shipPlaced) {
                row = (int) (Math.random() * GRID_SIZE);
                col = (int) (Math.random() * GRID_SIZE);
                boolean rotated = Math.random() > 0.5;

                shipPlaced = tryPlaceShip(ship, row, col, rotated); // Try to place the Ship at Start position
                System.out.println("Row: " + row + " Col: " + col + " Rotated: " + rotated); // TODO Debugging (remove later)
            }

            System.out.println("Ship placed: " + ship.getLength()); // TODO Debugging (remove later)
        }

    }

    /**
     *
     * @param ship
     * @param row
     * @param col
     * @param rotated
     * @return
     */
    // Validate start Position and place the ship
    private boolean tryPlaceShip(Ship ship, int row, int col, boolean rotated) {
        int shipLength = ship.getLength();
        int shipWidth = ship.getWidth();

        if (rotated) {
            // Check for Out of Bounds
            if (col + shipLength > GRID_SIZE) {
                return false;
            }
            for (int i = 0; i < shipLength; i++) {
                for (int j = 0; j < shipWidth; j++) {
                    // Check for Overlap
                    if (shipIDs[row + j][col + i] != 0) {
                        return false;
                    }
                }
            }
            for (int i = 0; i < shipLength; i++) {
                for (int j = 0; j < shipWidth; j++) {
                    // Place ship into array
                    shipIDs[row + j][col + i] = shipLength;
                    Rectangle rect = getNodeByRowColumnIndex(row + j, col + i);
                    if (rect != null) {
                        rect.setFill(Color.GRAY);

                        BoardCellSetup cell = rectangleBoardCellSetupMap.get(rect);
                        cell.setShip(ship);
                        ship.getBoardCellsSetup()[i] = cell;
                    }
                }
            }
        } else {
            // Check for Out of Bounds
            if (row + shipLength > GRID_SIZE) {
                return false;
            }
            for (int i = 0; i < shipLength; i++) {
                for (int j = 0; j < shipWidth; j++) {
                    // Check for Overlap
                    if (shipIDs[row + i][col + j] != 0) {
                        return false;
                    }
                }
            }
            for (int i = 0; i < shipLength; i++) {
                for (int j = 0; j < shipWidth; j++) {
                    // Place ship into array
                    shipIDs[row + i][col + j] = shipLength;
                    Rectangle rect = getNodeByRowColumnIndex(row + i, col + j);
                    if (rect != null) {
                        rect.setFill(Color.GRAY);

                        BoardCellSetup cell = rectangleBoardCellSetupMap.get(rect);
                        cell.setShip(ship);
                        ship.getBoardCellsSetup()[i] = cell;
                    }
                }
            }
        }
        return true;
    }

    private Rectangle getNodeByRowColumnIndex(int y, int x) {
        for (javafx.scene.Node node : grid.getChildren()) {
            if (!(node instanceof Rectangle)) {
                continue;
            }
            if (GridPane.getColumnIndex(node) == x && GridPane.getRowIndex(node) == y) {
                return (Rectangle) node;
            }
        }
        return null;
    }

}
