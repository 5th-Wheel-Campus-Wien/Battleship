package at.fifthwheel.battleship;

import java.awt.*;
import java.util.List;

public class Player {

    private final int BOARD_SIZE = GameConfig.getBoardSize();

    private String name;
    private final boolean IS_P1;

    private final BoardSetupCell[][] boardSetupCells = new BoardSetupCell[BOARD_SIZE][BOARD_SIZE];
    private BoardCellPlay[][] boardCellsPlay = new BoardCellPlay[BOARD_SIZE][BOARD_SIZE];

    private final List<Ship> SHIPS;

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public boolean getIsP1(){
        return IS_P1;
    }

    public BoardSetupCell[][] getBoardSetupCells(){
        return boardSetupCells;
    }
    public BoardCellPlay[][] getBoardCellsPlay(){
        return boardCellsPlay;
    }

    public BoardSetupCell getBoardSetupCell(int x, int y){
        return boardSetupCells[x][y];
    }
    public BoardCellPlay getBoardCellPlay(int x, int y){
        return boardCellsPlay[x][y];
    }

    public Point getBoardCellIndex(BoardCellPlay cell){
        for (int i = 0; i < boardCellsPlay.length; i++) {
            for (int j = 0; j < boardCellsPlay[i].length; j++) {
                if (boardCellsPlay[i][j].equals(cell)) { // Use equals for object comparison
                    return new Point(i, j); // Return the indices as a Point
                }
            }
        }
        return null;
    }

    public void setBoardCellsPlay(BoardCell[][] boardCells){
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                this.boardCellsPlay[row][col] = new BoardCellPlay(boardCells[row][col].getHasShip());
            }
        }
    }

    public List<Ship> getShips(){
        return SHIPS;
    }

    public Player(String name, boolean isP1) {
        this.name = name;
        this.IS_P1 = isP1;

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                this.boardSetupCells[row][col] = new BoardSetupCell();
            }
        }

        SHIPS = isP1 ? GameConfig.getShipsP1() : GameConfig.getShipsP2();
    }

}
