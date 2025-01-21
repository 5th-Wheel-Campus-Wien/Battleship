package at.fifthwheel.battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class GameConfig {

    private static final int CELL_SIZE = 35;
    private static final int BOARD_SIZE = 10;

    private static final List<Ship> ships = List.of(
//            new Ship(5, 1),
//            new Ship(4, 1),
//            new Ship(3, 1),
            new Ship(3, 1),
            new Ship(2, 1)
    );

    private static final List<Ship> shipsP1 = ships.stream()
            .map(ship -> new Ship(ship.getLength(), ship.getWidth()))
            .collect(Collectors.toList());

    private static final List<Ship> shipsP2 = ships.stream()
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

    private GameConfig(){
    }
}
