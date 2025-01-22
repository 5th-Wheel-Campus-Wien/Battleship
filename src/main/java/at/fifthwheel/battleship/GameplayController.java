package at.fifthwheel.battleship;

import javafx.animation.FillTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * The controller for the gameplay screen. This class handles the main gameplay logic, including the
 * interactions between players, the game grid, and determining whether a player wins.
 * It also manages switching between active and inactive players, as well as displaying the winner.
 */
public class GameplayController {

    /**
     * Logger instance for logging gameplay actions.
     */
    private static final Logger logger = LoggerFactory.getLogger(GameplayController.class);

    private SceneManager sceneManager;

    /**
     * Sets the SceneManager for the controller.
     * @param sceneManager The SceneManager that manages the scene transitions.
     */
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

    /**
     * The color effect to apply grayscale to the inactive player's grid.
     */
    private final ColorAdjust grayscale = new ColorAdjust() {{
        setSaturation(-0.5);
        setContrast(0.2);
        setBrightness(-0.2);
    }};

    /**
     * Initializes the gameplay UI, sets up the grids for both players, and switches the active player.
     */
    @FXML
    public void initializeUI() {
        logger.info("Initializing Gameplay UI");

        GameState gameState = sceneManager.getGameState();

        activePlayer = sceneManager.getGameState().getActivePlayer();
        createGridCells();

        activePlayer = gameState.switchActivePlayer();
        createGridCells();

        activePlayer = gameState.switchActivePlayer();

        positionUIElements();
    }

    /**
     * Creates the grid cells for the active player's board and sets up event handlers for the cells.
     */
    private void createGridCells() {

        GridPane gameGridPane = activePlayer.isPlayer1() ? gameGridP1 : gameGridP2;
        Map<Rectangle, BoardCellPlay> rectangleBoardCellMap = activePlayer.isPlayer1() ? rectangleBoardCellMapP1 : rectangleBoardCellMapP2;

        // Create grid cells
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
                rect.setFill(Color.LIGHTBLUE); // Default water color
                rect.setStroke(Color.BLACK);  // Cell border

                rectangleBoardCellMap.put(rect, activePlayer.getBoardCellPlay(col, row));

                rect.setOnMouseClicked(event -> {
                    shoot(rect);
                });

                gameGridPane.add(rect, col, row);
            }
        }
    }

    /**
     * Positions the UI elements on the screen based on the size of the game scene.
     */
    private void positionUIElements() {
        double sceneWidth = gameGridP1.getScene().getWidth();
        double sceneHeight = gameGridP1.getScene().getHeight();
        double gridSize = CELL_SIZE * GRID_SIZE + 5;

        double gameGridLayoutY = sceneHeight / 2 - (gridSize / 2);

        double borderToGridP1X = sceneWidth / 4 - gridSize / 2;

        gameGridP1.setLayoutX(borderToGridP1X);
        gameGridP1.setLayoutY(gameGridLayoutY);

        gameGridP2.setLayoutX(sceneWidth - borderToGridP1X - gridSize - 5);
        gameGridP2.setLayoutY(gameGridLayoutY);

        gameGridP1.setDisable(true);
        gameGridP1.setEffect(grayscale);

        if (!sceneManager.getGameState().getIsMultiPlayer()) {
            for (Node node : gameGridP1.getChildren()) {
                if (!(node instanceof Rectangle)) {
                    continue;
                }
                Rectangle rect = (Rectangle) node;
                if (rectangleBoardCellMapP1.get(rect).hasShip()) {
                    rect.setFill(Color.GREY);
                }
            }
        }

    }

    /**
     * Checks if a shot hits any ship, and applies the appropriate visual effect.
     * @param rect The rectangle representing the cell where the shot is fired.
     * @return true if the shot hit a ship, false otherwise.
     */
    private boolean checkForHit(Rectangle rect) {
        Map<Rectangle, BoardCellPlay> rectCellMap = activePlayer.isPlayer1() ? rectangleBoardCellMapP2 : rectangleBoardCellMapP1;

        inactivePlayer = sceneManager.getGameState().getPlayer1() == activePlayer ? sceneManager.getGameState().getPlayer2() : sceneManager.getGameState().getPlayer1();

        BoardCellPlay cell = rectCellMap.get(rect);

        if (cell == null || cell.getIsHit()) {
            return false;
        }

        Color color = Color.DARKGREEN;

        cell.setIsHit(true);

        Ship ship = cell.getShip();
        if (ship != null) {
            ship.incrementHitCount();
            color = Color.RED;

            if (ship.getIsSunk()){
                color = Color.DARKRED;
            }
        }

        FillTransition fillCell = new FillTransition(Duration.seconds(0.5), rect);
        fillCell.setToValue(color);
        fillCell.setCycleCount(1);
        fillCell.setAutoReverse(false);

        fillCell.play();

        return true;
    }

    /**
     * Handles shooting at a specific grid cell and checks if a player wins.
     * @param rect The rectangle representing the cell to shoot at.
     * @return true if the shot was successful, false otherwise.
     */
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

    /**
     * Switches the UI to reflect the active and inactive players' grids.
     */
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

    /**
     * Simulates a shot by the computer opponent.
     */
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

    /**
     * Checks if the inactive player has lost (i.e., all their ships are sunk).
     * @return true if the inactive player has lost, false otherwise.
     */
    private boolean checkIfWon() {
        List<Ship> ships = inactivePlayer.getShips();

        for (Ship ship : ships) {
            if (!ship.getIsSunk()) {
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
    /**
     * Switches to the end screen when the game is over.
     * @param actionEvent The event triggered by clicking the "Continue" button.
     */
    @FXML
    private void continueToNextScene(ActionEvent actionEvent) {
        sceneManager.switchToEndScreen();
    }
}
