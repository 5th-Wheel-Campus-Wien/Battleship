package at.fifthwheel.battleship;

import java.util.ArrayList;
import java.util.List;

/**
 * The ComputerShotController class handles the logic for a computer-controlled player in the Battleship game.
 * It manages shooting logic, checks for hits, and determines the game's end state.
 */
public class ComputerShotController {
    private final int[][] shipGrid;
    private final boolean[][] shotsFired;
    private final int gridSize;

    /**
     * Constructs a new ComputerShotController.
     * @param shipGrid A 2D array representing the grid with ships. A value greater than 0 indicates a ship is present.
     * @param gridSize The size of the grid (width and height).
     */
    public ComputerShotController(int[][] shipGrid, int gridSize) {
        this.shipGrid = shipGrid;
        this.gridSize = gridSize;
        this.shotsFired = new boolean[gridSize][gridSize];
    }

    /**
     * Takes a shot at a random position on the grid that has not already been targeted.
     * @return An array containing the row and column of the shot [row, col].
     */
    public int[] takeShot() {
        int row, col;

        do {
            row = (int) (Math.random() * gridSize);
            col = (int) (Math.random() * gridSize);
        } while (shotsFired[row][col]);

        shotsFired[row][col] = true;

        if (shipGrid[row][col] > 0) {
            System.out.println("Hit!");
        } else {
            System.out.println("Opfer");
        }

        return new int[]{row, col};
    }

    /**
     * Checks if a shot at a specific position hits a ship.
     * @param row The row index of the position.
     * @param col The column index of the position.
     * @return {@code true} if the position contains part of a ship; {@code false} otherwise.
     */
    public boolean isHit(int row, int col) {
        return shipGrid[row][col] > 0;
    }

    /**
     * Determines whether the game is over. The game is over when all positions without ships have been targeted.
     * @return {@code true} if the game is over; {@code false} otherwise.
     */
    public boolean isGameOver() {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                if (shipGrid[row][col] == 0 && !shotsFired[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Gets a list of all the positions that have been targeted.
     * @return A list of int arrays, where each array contains the row and column of a shot [row, col].
     */
    public List<int[]> getShotsFired() {
        List<int[]> shots = new ArrayList<>();
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                if (shotsFired[row][col]) {
                    shots.add(new int[]{row, col});
                }
            }
        }
        return shots;
    }
}