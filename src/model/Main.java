package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;

/**
 * This application is an inventory management system that allows for addition, modification, and deletion of both in-house
 * and outsourced parts. Those parts can in turn be added to a list of products which they are used to produce.
 *
 * FUTURE ENHANCEMENT: A future enhancement of this application could be to include a check to verify that the sum of
 * prices of a product's associated parts does not exceed the price of the product or the acceptable profit margin.
 *
 * Generated javadoc files is located in BalabisProject/javadoc
 */
public class Main extends Application {

    /**
     * Loads and displays starting window.
     *
     * @param primaryStage
     * @throws Exception Throws IO exception.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Main.fxml")));
        primaryStage.setTitle("Main Form");
        primaryStage.setScene(new Scene(root, 760, 320));
        primaryStage.show();
    }

    /**
     * Main method to start application.
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
} 

