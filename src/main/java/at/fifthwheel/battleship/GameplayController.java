package at.fifthwheel.battleship;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
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
    @FXML
    private Button continueButton;

    private final Map<Rectangle, BoardCellPlay> rectangleBoardCellMapP1 = new HashMap<>();
    private final Map<Rectangle, BoardCellPlay> rectangleBoardCellMapP2 = new HashMap<>();

    private Player activePlayer;

    @FXML
    public void initializeUI() {

        GameState gameState = sceneManager.getGameState();

        activePlayer = sceneManager.getGameState().getActivePlayer();

        createGridCells();

        if (gameState.getIsMultiPlayer()) {
            activePlayer = gameState.switchActivePlayer();

            createGridCells();

            activePlayer = gameState.switchActivePlayer();
        }
        positionUIElements();
    }

    private void createGridCells() {

        System.out.println("active player: " + activePlayer.getName());

        GridPane gameGridPane = activePlayer.getIsP1() ? gameGridP1 : gameGridP2;
        Map<Rectangle, BoardCellPlay> rectangleBoardCellMap = activePlayer.getIsP1() ? rectangleBoardCellMapP1 : rectangleBoardCellMapP2;

        // Create grid cells
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
                rect.setFill(Color.LIGHTBLUE); // Default water color
                rect.setStroke(Color.BLACK);  // Cell border

                rect.setOnMouseClicked(event -> {
                    if (checkForHit(rect)) {
                        System.out.println("active player clicked: " + activePlayer.getName());
                        checkIfWon(rect);
                        sceneManager.getGameState().switchActivePlayer();
                        switchActivePlayerUI();
                    }
                });

                rectangleBoardCellMap.put(rect, activePlayer.getBoardCellPlay(col, row));

                BoardCellPlay cell = activePlayer.getBoardCellPlay(col, row);
                System.out.println("cell has ship: " + cell.hasShip);

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
        } else {
            gameGridP1.setLayoutX(sceneWidth / 2 - gridSize / 2);
            gameGridP1.setLayoutY(gameGridLayoutY);

            gameGridP2.setDisable(true);
            gameGridP2.setVisible(false);
        }

        continueButton.setLayoutX(sceneWidth / 2);
        continueButton.setLayoutY(sceneHeight - sceneHeight / 8);
    }

    private boolean checkForHit(Rectangle rect) {
        Map<Rectangle, BoardCellPlay> rectCellMap = activePlayer.getIsP1() ? rectangleBoardCellMapP1 : rectangleBoardCellMapP2;

        BoardCellPlay cell = rectCellMap.get(rect);

        if (cell == null || cell.getHit()) {
            return false;
        }

        cell.setHit(true);

        Point point = activePlayer.getBoardCellIndex(cell);

        Color color = Color.DARKGREEN;

        List<Ship> ships = activePlayer.getShips();
        for (Ship ship : ships) {
            for (Point p : ship.getBoardIndices()) {
                if (p == point) {
                    ship.incrementHitCount();
                    color = Color.RED;
                }
            }
        }

        rect.setFill(color);
        System.out.println("active player checkhit: " + activePlayer.getName());
        return true;
    }

    private void switchActivePlayerUI() {
        gameGridP1.setDisable(activePlayer.getIsP1());
        gameGridP2.setDisable(activePlayer.getIsP1());
    }

    private void checkIfWon(Rectangle rect){
        List<Ship> ships = activePlayer.getShips();

        for (Ship ship : ships) {
            if (!ship.getIsSunk()){
                return;
            }
        }

        gameGridP1.setDisable(true);
        gameGridP2.setDisable(true);

        continueButton.setDisable(false);
        continueButton.setVisible(true);
    }

    @FXML
    private void continueToNextScene(ActionEvent actionEvent) {
        sceneManager.switchToWinScreen();
    }
}
