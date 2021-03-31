package main.viewControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import main.model.*;

import java.io.IOException;

public class NewGameMenuController {
    /*
    If your name is not Dustin Payne and you're reading this... I accidentally left my notes in. Its almost 11PM now and
    I need to get ready for bed. I have the game fully implemented and pushed to the repo, but i've been implementing an AI
    player for the last few hours and i'm almost done.
    I need to ...
        1) add options to set AI player(s) in the new game menu
        2) add AI ship placement
        3) do a lot more documenting and unit testing and making sure this is ready to go.
        4) stop pranking myself so hard with crap like 1-3 all in one day.
     */

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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/res/fxml/PlaceShipsScreen.fxml"));
            Parent root = loader.load();
            GameRules rules;
            if (standardMode.isSelected()) {
                rules = new StandardRules();
            } else if (salvoMode.isSelected()) {
                rules = new SalvoRules();
            } else {
                throw new RuntimeException("Error in game mode selection");
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
