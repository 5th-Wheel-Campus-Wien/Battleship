package at.fifthwheel.battleship;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ComputerSetupController {

    private SceneManager sceneManager;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @FXML
    private GridPane grid;

    private int GRID_SIZE = 10;
    private int CELL_SIZE = 35;

    private int[][] shipIDs = new int[GRID_SIZE][GRID_SIZE];

    @FXML
    private void initialize(){

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                shipIDs[row][col] = -1;
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
                cell.setFill(Color.LIGHTBLUE); // Default water color
                cell.setStroke(Color.BLACK);  // Cell border

                grid.add(cell, col, row);
            }
        }
    }

}
