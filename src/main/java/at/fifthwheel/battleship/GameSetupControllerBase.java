package at.fifthwheel.battleship;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Abstract base controller for setting up the game grid and ship placement in the Battleship game.
 * This controller handles ship creation, drag-and-drop functionality, ship rotation, and grid setup.
 * Subclasses should implement the abstract method to proceed to the next scene after the game setup.
 */
public abstract class GameSetupControllerBase {

    SceneManager sceneManager;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    private final int GRID_SIZE = GameConfig.getBoardSize();
    private final int CELL_SIZE = GameConfig.getCellSize();

    @FXML
    private Pane shipContainer;
    @FXML
    private GridPane gameSetupGrid;
    @FXML
    private Button rotateShipButton;
    @FXML
    private Button continueButton;

    private List<Ship> ships;
    private final Map<Rectangle, Ship> shipRectToShipMap = new HashMap<>();
    private final Map<Rectangle, Point> shipOrigins = new HashMap<>();

    //private final Map<Rectangle, BoardCellSetup> rectangleBoardCellMap = new HashMap<>();

    private GameSetupHelper gameSetupHelper;

    Player player;

    private Rectangle currentlyDraggedShipRect;

    /**
     * Initializes the game setup UI, including creating grid cells, displaying ships, and setting up drag-and-drop.
     * This method is called during the FXML initialization phase.
     */
    @FXML
    public void initializeUI() {

        this.player = sceneManager.getGameState().getActivePlayer();
        this.gameSetupHelper = new GameSetupHelper(gameSetupGrid, shipContainer, shipRectToShipMap, shipOrigins, player);
        ships = player.getShips();

        // Create grid cells
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
                rect.setFill(javafx.scene.paint.Color.LIGHTBLUE); // Default water color
                rect.setStroke(Color.BLACK);  // Cell border

                gameSetupGrid.add(rect, col, row);

//                BoardCellSetup boardCell = player.getBoardCellSetup(row, col);
//                rectangleBoardCellMap.put(rect, boardCell);
            }
        }

        int shipsMidIndex = 2;
        double shipsRowOffset = 20.0;
        double nextY = 0;

        // create rectangles (representing ships), map them to ships and position in ship-container (Pane)
        for (int i = 0; i < ships.size(); i++) {
            Ship ship = ships.get(i);
            Rectangle shipRect = new Rectangle();
            shipRectToShipMap.put(shipRect, ship);

            shipRect.widthProperty().bind(ship.widthProperty().multiply(CELL_SIZE)); // Adjusting for grid size
            shipRect.heightProperty().bind(ship.lengthProperty().multiply(CELL_SIZE));

            shipRect.setStyle("-fx-fill: gray; -fx-stroke: #7090c0; -fx-stroke-width: 3");

            double posX = (i < shipsMidIndex) ? 0 : (20 + CELL_SIZE);

            shipRect.setLayoutX(posX); // Set X position based on column
            shipRect.setLayoutY(nextY); // Set Y position for stacking vertically
            nextY += (i == (shipsMidIndex - 1)) ? -nextY : shipRect.getHeight() + shipsRowOffset; // Update position for the next shipRect

            shipContainer.getChildren().add(shipRect); // Add the rectangle to the container

            setDragAndDrop(shipRect);

            shipOrigins.put(shipRect, new Point((int) shipRect.getLayoutX() - 2, (int) shipRect.getLayoutY() - 2));
        }

        positionUIElements();
    }

    /**
     * Sets up drag-and-drop functionality for the given ship rectangle.
     * Allows the user to click and drag a ship to place it on the game board.
     * @param shipRect The rectangle representing a ship.
     */
    public void setDragAndDrop(Rectangle shipRect) {

        shipRect.setOnMousePressed(event -> {
            // Save initial position and offsets
            double relativeShipX = shipRect.getLayoutX();
            double relativeShipY = shipRect.getLayoutY();
            double mouseOffsetX = event.getSceneX() - relativeShipX;
            double mouseOffsetY = event.getSceneY() - relativeShipY;
            currentlyDraggedShipRect = shipRect;

            shipRect.toFront();
            shipRect.setUserData(new double[]{mouseOffsetX, mouseOffsetY}); // Save offset in user data
        });

        shipRect.setOnMouseDragged(dragEvent -> {
            double[] offsets = (double[]) shipRect.getUserData();
            shipRect.relocate(dragEvent.getSceneX() - offsets[0], dragEvent.getSceneY() - offsets[1]);
        });

        shipRect.setOnMouseReleased(releaseEvent -> {
            if (currentlyDraggedShipRect == shipRect) {
                gameSetupHelper.placeShip(shipRect);    // TODO: Verursacht wahrscheinlich den "nochmal drauf klicken" Bug beim platzieren
            }
        });

    }

    /**
     * Positions the game setup grid and other UI elements on the screen.
     * This method ensures that the elements are properly centered and aligned.
     */
    private void positionUIElements() {

        double sceneWidth = gameSetupGrid.getScene().getWidth();
        double sceneHeight = gameSetupGrid.getScene().getHeight();
        double gridSize = CELL_SIZE * GRID_SIZE;

        gameSetupGrid.setLayoutX(sceneWidth / 2 - gridSize / 2);
        gameSetupGrid.setLayoutY(sceneHeight / 2 - gridSize / 2);

        shipContainer.setLayoutX(gameSetupGrid.getLayoutX() - (CELL_SIZE * 3));
        shipContainer.setLayoutY(gameSetupGrid.getLayoutY());

        rotateShipButton.setLayoutX(sceneWidth / 2 - (rotateShipButton.getWidth() / 2));
        rotateShipButton.setLayoutY(gameSetupGrid.getLayoutY() + gridSize + CELL_SIZE);

        continueButton.setLayoutX(sceneWidth / 2 - continueButton.getWidth() / 2);
        continueButton.setLayoutY(sceneHeight - sceneHeight / 8);
    }

    /**
     * Rotates the currently selected ship in the ship container.
     * This method calls the helper function to perform the rotation logic.
     */
    @FXML
    private void rotateShip() {
        gameSetupHelper.rotateShip();
    }

    /**
     * Checks if all ships have been placed on the grid by the player.
     * Each ship must occupy a valid grid space before proceeding.
     * @return true if all ships are placed correctly, false otherwise.
     */
    boolean checkShipIndices() {
        for (Ship ship : shipRectToShipMap.values()) {
            if (Arrays.stream(ship.getBoardCellsSetup()).anyMatch(Objects::isNull)) {
                // TODO: Alert / Label unter Button "alle Schiffe müssen platziert sein" ausgeben ?
                return false;
            }
        }
        return true;
    }

    /**
     * Abstract method that should be implemented in subclasses to handle the transition to the next scene.
     * This method will be called when the user presses the continue button after completing ship placement.
     */
    @FXML
    abstract void continueToNextScene();

}
