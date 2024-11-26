package application;

import Member.*;
import Staff.*;
import Admin.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
        
        // Create the root pane
        AnchorPane root = loader.load();

        // Set up the scene
        Scene scene = new Scene(root, 835, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("RAS Leisure Club Registration");

        // Show the stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
