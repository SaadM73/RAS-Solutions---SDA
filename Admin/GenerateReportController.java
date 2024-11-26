package Admin;

import Member.*;
import Staff.*;
import application.*;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GenerateReportController
{

	@FXML private Button backButton;
	@FXML private Button report1Button;
	@FXML private Button report2Button;
	@FXML private Button report3Button;
	
	@FXML
    void handlereport1() 
    {
    	try {
            // Load the previous scene (LoginPage or similar)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Report1.fxml")); // Adjust path as necessary
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) report1Button.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Report 1");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handlereport2(ActionEvent event) {
    	try {
            // Load the previous scene (LoginPage or similar)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Report2.fxml")); // Adjust path as necessary
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) report2Button.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Report 2");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void handlereport3(ActionEvent event) 
    {
    	try {
            // Load the previous scene (LoginPage or similar)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Report3.fxml")); // Adjust path as necessary
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) report3Button.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Report 3");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleBack() {
        try {
            // Load the previous scene (LoginPage or similar)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHome.fxml")); // Adjust path as necessary
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Admin Home");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}





