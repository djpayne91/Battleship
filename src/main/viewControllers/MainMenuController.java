package main.viewControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller class for Main Menu
 */
public class MainMenuController {
    private Stage primaryStage;

    public MainMenuController(){
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void showNewGameMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/res/fxml/NewGameMenu.fxml"));
            // don't forget to load before trying to get controller
            Parent root = loader.load();
            NewGameMenuController newGameMenuController = loader.getController();
            newGameMenuController.setPrimaryStage(primaryStage);
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.err.println("Unable to load New Game Menu");
            e.printStackTrace();
        }
    }

    public void exitGame(javafx.event.ActionEvent event) {
        primaryStage.close();
    }
}
