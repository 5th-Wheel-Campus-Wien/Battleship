package at.fifthwheel.battleship;

public class Player {

    private final int BOARD_SIZE = GameConfig.getBoardSize();

    private String name;
    private final boolean isP1;

    private final BoardSetupCell[][] boardSetupCells = new BoardSetupCell[BOARD_SIZE][BOARD_SIZE];
    private final BoardCellPlay[][] boardCellsPlay = new BoardCellPlay[BOARD_SIZE][BOARD_SIZE];

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public boolean isP1(){
        return isP1;
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

    public Player(String name, boolean isP1) {
        this.name = name;
        this.isP1 = isP1;

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                this.boardCellsPlay[row][col] = new BoardCellPlay();
                this.boardSetupCells[row][col] = new BoardSetupCell();
            }
        }
    }

}
