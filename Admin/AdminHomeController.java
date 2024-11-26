package Admin;

import Member.*;
import Staff.*;
import application.*;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdminHomeController 
{

	@FXML
    private Button genertereportButton;
	
	@FXML
    private Button managemembershipButton;
	
	@FXML
    private Button managestaffButton;
	
	@FXML
    private Button maintainFacilitiesButton;
	
	@FXML
    private Button viewStaffButton;
	
	@FXML
	private Button backButton;
	
    @FXML
    public void handleGenerateReport(ActionEvent event) {
        System.out.println("Generate Report button clicked!");
        try {
            // Load AdminHome.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GenerateReport.fxml"));
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) genertereportButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Generate Report");
            currentStage.show();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleManageMembership(ActionEvent event) {
        System.out.println("Manage Membership button clicked!");
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageMembership.fxml"));
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) managemembershipButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Manage Membership");
            currentStage.show();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void handleviewStaff(ActionEvent event) {
        System.out.println("View Staff button clicked!");
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewStaffPage.fxml"));
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) viewStaffButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("View Staff");
            currentStage.show();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void handleManageStaff(ActionEvent event) {
        System.out.println("Manage Staff button clicked!");
        try 
        {            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageStaffPage.fxml"));
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) managestaffButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Manage Staff");
            currentStage.show();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleMaintainFacilities(ActionEvent event) {
        System.out.println("Maintain Facilities button clicked!");
        try 
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageFacility.fxml"));
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) maintainFacilitiesButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Maintain Facilities");
            currentStage.show();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleBack() {
        try {
            // Load the previous scene (LoginPage or similar)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/MainPage.fxml")); // Adjust path as necessary
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Main Page");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
