package Member;

import Admin.*;
import Staff.*;
import application.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.IOException;

import javafx.event.ActionEvent;

public class MemberRegisterController
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
    private ComboBox<String> membershipTypeComboBox;

    @FXML
    private Label feedbackLabel;
    
    @FXML 
    private Button backButton;
    
    @FXML
    private Button registerButton;

    //private final SQLPersistenceHandler persistenceHandler;

    // Constructor to initialize the persistence handler
    /*public MemberRegisterController() {
        //persistenceHandler = new SQLPersistenceHandler();
    }*/

    @FXML
    public void initialize() {
        // Add membership types to the combo box
        membershipTypeComboBox.getItems().addAll("Basic", "Premium", "VIP");
    }

    @FXML
    void handleRegisterButton(ActionEvent event) {
        // Get input values
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        String phoneNumber = phoneNumberField.getText().trim();
        String membershipType = membershipTypeComboBox.getValue();

        // Validate inputs
        if (!validateInputs(fullName, email, password, phoneNumber, membershipType)) {
            return;
        }

        try 
        {
            // Create a new member object
        	Member newMember = MemberFactory.createMember(0, fullName, email, password, phoneNumber, membershipType);
        	        	
            // Check if the email already exists in the database
            if (Member.isEmailAlreadyRegistered(newMember.getEmail())) 
            {
                feedbackLabel.setText("Email is already registered. Please use a different email.");
                feedbackLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            Member.register(newMember);
            switchtologin();
            clearForm();

        } 
        catch (Exception e) {
            feedbackLabel.setText("An error occurred during registration. Please try again.");
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

    private boolean validateInputs(String fullName, String email, String password, String phoneNumber, String membershipType)
    {
        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || phoneNumber.isEmpty() || membershipType == null) {
            feedbackLabel.setText("All fields are required. Please fill in all fields.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            return false;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$"))
        {
            feedbackLabel.setText("Invalid email format.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            return false;
        }

        if (password.length() < 6) 
        {
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
        membershipTypeComboBox.setValue(null);
    }
    

    // Ensure the connection is closed when the controller is destroyed
    public void close() {
    	SQLPersistenceHandler.getInstance().closeConnection();
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
    
    public void switchtologin()
    {
    	try {
            // Load the previous scene (LoginPage or similar)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MemberLogin.fxml")); // Adjust path as necessary
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) registerButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Member Login");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
