package at.fifthwheel.battleship;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A configuration class that holds constants and static data related to the game, such as the
 * board size, cell size, and the ships available for both players.
 * This class provides access to the game setup, including ships' configurations for both players.
 */
public final class GameConfig {

    private static final int CELL_SIZE = 35;
    private static final int BOARD_SIZE = 10;

    /**
     * A predefined list of ships for the game, each defined by its length and width.
     */
    private static final List<Ship> SHIPS = List.of(
            new Ship(5, 1),
            new Ship(4, 1),
            new Ship(3, 1),
            new Ship(3, 1),
            new Ship(2, 1)
    );

    private static final List<Ship> shipsP1 = SHIPS.stream()
            .map(ship -> new Ship(ship.getLength(), ship.getWidth()))
            .collect(Collectors.toList());

    private static final List<Ship> shipsP2 = SHIPS.stream()
            .map(ship -> new Ship(ship.getLength(), ship.getWidth()))
            .collect(Collectors.toList());

//    private static final List<Ship> shipsComputer = ships.stream()
//            .map(ship -> new Ship(ship.getLength(), ship.getWidth()))
//            .collect(Collectors.toList());

    public static int getCellSize(){
        return CELL_SIZE;
    }
    public static int getBoardSize(){
        return BOARD_SIZE;
    }

    public static List<Ship> getShipsP1(){
        return shipsP1;
    }
    public static List<Ship> getShipsP2(){
        return shipsP2;
    }
//    public static List<Ship> getShipsComputer(){ return shipsComputer; }

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private GameConfig(){
    }
}
