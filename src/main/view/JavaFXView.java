package main.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.GameController;

import java.io.IOException;

/**
 * Class representing a view using JavaFX for UI.
 */
public class JavaFXView extends Application implements View {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;

    private static Stage window;

    private GameController controller = new GameController();

    /**
     * Starting point for any JavaFX application. This will get called elsewhere.
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        // initialize scenes so we don't have to initialize on the fly as we switch around.
        // Treat this like a state machine and we're just setting up the states now. Runtime will only handle transitioning.
        // set up initial settings
        window.setTitle("Battleship");
        window.setWidth(WIDTH);
        window.setHeight(HEIGHT);
        window.setResizable(false);
        window.centerOnScreen();
        window.show();
        showMainMenu(null);
    }

    /**
     * Show view to user. Starting point for view interface.
     * @param args application args
     */
    @Override
    public void show(String[] args) {
        launch(args);
    }

    /**
     * Switches view to main menu.
     */
    @FXML
    public void showMainMenu(ActionEvent event){
        try {
            Scene newScene = loadScene("/main/res/fxml/MainMenu.fxml");
            window.setScene(newScene);
        } catch (IOException e) {
            System.err.println("Unable to load Main Menu");
            e.printStackTrace();
        }
    }

    /**
     * Switches view to game options menu to start a new game
     */
    @FXML
    public void showNewGameMenu(ActionEvent event){
        try {
            window.setScene(loadScene("/main/res/fxml/NewGameMenu.fxml"));
        } catch (IOException e) {
            System.err.println("Unable to load New Game Menu");
            e.printStackTrace();
        }
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
    @FXML
    public void exitGame(ActionEvent event){
        window.close();
    }

    /**
     * Helper method to load scenes from fxml
     * @param filePath path to file for scene to load
     * @return loaded scene
     * @throws IOException if there's an io issue
     */
    private Scene loadScene(String filePath) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(filePath));
        return new Scene(root);
    }

}
