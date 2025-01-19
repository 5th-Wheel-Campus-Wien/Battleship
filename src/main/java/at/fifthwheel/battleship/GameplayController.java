package at.fifthwheel.battleship;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;

public class GameplayController {

    private SceneManager sceneManager;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    private final int CELL_SIZE = GameConfig.getCellSize();
    private final int GRID_SIZE = GameConfig.getBoardSize();

    @FXML
    private GridPane gameGridP1;
    @FXML
    private GridPane gameGridP2;

    private final Map<Rectangle, BoardCellPlay> rectangleBoardCellMapP1 = new HashMap<>();
    private final Map<Rectangle, BoardCellPlay> rectangleBoardCellMapP2 = new HashMap<>();

    private Player activePlayer;

    @FXML
    public void initializeUI() {

        GameState gameState = sceneManager.getGameState();

        activePlayer = sceneManager.getGameState().getActivePlayer();

        initializeUIP1();

        if (gameState.getIsMultiPlayer()) {
            activePlayer = gameState.switchActivePlayer();

            initializeUIP2();

            activePlayer = gameState.switchActivePlayer();
        }
        positionUIElements();
    }

    private void initializeUIP1() {
        createGridCells(rectangleBoardCellMapP1);
    }

    private void initializeUIP2() {
        createGridCells(rectangleBoardCellMapP2);
    }

    private void createGridCells(Map<Rectangle, BoardCellPlay> rectangleBoardCellMap) {

        GridPane gameGridPane = activePlayer.isP1() ? gameGridP1 : gameGridP2;

        // Create grid cells
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
                rect.setFill(Color.LIGHTBLUE); // Default water color
                rect.setStroke(Color.BLACK);  // Cell border
                rect.setOnMouseClicked(event -> checkForHit(rect));

                rectangleBoardCellMap.put(rect, activePlayer.getBoardCellPlay(col, row));

                gameGridPane.add(rect, col, row);
            }
        }
    }

    private void positionUIElements() {
        double sceneWidth = gameGridP1.getScene().getWidth();
        double sceneHeight = gameGridP1.getScene().getHeight();
        double gridSize = CELL_SIZE * GRID_SIZE + 5;

        double gameGridLayoutY = sceneHeight / 2 - (gridSize / 2);

        if (sceneManager.getGameState().getIsMultiPlayer()) {
            double borderToGridP1X = sceneWidth / 4 - gridSize / 2;

            gameGridP1.setLayoutX(borderToGridP1X);
            gameGridP1.setLayoutY(gameGridLayoutY);

            gameGridP2.setLayoutX(sceneWidth - borderToGridP1X - gridSize - 5);
            gameGridP2.setLayoutY(gameGridLayoutY);
        }
        else{
            gameGridP1.setLayoutX(sceneWidth / 2 - gridSize / 2);
            gameGridP1.setLayoutY(gameGridLayoutY);

            gameGridP2.setDisable(true);
            gameGridP2.setVisible(false);
        }
    }

    private void checkForHit(Rectangle cell) {

    }

}
