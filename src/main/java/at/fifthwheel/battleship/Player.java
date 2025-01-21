package at.fifthwheel.battleship;

import java.util.List;

public class Player {

    private final int BOARD_SIZE = GameConfig.getBoardSize();

    private String name;
    private final boolean IS_P1;

    private final BoardCellSetup[][] boardCellsSetup = new BoardCellSetup[BOARD_SIZE][BOARD_SIZE]; //TODO: alle Konstanten nach Konvention benennen(?)
    private final BoardCellPlay[][] boardCellsPlay = new BoardCellPlay[BOARD_SIZE][BOARD_SIZE];

    private final List<Ship> SHIPS;

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
        return SHIPS;
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

    public void createBoardCellsPlay(BoardCellSetup[][] boardCells){
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                BoardCellSetup boardCellSetup = boardCells[row][col];
                this.boardCellsPlay[row][col] = new BoardCellPlay(boardCellSetup.getX(), boardCellSetup.getY(), boardCellSetup.getShip());
            }
        }
    }


    public Player(String name, boolean isP1) {
        this.name = name;
        this.IS_P1 = isP1;

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                this.boardCellsSetup[row][col] = new BoardCellSetup(row, col);
            }
        }

        SHIPS = isP1 ? GameConfig.getShipsP1() : GameConfig.getShipsP2();
    }

}
