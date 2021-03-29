package main.viewControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.model.*;

import java.util.Stack;

public class GameScreenController {

    @FXML
    private VBox root;

    @FXML
    private GridPane playerOneGrid;

    @FXML
    private GridPane playerTwoGrid;

    @FXML
    private Text messageBox;

    @FXML
    private ToggleGroup orientationGroup;

    @FXML
    private RadioButton vertToggle;
    @FXML
    private RadioButton horizToggle;
    @FXML
    private VBox playerOneShipsBox;
    @FXML
    private VBox playerTwoShipsBox;

    private Pane[][] playerOnePanes;
    private Pane[][] playerTwoPanes;
    private Stack<Pane> needToReset = new Stack<>();
    private Orientation orientation = Orientation.VERTICAL;
    private Stage primaryStage;
    private GameRules gameRules;
    private BattleshipGame game;
    private Ship currentShip;

    /**
     * Public constructor must be present for framework.
     */
    public GameScreenController(){
    }

    /**
     * Public setter for primary stage.
     * @param primaryStage stage the scene will be on.
     */
    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    /**
     * Public setter for rules the game will use.
     * @param gameRules GameRules object to use.
     */
    public void setGameRules(GameRules gameRules){
        this.gameRules = gameRules;
    }

    /**
     * Sets up the game in this controller.
     * @param playerOne player one in the game
     * @param playerTwo player two in the game
     */
    public void setPlayers(Player playerOne, Player playerTwo){
        this.game = new BattleshipGame(playerOne, playerTwo);
    }

    /**
     * The framework will call this after the view is drawn and the view components injected into this controller.
     */
    @FXML
    public void initialize(){
        // initialize squares with white background in gridpane. keep refs for easy access.
        playerOnePanes = new Pane[10][10];
        playerTwoPanes = new Pane[10][10];
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                Pane pane1 = new Pane();
                pane1.getStyleClass().add("gridSquare");
                Pane pane2 = new Pane();
                pane2.getStyleClass().add("gridSquare");
                playerOneGrid.add(pane1, i, j);
                playerTwoGrid.add(pane2, i, j);
                playerOnePanes[i][j] = pane1;
                playerTwoPanes[i][j] = pane2;
            }
        }
        enterPlacementPlayerOne();
    }

    /**
     * method to enter placement phase for player one.
     */
    public void enterPlacementPlayerOne(){
        playerOneGrid.setOnMouseEntered(event -> {
            Pane target = (Pane) event.getTarget();
            System.out.println("Target X: " + target.getLayoutX());
            System.out.println("Target Y: " + target.getLayoutY());
        });
        messageBox.setText("First, Player One will place their ships!");
    }

    /**
     * enter placement phase for player two
     */
    public void enterPlacementPlayerTwo(){

    }

    /**
     * Hover a ship with the given orientation over the given pane. This can be done much better I think, but the glitches
     * are purely visual so I don't think its too big a deal for now.
     *
     * @param panes The set of panes to highlight.
     * @param ship  The ship to hover.
     * @param orientation   Orientation of the ship.
     * @param row   The row of the pane.
     * @param column The column of the pane.
     */

    public void hoverShipOverPane(Pane[][] panes, Ship ship, Orientation orientation, int row, int column){
        for(int i = 0; i < needToReset.size(); i++){
            changePaneColor(needToReset.pop(), Color.WHITE);
        }
        switch (orientation){
            case VERTICAL: {
                Color color;
                int numSquares = 0;
                if(row + ship.getLength() > panes.length){
                    color = Color.RED;
                    numSquares = ship.getLength() - (row + ship.getLength() - panes.length);
                } else {
                    color = Color.GREEN;
                    numSquares = ship.getLength();
                }
                for(int i = 0; i < numSquares; i++){
                    needToReset.push(panes[column][row+i]);
                    changePaneColor(panes[column][row+i], color);
                }
                break;
            }
            case HORIZONTAL: {
                Color color;
                int numSquares = 0;
                if(column + ship.getLength() > panes[0].length){
                    color = Color.RED;
                    numSquares = ship.getLength() - (column + ship.getLength() - panes[0].length);
                } else {
                    color = Color.GREEN;
                    numSquares = ship.getLength();
                }
                for(int i = 0; i < numSquares; i++){
                    needToReset.push(panes[column+i][row]);
                    changePaneColor(panes[column+i][row], color);
                }
                break;
            }
        }
    }

    /**
     * Helper class for changing colors of certain panes.
     * @param pane
     * @param color
     */
    private void changePaneColor(Pane pane, Color color){
        pane.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    /**
     * Event handler for ship orientation buttons.
     * @param event
     */
    @FXML
    public void switchOrientation(ActionEvent event){
        if(orientationGroup.getSelectedToggle().equals(vertToggle)){
            orientation = Orientation.VERTICAL;
        } else if(orientationGroup.getSelectedToggle().equals(horizToggle))
            orientation = Orientation.HORIZONTAL;
    }

}
