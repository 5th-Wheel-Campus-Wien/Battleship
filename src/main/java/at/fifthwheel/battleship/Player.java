package at.fifthwheel.battleship;

import java.util.List;

/**
 * Represents a player in the Battleship game, containing information such as the player's name,
 * whether the player is Player 1, the setup of the player's board cells, and the list of ships.
 */
public class Player {

    private final int BOARD_SIZE = GameConfig.getBoardSize();

    private String name;
    private final boolean IS_P1;

    private final BoardCellSetup[][] boardCellsSetup = new BoardCellSetup[BOARD_SIZE][BOARD_SIZE]; //TODO: alle Konstanten nach Konvention benennen(?)
    private final BoardCellPlay[][] boardCellsPlay = new BoardCellPlay[BOARD_SIZE][BOARD_SIZE];

    private final List<Ship> ships;

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public boolean isPlayer1(){
        return IS_P1;
    }

    public List<Ship> getShips(){
        return ships;
    }


    public BoardCellSetup[][] getBoardCellsSetup(){
        return boardCellsSetup;
    }
    public BoardCellPlay[][] getBoardCellsPlay(){
        return boardCellsPlay;
    }

    public BoardCellSetup getBoardCellSetup(int x, int y){
        return boardCellsSetup[x][y];
    }
    public BoardCellPlay getBoardCellPlay(int x, int y){
        return boardCellsPlay[x][y];
    }

    /**
     * Creates the playable board cells for this player based on the provided setup board cells.
     * The playable board cells are initialized using the corresponding setup board cells' positions and ship information
     * @param boardCells the 2D array of {@link BoardCellSetup} to use for creating the playable cells.
     */
    public void createBoardCellsPlay(BoardCellSetup[][] boardCells){
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                BoardCellSetup boardCellSetup = boardCells[row][col];
                this.boardCellsPlay[row][col] = new BoardCellPlay(boardCellSetup.getX(), boardCellSetup.getY(), boardCellSetup.getShip());
            }
        }
    }

    /**
     * Constructor to initialize a player with the given name and whether the player is Player 1.
     * It also initializes the board cells for setup and assigns the ships based on the player's role (Player 1 or Player 2).
     * @param name  the name of the player.
     * @param isP1  true if the player is Player 1, false if the player is Player 2.
     */
    public Player(String name, boolean isP1) {
        this.name = name;
        this.IS_P1 = isP1;

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                this.boardCellsSetup[row][col] = new BoardCellSetup(row, col);
            }
        }

        ships = isP1 ? GameConfig.getShipsP1() : GameConfig.getShipsP2();
    }

}
