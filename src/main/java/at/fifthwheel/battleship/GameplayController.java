package at.fifthwheel.battleship;

import javafx.animation.FillTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.effect.ColorAdjust;
import javafx.util.Duration;

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
    @FXML
    private Label winnerLabel;

    private final Map<Rectangle, BoardCellPlay> rectangleBoardCellMapP1 = new HashMap<>();
    private final Map<Rectangle, BoardCellPlay> rectangleBoardCellMapP2 = new HashMap<>();

    private Player activePlayer;
    private Player inactivePlayer;

    private final ColorAdjust grayscale = new ColorAdjust() {{
        setSaturation(-0.5);
        setContrast(0.2);
        setBrightness(-0.2);
    }};

    @FXML
    public void initializeUI() {

        GameState gameState = sceneManager.getGameState();

        activePlayer = sceneManager.getGameState().getActivePlayer();

        createGridCells();

        if (gameState.getIsMultiPlayer() || true) { // TODO Debugging - remove "true"
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
                    shoot(rect);
                });

                rectangleBoardCellMap.put(rect, activePlayer.getBoardCellPlay(col, row));

//                if (rectangleBoardCellMap.get(rect).getShip() != null) {
//                   rect.setFill(Color.GREY);
//                }

                gameGridPane.add(rect, col, row);
            }
        }
    }

    private void positionUIElements() {
        double sceneWidth = gameGridP1.getScene().getWidth();
        double sceneHeight = gameGridP1.getScene().getHeight();
        double gridSize = CELL_SIZE * GRID_SIZE + 5;

        double gameGridLayoutY = sceneHeight / 2 - (gridSize / 2);

        if (sceneManager.getGameState().getIsMultiPlayer() || true) { // TODO remove "true"
            double borderToGridP1X = sceneWidth / 4 - gridSize / 2;

            gameGridP1.setLayoutX(borderToGridP1X);
            gameGridP1.setLayoutY(gameGridLayoutY);

            gameGridP2.setLayoutX(sceneWidth - borderToGridP1X - gridSize - 5);
            gameGridP2.setLayoutY(gameGridLayoutY);

            gameGridP1.setDisable(true);
            gameGridP1.setEffect(grayscale);
        } else {
            gameGridP2.setLayoutX(sceneWidth / 2 - gridSize / 2);
            gameGridP2.setLayoutY(gameGridLayoutY);

            gameGridP1.setDisable(true);
            gameGridP1.setVisible(false);
        }

//        continueButton.setLayoutX(sceneWidth / 2);
//        continueButton.setLayoutY(sceneHeight - sceneHeight / 8);
    }

    private boolean checkForHit(Rectangle rect) {
        Map<Rectangle, BoardCellPlay> rectCellMap = activePlayer.getIsP1() ? rectangleBoardCellMapP2 : rectangleBoardCellMapP1;

        inactivePlayer = sceneManager.getGameState().getPlayer1() == activePlayer ? sceneManager.getGameState().getPlayer2() : sceneManager.getGameState().getPlayer1();

        BoardCellPlay cell = rectCellMap.get(rect);

        if (cell == null || cell.isHit()) {
            return false;
        }

        Color color = Color.DARKGREEN;

        cell.setHit();

        Ship ship = cell.getShip();
        if (ship != null) {
            ship.incrementHitCount();
            color = Color.RED;
        }

        FillTransition fillCell = new FillTransition(Duration.seconds(0.25), rect);
        fillCell.setToValue(color);
        fillCell.setCycleCount(1);
        fillCell.setAutoReverse(false);

        fillCell.play();
        //rect.setFill(color);
        return true;
    }

    private boolean shoot(Rectangle rect) {
        if (checkForHit(rect)) {
            if (checkIfWon()) {
                return true;
            }
            activePlayer = sceneManager.getGameState().switchActivePlayer();
            switchActivePlayerUI();
            return true;
        }
        return false;
    }

    private void switchActivePlayerUI() {
        gameGridP1.setDisable(!gameGridP1.isDisabled());
        gameGridP2.setDisable(!gameGridP2.isDisabled());

        // Set Grayscale to the currently inactive Player's grid
        if (gameGridP1.isDisabled()) {
            gameGridP1.setEffect(grayscale);
            gameGridP2.setEffect(null);
        } else {
            gameGridP2.setEffect(grayscale);
            gameGridP1.setEffect(null);
        }

        if (!sceneManager.getGameState().getIsMultiPlayer() && gameGridP2.isDisabled()) {
            computerShot();
        }
    }

    private void computerShot() {
        while (true) {
            int rectIndex = (int) (Math.random() * GRID_SIZE * GRID_SIZE);

            Rectangle rect;

            int gridIndex = 0;
            for (Node node : gameGridP1.getChildren()) {
                if (!(node instanceof Rectangle)) {
                    continue;
                }
                if (rectIndex == gridIndex) {
                    rect = (Rectangle) node;
                    if (shoot(rect)) {
                        return;
                    }
                }
                gridIndex++;
            }
        }
    }

    private boolean checkIfWon() {
        List<Ship> ships = inactivePlayer.getShips();

        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }

        gameGridP1.setDisable(true);
        gameGridP2.setDisable(true);
        gameGridP1.setEffect(null);
        gameGridP2.setEffect(null);

        continueButton.setDisable(false);
        continueButton.setVisible(true);

        String winnerName = sceneManager.getGameState().getActivePlayer().getName();
        winnerLabel.setText(winnerName + " has won!");
        winnerLabel.setVisible(true);

        return true;
    }

    @FXML
    private void continueToNextScene(ActionEvent actionEvent) {
        sceneManager.switchToEndScreen();
    }
}
