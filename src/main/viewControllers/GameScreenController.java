package main.viewControllers;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.model.BattleshipGame;
import main.model.GameRules;
import main.model.Player;
import main.model.ShipType;

import java.io.IOException;
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
    @FXML
    private Label instructions;
    @FXML
    private Label shotsLeftLabel;
    @FXML
    private Label gameMessageText;

    private BattleshipGame game;
    private GameRules rules;
    private List<Shot> shots = new ArrayList<>();
    private Player playerOne;
    private Player playerTwo;
    private Player currentPlayer;
    private Stage primaryStage;
    private SimpleBooleanProperty turnOver = new SimpleBooleanProperty(false);
    private SimpleIntegerProperty shotsLeftProperty = new SimpleIntegerProperty(1);

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
     * Framework method. Called after dependency injection.
     */
    public void initialize() {
        instructions.setText(
                "Instructions: \n" +
                        "Each player has a certain number \n" +
                        "of shots determined by game type. \n" +
                        "Players take turns taking shots \n" +
                        "at each other's board until one \n" +
                        "player has sunk all of the other's \n" +
                        "ships."
        );
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
                playerOnePane.setOnMouseClicked(event -> takeShot(row, col, playerOnePane, playerTwo));
                playerTwoPane.setOnMouseClicked(event -> takeShot(row, col, playerTwoPane, playerOne));
                shotsLeftProperty.setValue(rules.getShotsPerTurn(playerOne));
                SimpleStringProperty shotsLeftStringProperty = new SimpleStringProperty("Shots left: ");
                shotsLeftLabel.textProperty().bind(shotsLeftStringProperty.concat(shotsLeftProperty));
                turnOver.bind(shotsLeftProperty.isEqualTo(0));
                playerOneGrid.add(playerOnePane, col, row);
                playerTwoGrid.add(playerTwoPane, col, row);
            }
        }
        nextTurnButton.disableProperty().bind(turnOver.not());
    }

    private void addShot(Shot shot) {
        this.shots.add(shot);
        shot.pane.getStyleClass().set(0,"gridSquare-shot");
    }

    /**
     * Update game state after shot.
     */
    private void nextShot() {
        shotsLeftProperty.set(shotsLeftProperty.getValue() - 1);
        if (turnOver.getValue()) {
            showShots();
            Player winner = game.getWinner();
            if (winner != null) {
                showGameOverScreen(winner);
            }
        }
    }
    /**
     * Show game over screen. Pass in winner.
     */
    private void showGameOverScreen(Player winner) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/res/fxml/GameOverScreen.fxml"));
            Parent root = loader.load();
            GameOverScreenController controller = loader.getController();
            controller.setWinner(winner.equals(playerOne) ? "Player One" : "Player Two");
            controller.setPrimaryStage(primaryStage);
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.err.println("Unable to load game over screen.");
            e.printStackTrace();
        }
    }

    /**
     * show shots taken and clear list
     */
    private void showShots() {
        // edit style of shot panes
        int sunkShips = 0;
        for (Shot s : shots) {
            s.pane.getStyleClass().set(0, (s.hit == ShipType.EMPTY_SQUARE ? "gridSquare-miss" : "gridSquare-hit"));
            if(s.player.hitShip(s.hit)){
                sunkShips++;
            }
        }
        setShipSunkText(sunkShips);
        // test for win
        shots.clear();
    }

    private void setShipSunkText(int sunkShips) {
        if(sunkShips > 0){
            gameMessageText.setText("You sunk " + sunkShips + (sunkShips == 1 ? " ship" : " ships."));
        } else {
            gameMessageText.setText("");
        }
    }

    private void takeShot(int row, int col, Pane pane, Player player){
        if (!turnOver.getValue()) {
            addShot(new Shot(row, col, player.getBoard().shoot(row, col), pane, player));
            nextShot();
            pane.setOnMouseClicked(null);
        }
    }


    @FXML
    public void nextPlayer() {
        setShipSunkText(0);
        if (currentPlayer.equals(playerOne)) {
            playerOneView.setVisible(false);
            playerTwoView.setVisible(true);
            currentPlayer = playerTwo;
        } else {
            playerOneView.setVisible(true);
            playerTwoView.setVisible(false);
            currentPlayer = playerOne;
        }
        shotsLeftProperty.set(rules.getShotsPerTurn(currentPlayer));
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
