package Member;

import Admin.*;
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


public class MemberDashboardController
{

    @FXML
    private Button viewBookingButton;
    
    @FXML
    private Button makeBookingButton;
    
    @FXML
    private Button cancelBookingButton;
    
    @FXML
    private Button payBillsButton;
    
    @FXML
    private Button requestRefundButton;
    
    @FXML
    private Button updateProfileButton;
    
    @FXML
    private Button membershipDetailsButton;
    
    @FXML 
    private Button backButton;
    
    @FXML private Label welcomeLabel;
    
    @FXML
    private void initialize() {
        Member loggedinMember = SessionManager.getInstance().getLoggedInMember();
        if (loggedinMember != null) {
            welcomeLabel.setText("Welcome " + loggedinMember.getName());
        } else {
            welcomeLabel.setText("Welcome, Guest!");
        }
    }

    
	@FXML
    void handleViewBooking(ActionEvent event) 
	{
        System.out.println("Navigating to View Booking page...");
        try {
            // Load AdminHome.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MemberViewBooking.fxml"));
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
    void handleMakeBooking(ActionEvent event) {
        System.out.println("Navigating to Make Booking page...");
        try {
            // Load AdminHome.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MemberMakeBooking.fxml"));
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
    void handleCancelBooking(ActionEvent event) {
        System.out.println("Navigating to Cancel Booking page...");
        try {
            // Load AdminHome.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MemberCancelBooking.fxml"));
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
    void handlePayBills(ActionEvent event) {
        System.out.println("Navigating to Pay Bills page...");
        try {
            // Load AdminHome.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PayBills.fxml"));
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) payBillsButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Pay Bills");
            currentStage.show();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    @FXML
    void handleRequestRefund(ActionEvent event) {
        System.out.println("Navigating to Request Refund page...");
        try {
            // Load AdminHome.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RequestRefundPage.fxml"));
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) requestRefundButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Request Refund");
            currentStage.show();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    @FXML
    void handleUpdateProfile(ActionEvent event) {
        System.out.println("Navigating to Update Profile page...");
        try {
            // Load AdminHome.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateProfilePage.fxml"));
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) updateProfileButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Update Profile");
            currentStage.show();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    @FXML
    void handleMembershipDetails(ActionEvent event) {
        System.out.println("Navigating to Membership Details page...");
        try {
            // Load AdminHome.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MembershipDetailsPage.fxml"));
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) membershipDetailsButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Membership Details");
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
