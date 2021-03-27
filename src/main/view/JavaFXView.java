package main.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class representing a view using JavaFX for UI.
 */
public class JavaFXView extends Application implements View {

    private Scene currentScene;
    private Stage stage;

    /**
     * Starting point for any JavaFX application. This will get called elsewhere.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        // set up initial settings

        stage.show();
        showMainMenu();
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
     * Switches view to Gameplay screen
     */
    public void showGameScreen(){

    }

    /**
     * Switches view to game over screen
     */
    public void showGameOverScreen(){

    }

    /**
     * Show view to user.
     * @param args
     */
    @Override
    public void show(String[] args) {
        launch(args);
    }

}
