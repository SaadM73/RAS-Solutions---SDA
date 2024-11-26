package Admin;

import Member.*;
import Staff.*;
import application.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ManageMembershipController 
{

	@FXML private Button viewMembershipButton;
	@FXML private Button cancelMembershipButton;
	@FXML private Button backButton;
	
	@FXML
    public void handleViewMembership()
	{
		if (viewMembershipButton != null) {
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewMembership.fxml"));
	            AnchorPane root = loader.load();

	            Stage currentStage = (Stage) viewMembershipButton.getScene().getWindow();
	            Scene scene = new Scene(root);
	            currentStage.setScene(scene);
	            currentStage.setTitle("View Membership");
	            currentStage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    } else {
	        System.out.println("viewMembershipButton is null");
	    }

    }

    @FXML
    public void handleCancelMembership() 
    {
    	try {
            // Load AdminHome.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CancelMembership.fxml"));
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) cancelMembershipButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Cancel Membership");
            currentStage.show();
        } 
    	
    	catch (IOException e) {
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
