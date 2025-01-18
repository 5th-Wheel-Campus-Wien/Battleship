package at.fifthwheel.battleship;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class BattleShipApp extends Application {

    private SceneManager sceneManager;

    @Override
    public void start(Stage stage) throws IOException {
        SceneManager sceneManager = new SceneManager(stage);

        // Register PlayerNameScene
        FXMLLoader playerNameLoader = new FXMLLoader(getClass().getResource("playername-view.fxml"));
        Scene playerNameScene = new Scene(playerNameLoader.load());
        PlayerNameController playerNameController = playerNameLoader.getController();
        playerNameController.setSceneManager(sceneManager);

        sceneManager.registerScene("playerNameSelection", playerNameScene);

        // Register TitleScreenScene
        FXMLLoader titleScreenLoader = new FXMLLoader(getClass().getResource("titlescreen-view.fxml"));
        Scene titleScreenScene = new Scene(titleScreenLoader.load());
        TitleScreenController titleScreenController = titleScreenLoader.getController();
        titleScreenController.setSceneManager(sceneManager);

        sceneManager.registerScene("titleScreen", titleScreenScene);

        // Register GameSetupScene
        FXMLLoader gameSetupLoader = new FXMLLoader(getClass().getResource("game-setup-view.fxml"));
        Scene gameSetupScene = new Scene(gameSetupLoader.load());
        GameSetupController gameSetupController = gameSetupLoader.getController();
        gameSetupScene.getRoot().setUserData(gameSetupLoader);
        gameSetupController.setSceneManager(sceneManager);

        sceneManager.registerScene("gameSetup", gameSetupScene);

        // Register GameplayScene
        FXMLLoader gameplayLoader = new FXMLLoader(getClass().getResource("gameplay-view.fxml"));
        Scene gameplayScene = new Scene(gameplayLoader.load());
        GameplayController gameplayController = gameplayLoader.getController();
        gameplayController.setSceneManager(sceneManager);

        sceneManager.registerScene("gameplay", gameplayScene);

        // Register WinScreen
        FXMLLoader winScreenLoader = new FXMLLoader(getClass().getResource("winscreen-view.fxml"));
        Scene winScreenScene = new Scene(winScreenLoader.load());
        WinScreenController winScreenController = winScreenLoader.getController();
        winScreenController.setSceneManager(sceneManager);

        sceneManager.registerScene("winScreen", winScreenScene);

        // Register ComputerScreen
        FXMLLoader computerScreenLoader = new FXMLLoader(getClass().getResource("computer-setup-view.fxml"));
        Scene computerScreenScene = new Scene(computerScreenLoader.load());
        ComputerSetupController computerScreenController = computerScreenLoader.getController();
        computerScreenController.setSceneManager(sceneManager);


        //titleScreenLoader
        //titleScreenScene

        stage.setTitle("Battleship");
        stage.setScene(computerScreenScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}