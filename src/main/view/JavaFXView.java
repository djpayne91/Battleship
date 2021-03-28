package main.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.GameController;

import java.io.IOException;

/**
 * Class representing a view using JavaFX for UI.
 */
public class JavaFXView extends Application implements View {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;

    private static Scene mainMenuScene;
    private static Scene gameOptionsScene;

    private Stage stage;

    private GameController controller = new GameController();

    /**
     * Starting point for any JavaFX application. This will get called elsewhere.
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        // initialize scenes so we don't have to initialize on the fly as we switch around.
        // Treat this like a state machine and we're just setting up the states now. Runtime will only handle transitioning.
        // set up initial settings
        loadScenes();

        stage.setTitle("Battleship");
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
        showMainMenu();
    }

    /**
     * Show view to user.
     * @param args
     */
    @Override
    public void show(String[] args) {
        launch(args);
    }

    /**
     * Switches view to main menu.
     */
    public void showMainMenu(){

    }

    /**
     * Switches view to game options menu to start a new game
     */
    public void showGameOptionsMenu(){

    }

    /**
     * Switches view to Gameplay screen. This screen will be
     */
    public void showGameScreen(){

    }

    /**
     * Switches view to game over screen
     */
    public void showGameOverScreen(){

    }

    public void exitGame(){
        stage.close();
    }


    private void loadScenes() {
        // TODO load scenes here so we don't have to during runtime.
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/main/res/fxml/MainMenu.fxml"));
            mainMenuScene = new Scene(root);
        } catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

}
