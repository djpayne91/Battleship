package main.viewControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import main.model.Board;
import main.model.GameRules;
import main.model.Player;
import main.model.StandardRules;

import java.io.IOException;

public class NewGameMenuController {

    public ToggleGroup gameMode;
    private Stage primaryStage;

    @FXML
    private RadioButton salvoMode;

    @FXML
    private RadioButton standardMode;

    /**
     * Setter for primary stage.
     *
     * @param primaryStage Stage this scene will be displayed on.
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Event handler for button to start the new game.
     */
    public void showGameScreen() {
        if (salvoMode.isSelected()) {
            System.err.println("Salvo Mode is not yet implemented.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/res/fxml/PlaceShipsScreen.fxml"));
            Parent root = loader.load();
            GameRules rules;
            if (standardMode.isSelected()) {
                rules = new StandardRules();
            } else {
                System.err.println("Error getting game rules");
                return;
            }
            PlaceShipsScreenController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);
            controller.setGameRules(rules);
            // I'd like this to be cleaner, but for now we initialize the first player.
            controller.setPlayer(new Player(rules.getShipList(), new Board(10, 10)));
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.err.println("Unable to load ship placement screen.");
            e.printStackTrace();
        }
    }

    /**
     * Event handler for button to return to main menu.
     */
    public void showMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/res/fxml/MainMenu.fxml"));
            Parent root = loader.load();
            MainMenuController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.err.println("Unable to load main menu.");
            e.printStackTrace();
        }
    }
}
