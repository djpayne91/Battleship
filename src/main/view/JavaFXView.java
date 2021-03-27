package main.view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.GameController;
import main.StandardRules;
import main.model.BattleshipGame;
import main.model.Player;


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
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        // initialize scenes so we don't have to initialize on the fly as we switch around.
        // Treat this like a state machine and we're just setting up the states now. Runtime will only handle transitioning.
        mainMenuScene = initMainMenuScene();
        gameOptionsScene = initGameOptionsScene();

        // set up initial settings
        stage.setTitle("Battleship");
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
        showMainMenu();
    }

    /**
     * Switches view to main menu.
     */
    public void showMainMenu(){
        stage.setScene(mainMenuScene);
    }

    /**
     * Switches view to game options menu to start a new game
     */
    public void showGameOptionsMenu(){
        stage.setScene(gameOptionsScene);
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

    public void exitGame(){
        stage.close();
    }

    /**
     * Show view to user.
     * @param args
     */
    @Override
    public void show(String[] args) {
        launch(args);
    }

    private Scene initMainMenuScene(){
        VBox root = new VBox();
        Button newGameButton = new Button("New Game");
        newGameButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            showGameOptionsMenu();
        });
        Button exitButton = new Button("Exit Game");
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event)->{
            exitGame();
        });
        root.getChildren().addAll(newGameButton, exitButton);
        Scene scene = new Scene(root);
        scene.setFill(Color.ALICEBLUE);
        return scene;
    }

    private Scene initGameOptionsScene(){
        VBox root = new VBox();
        HBox ruleOptions = new HBox();

        ToggleGroup gameRuleOptions = new ToggleGroup();

        RadioButton standardButton = new RadioButton("Standard Rules");
        standardButton.setToggleGroup(gameRuleOptions);
        RadioButton salvoButton = new RadioButton("Salvo Rules");
        salvoButton.setToggleGroup(gameRuleOptions);
        salvoButton.setDisable(true);

        ruleOptions.getChildren().addAll(standardButton, salvoButton);

        Button startGameButton = new Button("Start Game");
        startGameButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            // ok so the start game button has been selected, we need to do a few things... first we need to check
            // if all the options have been checked
            if(gameRuleOptions.getSelectedToggle().equals(standardButton)){
                controller.startNewGame(new StandardRules());
            }
            gameRuleOptions.selectToggle(null);
        });
        // set up back button
        Button backButton = new Button("Back to main menu");
        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            showMainMenu();
        });

        root.getChildren().addAll(ruleOptions, startGameButton, backButton);
        Scene scene = new Scene(root);
        scene.setFill(Color.ALICEBLUE);
        return scene;
    }

}
