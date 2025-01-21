package at.fifthwheel.battleship;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComputerSetupController {

    private SceneManager sceneManager;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @FXML
    private GridPane grid;

    private static final List<Ship> shipsComputer = GameConfig.getShipsP2();
    private static final int GRID_SIZE = GameConfig.getBoardSize();
    private static final int CELL_SIZE = GameConfig.getCellSize();

    private final int[][] shipIDs = new int[GRID_SIZE][GRID_SIZE];

    private final Map<Rectangle, BoardCellSetup> rectangleBoardCellSetupMap = new HashMap<>();

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

        printShipGrid();    // TODO Debugging (remove later)
    }

    // Place ships on the Grid randomly
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
//                    ship.getBoardCellsSetup()[i].set(i, new Point(col + i, row + j));
//                    int pX = ship.getBoardCellsSetup()[i].getX();
//                    int pY = ship.getBoardCellsSetup()[i].getY();
//                    System.out.println("x: " + pX + " y: " + pY);
                    // TODO Debugging (remove later)
                    Rectangle cell = getNodeByRowColumnIndex(row + j, col + i);
                    if (cell != null) {
                        cell.setFill(Color.GRAY);

                        BoardCellSetup cellSetup = rectangleBoardCellSetupMap.get(cell);
                        cellSetup.setShip(ship);
                        ship.getBoardCellsSetup()[i] = cellSetup;
                        ship.getBoardCellsSetup()[i].setShip(ship);
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
//                    ship.getBoardCellsSetup().set(i, new Point(col + j, row + i));
//                    int pX = ship.getBoardCellsSetup()[i].getX();
//                    int pY = ship.getBoardCellsSetup()[i].getY();
//                    System.out.println("x: " + pX + " y: " + pY); // TODO Debugging (remove later)
                    // TODO Debugging (remove later)
                    Rectangle cell = getNodeByRowColumnIndex(row + i, col + j);
                    if (cell != null) {
                        cell.setFill(Color.GRAY);

                        BoardCellSetup cellSetup = rectangleBoardCellSetupMap.get(cell);
                        cellSetup.setShip(ship);
                        ship.getBoardCellsSetup()[i] = cellSetup;
                        ship.getBoardCellsSetup()[i].setShip(ship);
                    }
                }
            }
        }
        return true;
    }

    // TODO Debugging: print Grid of placed Ships
    private void printShipGrid() {
        for (int[] row : shipIDs) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    // TODO Debugging: Get current Grid Cell
    private Rectangle getNodeByRowColumnIndex(int row, int col) {
        for (javafx.scene.Node node : grid.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return (Rectangle) node;
            }
        }
        return null;
    }

}
