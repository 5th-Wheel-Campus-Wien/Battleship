package at.fifthwheel.battleship;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.*;


public class PlayerNameController extends Application {

    private SceneManager sceneManager;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @Override
    public void start(Stage primaryStage) {
       primaryStage.setTitle("Battleship");

       VBox root = new VBox(10);
       root.setAlignment(Pos.CENTER);
       root.setPadding(new Insets(20));

       Label titleLabel = new Label("Welcome to Battleship");
       titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold");

       ToggleGroup modeGroup = new ToggleGroup();
       RadioButton singlePlayerButton = new RadioButton("Single Player");
       RadioButton multiPlayerButton = new RadioButton("Multi Player");
       singlePlayerButton.setToggleGroup(modeGroup);
       multiPlayerButton.setToggleGroup(modeGroup);
       singlePlayerButton.setSelected(true);

       TextField player1NameField = new TextField();
       player1NameField.setPromptText("Player 1");
       TextField player2NameField = new TextField();
       player2NameField.setPromptText("Player 2");
       player2NameField.setDisable(true);

       singlePlayerButton.setOnAction(e -> {
           player2NameField.setDisable(true);
           player2NameField.clear();
       });
       multiPlayerButton.setOnAction(e -> {
           player2NameField.setDisable(false);
       });

       Button startButton = new Button("Start");
       startButton.setStyle("-fx-font-size: 20px;");

       startButton.setOnAction(e -> {
          String player1Name = player1NameField.getText().trim();
          String player2Name = player2NameField.getText().trim();
          boolean isSinglePlayer = singlePlayerButton.isSelected();

          if (multiPlayerButton.isSelected() && player2Name.isEmpty()) {
              showAlert("Error!", "Player 2 Name is required");
          }
       });
       root.getChildren().addAll(
               titleLabel,
               singlePlayerButton,
               multiPlayerButton,
               player1NameField,
               player2NameField,
               startButton
       );
       Scene scene = new Scene(root, 400, 300);
       primaryStage.setScene(scene);
       primaryStage.show();
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
