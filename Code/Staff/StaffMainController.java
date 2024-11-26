package Staff;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StaffMainController 
{

	@FXML
    private Button makeBookingButton;
	
	@FXML
    private Button viewBookingButton;
	
	@FXML
    private Button cancelBookingButton;
	
	@FXML
    private Button maintainFacilitiesButton;
	
	@FXML
	private Button backButton;
	
	@FXML
    void handleMakeBooking(ActionEvent event) {
        System.out.println("Navigating to Make Booking Page...");
        try {
            // Load AdminHome.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MakeBooking.fxml"));
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) makeBookingButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Make Booking");
            currentStage.show();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    @FXML
    void handleViewBooking(ActionEvent event) {
        System.out.println("Navigating to View Bookings Page...");
        try {
            // Load AdminHome.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewBookingsPage.fxml"));
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) viewBookingButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("View Booking");
            currentStage.show();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    @FXML
    void handleCancelBooking(ActionEvent event) {
        System.out.println("Navigating to Cancel Booking Page...");
        try {
            // Load AdminHome.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CancelBookingPage.fxml"));
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) cancelBookingButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Cancel Booking");
            currentStage.show();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    @FXML
    void handleMaintainFacilities(ActionEvent event) {
        System.out.println("Navigating to Maintain Facilities Page...");
        try {
            // Load AdminHome.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffMaintainFacilities.fxml"));
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
