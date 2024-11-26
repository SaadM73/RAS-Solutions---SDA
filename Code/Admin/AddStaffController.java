package Admin;

import Member.*;
import Staff.*;
import application.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.IOException;

import javafx.event.ActionEvent;

public class AddStaffController
{

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField roleField;

    @FXML
    private Label feedbackLabel;
    
    @FXML 
    private Button backButton;
    
    @FXML private Button addButton;
    @FXML private Button cancelButton;

    @FXML
    void handleAddButton(ActionEvent event)
    {
        // Get input values
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        String phoneNumber = phoneNumberField.getText().trim();
        String role = roleField.getText().trim();

        // Validate inputs
        if (!validateInputs(fullName, role, email, phoneNumber, password))
        {
            return;
        }

        try {
            // Create a new member object
            Staff staff = StaffFactory.createStaff(0, fullName, role, email, phoneNumber, password);

            // Check if the email already exists in the database
            if (Staff.isEmailAlreadyRegistered(email)) {
                feedbackLabel.setText("Email is already registered. Please use a different email.");
                feedbackLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            // Insert the new Staff into the database
            Staff.insertStaff(staff);
            feedbackLabel.setText("Add Staff successful.");
            feedbackLabel.setStyle("-fx-text-fill: green;");
            clearForm();

        } catch (Exception e) {
            feedbackLabel.setText("An error occurred during addition. Please try again.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    @FXML
    void handleCancelButton(ActionEvent event) 
    {
        clearForm();
        feedbackLabel.setText("");
    }

    private boolean validateInputs(String fullName, String role, String email, String phoneNumber, String password) {
        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || phoneNumber.isEmpty() || role == null) {
            feedbackLabel.setText("All fields are required. Please fill in all fields.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            return false;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            feedbackLabel.setText("Invalid email format.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            return false;
        }

        if (password.length() < 6) {
            feedbackLabel.setText("Password must be at least 6 characters long.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            return false;
        }

        if (!phoneNumber.matches("^\\d{11}$")) {
            feedbackLabel.setText("Phone number must be 11 digits.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            return false;
        }

        return true;
    }

    private void clearForm() {
        fullNameField.clear();
        emailField.clear();
        passwordField.clear();
        phoneNumberField.clear();
        roleField.clear();
    }
    

    // Ensure the connection is closed when the controller is destroyed
    public void close() {
        SQLPersistenceHandler.getInstance().closeConnection();
    }
    
    @FXML
    private void handleBack() {
        try {
            // Load the previous scene (LoginPage or similar)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/ManageStaffPage.fxml")); // Adjust path as necessary
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Manage Staff");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
}
