package main.viewControllers;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.model.BattleshipGame;
import main.model.GameRules;
import main.model.Player;
import main.model.ShipType;

import java.util.ArrayList;
import java.util.List;

public class GameScreenController {

    @FXML
    private GridPane playerOneGrid;
    @FXML
    private GridPane playerTwoGrid;
    @FXML
    private VBox playerOneView;
    @FXML
    private VBox playerTwoView;
    @FXML
    private Button nextTurnButton;

    private BattleshipGame game;
    private GameRules rules;
    private List<Shot> shots = new ArrayList<>();
    private int shotsThisTurn = 0;
    private Player playerOne;
    private Player playerTwo;
    private Player currentPlayer;
    private Stage primaryStage;
    private SimpleBooleanProperty turnOver = new SimpleBooleanProperty(false);

    /**
     * Public setter for the game to play.
     */
    public void setGame(BattleshipGame game) {
        this.game = game;
        this.playerOne = game.getPlayerOne();
        this.playerTwo = game.getPlayerTwo();
        currentPlayer = playerOne;
    }

    /**
     * Public setter for primary stage
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Public setter for game rules to use.
     */
    public void setRules(GameRules rules) {
        this.rules = rules;
    }

    /**
     * Initialize the game grids after the public set methods have been called.
     * Note to self: fix this... figure out something that works better with the framework.
     */
    public void postInit() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Pane playerOnePane = new Pane();
                playerOnePane.getStyleClass().add("gridSquare");
                Pane playerTwoPane = new Pane();
                playerTwoPane.getStyleClass().add("gridSquare");
                final int row = j;
                final int col = i;
                // set up event listeners on Panes. Player Two's grid will shoot at player one and vice versa
                playerOnePane.setOnMouseClicked(event -> {
                    if (!turnOver.getValue()) {
                        shots.add(new Shot(row, col, playerTwo.getBoard().shoot(row, col), playerOnePane, playerTwo));
                        nextShot();
                        playerOnePane.setOnMouseClicked(null);
                    }
                });
                playerTwoPane.setOnMouseClicked(event -> {
                    if (!turnOver.getValue()) {
                        shots.add(new Shot(row, col, playerOne.getBoard().shoot(row, col), playerTwoPane, playerOne));
                        nextShot();
                        playerTwoPane.setOnMouseClicked(null);
                    }
                });
                playerOneGrid.add(playerOnePane, col, row);
                playerTwoGrid.add(playerTwoPane, col, row);
            }
        }
        nextTurnButton.disableProperty().bind(turnOver.not());
    }

    /**
     * Update game state after shot.
     */
    private void nextShot() {
        shotsThisTurn++;
        turnOver.setValue(shotsThisTurn == rules.getShotsPerTurn(currentPlayer));
        if (turnOver.getValue()) {
            showShots();
            Player winner = game.getWinner();
            if(winner != null){
                showGameOverScreen(winner);
            }
        }
    }

    /**
     * Show game over screen. Pass in winner.
     */
    private void showGameOverScreen(Player winner) {
        
    }

    /**
     * show shots taken and clear list
     */
    private void showShots() {
        // edit style of shot panes
        for (Shot s : shots) {
            s.pane.getStyleClass().set(0, (s.hit == ShipType.EMPTY_SQUARE ? "gridSquare-miss" : "gridSquare-hit"));
            s.player.hitShip(s.hit);
        }
        // test for win
        shots.clear();
    }

    @FXML
    public void nextPlayer() {
        turnOver.setValue(false);
        shotsThisTurn = 0;
        if(currentPlayer.equals(playerOne)){
            playerOneView.setVisible(false);
            playerTwoView.setVisible(true);
            currentPlayer = playerTwo;
        } else {
            playerOneView.setVisible(true);
            playerTwoView.setVisible(false);
            currentPlayer = playerOne;
        }
    }

    private static class Shot {

        int row;
        int col;
        ShipType hit;
        Pane pane;
        Player player;

        Shot(int row, int col, ShipType hit, Pane pane, Player player) {
            this.row = row;
            this.col = col;
            this.hit = hit;
            this.pane = pane;
            this.player = player;
        }

        @Override
        public String toString() {
            return "Shot{" +
                    "row=" + row +
                    ", col=" + col +
                    ", hit=" + hit +
                    '}';
        }
    }
}
