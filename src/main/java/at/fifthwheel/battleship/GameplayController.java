package at.fifthwheel.battleship;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameplayController {

    private SceneManager sceneManager;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    private GameState gameState;

    private final int CELL_SIZE = GameConfig.getCellSize();
    private final int GRID_SIZE = GameConfig.getBoardSize();

    @FXML
    private GridPane gameBoardGridP1;
    @FXML
    private GridPane gameBoardGridP2;

    private ObservableList<Ship> shipsP1;
    private ObservableList<Ship> shipsP2;

    private boolean isMultiplayer;

    // woops, need 2 of those too
    private Map<Rectangle, Integer> cellIndex;

    @FXML
    public void initializeUI() {


        //GridPane gameBoardGrid = activePlayerUI.;
        //List<Ship> ships = activePlayerUI.getShips();

        cellIndex = new HashMap<>();

        // Create grid cells
        int index = 0;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
                cell.setFill(Color.LIGHTBLUE); // Default water color
                cell.setStroke(Color.BLACK);  // Cell border
                cell.setOnMouseClicked(event -> checkForHit(cell));

                //gameBoardGrid.add(cell, col, row);
                cellIndex.put(cell, index);
                index++;
            }
        }
    }

    private void initializePlayer1(){
        //activePlayerUI = playerUIP1;
        initializeGameSetupElements();
    }

    private void initializePlayer2(){
        //activePlayerUI = playerUIP2;
        initializeGameSetupElements();
    }

    private void initializeGameSetupElements() {

    }

    private void checkForHit(Rectangle cell) {

    }

}
