module org.example.battleship {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens org.example.battleship to javafx.fxml;
    exports org.example.battleship;
}