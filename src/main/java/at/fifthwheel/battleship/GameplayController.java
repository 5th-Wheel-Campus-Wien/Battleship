package at.fifthwheel.battleship;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameplayController {

    private GameState gameState;

    private final int CELL_SIZE = 35;
    private final int GRID_SIZE = 10;
    @FXML
    private GridPane gameBoardGrid_P1;
    @FXML
    private GridPane gameBoardGrid_P2;

    private ObservableList<Ship> shipsP1;
    private ObservableList<Ship> shipsP2;

    private boolean isMultiplayer;

    private Map<Rectangle, Integer> cellIndex;

    @FXML
    private void initialize() {

        initialize(true);

        if (isMultiplayer) {
            initialize(false);
        }

    }

    private void initialize(boolean isPlayer1) {
        GridPane gameBoardGrid = isPlayer1 ? gameBoardGrid_P1 : gameBoardGrid_P2;
        List<Ship> ships = isPlayer1 ? shipsP1 : shipsP2;

        cellIndex = new HashMap<>();

        // Create grid cells
        int index = 0;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
                cell.setFill(Color.LIGHTBLUE); // Default water color
                cell.setStroke(Color.BLACK);  // Cell border
                cell.setOnMouseClicked(event -> checkForHit(cell));

                gameBoardGrid.add(cell, col, row);
                cellIndex.put(cell, index);
                index++;
            }
        }

    }

    private void checkForHit(Rectangle cell) {
        for (Ship ship : shipsP1) {
            for (int i : ship.boardIndices){
                if (cellIndex.get(cell) == i){
                    cell.setFill(Color.RED);
                }
                else{
                    cell.setFill(Color.GREEN);
                }
            }
        }
    }

}
