package at.fifthwheel.battleship;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.util.List;

public class ComputerSetupController {

    private SceneManager sceneManager;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @FXML
    private GridPane grid;

    private static final List<Ship> shipsComputer = GameConfig.getShipsComputer();
    private static final int GRID_SIZE = GameConfig.getBoardSize();
    private static final int CELL_SIZE = GameConfig.getCellSize();

    private int[][] shipIDs = new int[GRID_SIZE][GRID_SIZE];

    @FXML
    private void initialize(){

        // Create the Computer Grid (remove later)
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                shipIDs[row][col] = 0;
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
                cell.setFill(Color.LIGHTBLUE);
                cell.setStroke(Color.BLACK);

                grid.add(cell, col, row);
            }
        }

        placeShipsRandom();
        printShipGrid();    // TODO Debugging (remove later)
    }

    // Place ships on the Grid randomly
    private void placeShipsRandom(){
        for (Ship ship : shipsComputer) {
            boolean shipPlaced = false;

            // Look for suitable Start position
            while(!shipPlaced){
                int row = (int) (Math.random() * GRID_SIZE);
                int col = (int) (Math.random() * GRID_SIZE);
                boolean rotated = Math.random() > 0.5;

                shipPlaced = tryPlaceShip(ship, row, col, rotated); // Try to place the Ship at Start position
                System.out.println("Row: " + row + " Col: " + col + " Rotated: " + rotated); // TODO Debugging (remove later)
            }

            System.out.println("Ship placed: " + ship.getLength()); // TODO Debugging (remove later)
        }
    }

    // Validate start Position and place the ship
    private boolean tryPlaceShip(Ship ship, int row, int col, boolean rotated){
        int shipLength = ship.getLength();
        int shipWidth = ship.getWidth();

        if(rotated){
            // Check for Out of Bounds
            if(col + shipLength > GRID_SIZE){
                return false;
            }
            for (int i = 0; i < shipLength; i++) {
                for (int j = 0; j < shipWidth; j++) {
                    // Check for Overlap
                    if(shipIDs[row + j][col + i] != 0){
                        return false;
                    }
                }
            }
            for (int i = 0; i < shipLength; i++) {
                for (int j = 0; j < shipWidth; j++) {
                    // Place ship into array
                    shipIDs[row + j][col + i] = shipLength;
                    ship.getBoardIndices().set(i, new Point(col + i, row + j));
                    int pX = ship.getBoardIndices().get(i).x;
                    int pY = ship.getBoardIndices().get(i).y;
                    System.out.println("x: " + pX + " y: " + pY);
                    // TODO Debugging (remove later)
                    Rectangle cell = getNodeByRowColumnIndex(row + j, col + i);
                    if (cell != null){
                        cell.setFill(Color.GRAY);
                    }
                }
            }
        } else {
            // Check for Out of Bounds
            if(row + shipLength > GRID_SIZE){
                return false;
            }
            for (int i = 0; i < shipLength; i++) {
                for (int j = 0; j < shipWidth; j++) {
                    // Check for Overlap
                    if(shipIDs[row + i][col + j] != 0){
                        return false;
                    }
                }
            }
            for (int i = 0; i < shipLength; i++) {
                for (int j = 0; j < shipWidth; j++) {
                    // Place ship into array
                    shipIDs[row + i][col + j] = shipLength;
                    ship.getBoardIndices().set(i, new Point(col + j, row + i));
                    int pX = ship.getBoardIndices().get(i).x;
                    int pY = ship.getBoardIndices().get(i).y;
                    System.out.println("x: " + pX + " y: " + pY); // TODO Debugging (remove later)
                    // TODO Debugging (remove later)
                    Rectangle cell = getNodeByRowColumnIndex(row + i, col + j);
                    if (cell != null){
                        cell.setFill(Color.GRAY);
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
