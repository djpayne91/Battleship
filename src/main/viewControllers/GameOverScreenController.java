package main.viewControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;


public class GameOverScreenController {

    @FXML
    private Label winnerLabel;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public void setWinner(String winnerName){
        this.winnerLabel.setText("Winner: \n" + winnerName);
    }

    @FXML
    public void showMainMenu(){
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
