module at.fifthwheel.battleship {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.desktop;

    opens at.fifthwheel.battleship to javafx.fxml;
    exports at.fifthwheel.battleship;
}