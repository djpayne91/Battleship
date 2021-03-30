package main.viewControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;
import main.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Controller for view where player places ships.
 */
public class PlaceShipsScreenController {
    private final ToggleGroup shipButtons = new ToggleGroup();
    private final List<ToggleButton> ships = new ArrayList<>();
    private final Stack<Pair<Pane, Color>> needToReset = new Stack<>();
    @FXML
    private GridPane shipGrid;
    @FXML
    private ToggleGroup orientationGroup;
    @FXML
    private RadioButton vertToggle;
    @FXML
    private RadioButton horizToggle;
    @FXML
    private VBox shipList;
    @FXML
    private Button nextButton;
    @FXML
    private Label instructions;
    private Player player;
    private Pane[][] shipPanes;
    private Orientation orientation = Orientation.VERTICAL;
    private Stage primaryStage;
    private GameRules gameRules;
    private Ship currentShip;
    private boolean isPlayerTwo = false;
    private BattleshipGame game;

    /**
     * Public setter for primary stage.
     *
     * @param primaryStage stage the scene will be on.
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Public setter for rules the game will use.
     *
     * @param gameRules GameRules object to use.
     */
    public void setGameRules(GameRules gameRules) {
        this.gameRules = gameRules;
    }

    /**
     * Public setter for player that is placing ships.
     *
     * @param player player to place ships.
     */

    public void setPlayer(Player player) {
        this.player = player;
        this.currentShip = player.getShips().get(0);
        initToggleGroup();
    }

    public void setGame(BattleshipGame game) {
        this.game = game;
    }

    /**
     * Set whether this is player 2;
     *
     * @param isLastPlayer True if player 2 will place their ships.
     */
    public void setIsPlayerTwo(boolean isLastPlayer) {
        this.isPlayerTwo = isLastPlayer;
    }

    /**
     * The framework will call this after the view is drawn and the view components injected into this controller.
     */
    @FXML
    public void initialize() {
        // write instructions
        instructions.setText(
                "Placing ships: " + (isPlayerTwo ? "Player Two" : "Player One") + "\n" +
                        "Each player must place all of their ships on the board. \n" +
                        "Make sure the other player is looking away.\n" +
                        "You must place all of your ships.\n"

        );
        // initialize squares with white background in gridpane. keep refs for easy access.
        shipPanes = new Pane[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Pane pane = new Pane();
                pane.getStyleClass().add("gridSquare");
                final int row = i;
                final int column = j;
                pane.setOnMouseEntered(event -> hoverShipOverPane(shipPanes, currentShip, orientation, row, column));
                pane.setOnMouseClicked(event -> {
                    if (player.getBoard().placementIsValid(currentShip, row, column, orientation)) {
                        placeShipAtPane(shipPanes, currentShip, row, column, orientation);
                    }
                });
                shipGrid.add(pane, i, j);
                shipPanes[i][j] = pane;
            }
        }
    }

    /**
     * Event handler for next screen button.
     */

    @FXML
    public void nextButton(ActionEvent event) {
        if (!isPlayerTwo) {
            game = new BattleshipGame();
            game.setPlayerOne(player);
            goToNextPlayer();
        } else {
            game.setPlayerTwo(player);
            startGame();
        }
    }

    /**
     * Event handler for ship orientation buttons.
     */

    @FXML
    public void switchOrientation(ActionEvent event) {
        if (orientationGroup.getSelectedToggle().equals(vertToggle)) {
            orientation = Orientation.VERTICAL;
        } else if (orientationGroup.getSelectedToggle().equals(horizToggle))
            orientation = Orientation.HORIZONTAL;
    }

    /**
     * Load and display the next ship placement scene for player 2.
     */
    private void goToNextPlayer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/res/fxml/PlaceShipsScreen.fxml"));
            Parent root = loader.load();
            PlaceShipsScreenController controller = loader.getController();
            controller.setGameRules(gameRules);
            controller.setGame(game);
            controller.setPlayer(new Player(gameRules.getShipList(), new Board()));
            controller.setPrimaryStage(primaryStage);
            controller.setIsPlayerTwo(true);
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.err.println("Unable to load next ship placement screen.");
            e.printStackTrace();
        }
    }

    /**
     * Load and display game screen.
     */
    private void startGame() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/res/fxml/GameScreen.fxml"));
            Parent root = loader.load();
            GameScreenController controller = loader.getController();
            controller.setGame(game);
            controller.setRules(gameRules);
            controller.setPrimaryStage(primaryStage);
            controller.postInit();
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.err.println("Unable to load game screen.");
            e.printStackTrace();
        }
    }

    /**
     * Private helper method to place ships on the board.
     */
    private void placeShipAtPane(Pane[][] panes, Ship ship, int row, int column, Orientation orientation) {
        player.getBoard().placeShip(ship, row, column, orientation);
        disableShipButton(ship);
        needToReset.clear();
        switch (orientation) {
            case VERTICAL: {
                for (int i = 0; i < ship.getLength(); i++) {
                    changePaneColor(panes[row + i][column], Color.BLUE);
                }
                break;
            }
            case HORIZONTAL: {
                for (int i = 0; i < ship.getLength(); i++) {
                    changePaneColor(panes[row][column + i], Color.BLUE);
                }
                break;
            }
        }
    }


    /**
     * Helper method to populate ship toggle group
     */

    private void initToggleGroup() {
        for (Ship s : player.getShips()) {
            ToggleButton btn = new ToggleButton(s.getId().toString());
            btn.setToggleGroup(shipButtons);
            btn.setOnMouseClicked(event -> currentShip = s);
            ships.add(btn);
        }
        ships.get(0).setSelected(true);
        shipList.getChildren().addAll(ships);
    }

    /**
     * Helper method for changing colors of certain panes.
     */

    private void changePaneColor(Pane pane, Color color) {
        pane.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void disableShipButton(Ship ship) {
        // disable placed ship
        int index = player.getShips().indexOf(ship);
        ships.get(index).setDisable(true);
        // select new ship
        for (int i = 0; i < ships.size(); i++) {
            if (!ships.get(i).isDisabled()) {
                ships.get(i).setSelected(true);
                currentShip = player.getShips().get(i);
                return;
            }
        }
        // if all buttons are disabled, its time to clean up and get ready to switch scenes
        prepareForNextScene();
    }

    private void prepareForNextScene() {
        for (Node pane : shipGrid.getChildren()) {
            pane.setOnMouseEntered(null);
            pane.setOnMouseClicked(null);
        }
        nextButton.setDisable(false);
        nextButton.setText(isPlayerTwo ? "Start Game" : "Next Player");
    }

    /**
     * Hover a ship with the given orientation over the given pane.
     *
     * @param panes       The set of panes to highlight.
     * @param ship        The ship to hover.
     * @param orientation Orientation of the ship.
     * @param row         The column of the pane
     * @param col         The row of the pane.
     */

    private void hoverShipOverPane(Pane[][] panes, Ship ship, Orientation orientation, int row, int col) {
        // reset all panes from last hover
        for (Iterator<Pair<Pane, Color>> it = needToReset.iterator(); it.hasNext(); ) {
            Pair<Pane, Color> temp = it.next();
            changePaneColor(temp.getKey(), temp.getValue());
            it.remove();
        }
        // recolor panes that need it
        switch (orientation) {
            case VERTICAL: {
                Color color = player.getBoard().placementIsValid(ship, row, col, orientation) ? Color.GREEN : Color.RED;
                int numSquares;
                if (row + ship.getLength() > panes.length) {
                    numSquares = ship.getLength() - (row + ship.getLength() - panes.length);
                } else {
                    numSquares = ship.getLength();
                }
                for (int i = 0; i < numSquares; i++) {
                    Color oldColor = (Color) panes[row + i][col].getBackground().getFills().get(0).getFill();
                    needToReset.push(new Pair<>(panes[row + i][col], oldColor));
                    changePaneColor(panes[row + i][col], color);
                }
                break;
            }
            case HORIZONTAL: {
                Color color = player.getBoard().placementIsValid(ship, row, col, orientation) ? Color.GREEN : Color.RED;
                int numSquares;
                if (col + ship.getLength() > panes[0].length) {
                    numSquares = ship.getLength() - (col + ship.getLength() - panes[0].length);
                } else {
                    numSquares = ship.getLength();
                }
                for (int i = 0; i < numSquares; i++) {
                    Color oldColor = (Color) panes[row][col + i].getBackground().getFills().get(0).getFill();
                    needToReset.push(new Pair<>(panes[row][col + i], oldColor));
                    changePaneColor(panes[row][col + i], color);
                }
                break;
            }
        }
    }

}
