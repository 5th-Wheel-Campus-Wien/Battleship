package at.fifthwheel.battleship;

import java.util.ArrayList;
import java.util.List;

public class ComputerShotController {
    private final int[][] shipGrid;
    private final boolean[][] shotsFired;
    private final int gridSize;

    public ComputerShotController(int[][] shipGrid, int gridSize) {
        this.shipGrid = shipGrid;
        this.gridSize = gridSize;
        this.shotsFired = new boolean[gridSize][gridSize];
    }

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

    public boolean isHit(int row, int col) {
        return shipGrid[row][col] > 0;
    }

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