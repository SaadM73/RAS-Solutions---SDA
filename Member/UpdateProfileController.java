package Member;

import Admin.*;
import Staff.*;
import application.*;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;

public class UpdateProfileController {

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Button backButton;
    
    @FXML
    private Button cancelButton;
    
    @FXML
    private Button saveButton;
    
    @FXML
    private Label feedbackLabel;

    @FXML
    void handleSaveChanges(ActionEvent event) {
        String memberNameText = fullNameField.getText().trim();
        String email = emailField.getText().trim();
        String phoneNumber = phoneNumberField.getText().trim();
        String password = passwordField.getText().trim();

        if (memberNameText.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
            feedbackLabel.setText("Member ID, Email, and Phone Number are required.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try 
        {

            if (Member.updateMember(memberNameText, email, phoneNumber, password)) {
                feedbackLabel.setText("Profile updated successfully.");
                feedbackLabel.setStyle("-fx-text-fill: green;");
            } 
            
            else 
            {
                feedbackLabel.setText("Member not found.");
                feedbackLabel.setStyle("-fx-text-fill: red;");
            }
        }
        catch (NumberFormatException e)
        {
            feedbackLabel.setText("Invalid Member ID.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
        } 
        
        catch (Exception e) {
            feedbackLabel.setText("An error occurred. Please try again.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    @FXML
    void handleCancel(ActionEvent event)
    {
    	clearForm();
        feedbackLabel.setText("");
    }
    
    private void clearForm()
    {
        fullNameField.clear();
        emailField.clear();
        passwordField.clear();
        phoneNumberField.clear();
        
    }
    
    @FXML
    private void handleBack()
    {
        try
        {
            // Load the previous scene (LoginPage or similar)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MemberDashboard.fxml")); // Adjust path as necessary
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Member Dashboard");
            currentStage.show();
        } 
        
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
}

