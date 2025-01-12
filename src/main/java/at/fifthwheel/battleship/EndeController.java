package at.fifthwheel.battleship;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class EndeController extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Battleship");

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Label titleLabel = new Label("Game Ended!");
        titleLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold");

        Button endButton = new Button();
        endButton.setText("Start New Game");
        endButton.setStyle("-fx-font-size: 15px;");

        root.getChildren().addAll(titleLabel, endButton);

        Scene scene = new Scene(root, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }
}
