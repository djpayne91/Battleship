package main;

import main.view.JavaFXView;
import main.view.View;

/**
 * Driver class for the application
 */
public class Main {

    public static void main(String[] args) {
        // setup controller with view.
        View javaFxView = new JavaFXView();
        javaFxView.show(args);
    }

}
