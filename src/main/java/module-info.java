module org.example.battleship {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.desktop;

    opens org.example.battleship to javafx.fxml;
    exports org.example.battleship;
}