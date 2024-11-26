package Member;

import Admin.*;
import Staff.*;
import application.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.*;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class MemberLoginController
{

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button resetButton;
    @FXML private Button registerButton;
    @FXML private Button backButton;
    @FXML private Label feedbackLabel;
    
    
    @FXML
    public void initialize() {
        loginButton.setOnAction(event -> handleLogin());
        resetButton.setOnAction(event -> resetFields());
        backButton.setOnAction(event -> handleBack()); // Added back button handler
    }
    @FXML
    void handleLogin() 
    {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (email.isEmpty() || password.isEmpty()) 
        {
            feedbackLabel.setText("Email and Password are required.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try 
        {
            Member member = Member.Login(email, password);
            if (member != null)
            {
                SessionManager.setLoggedInMember(member);
                feedbackLabel.setText("Login successful! Redirecting...");
                feedbackLabel.setStyle("-fx-text-fill: green;");
                switchToMemberHome();
                // Navigate to Member Dashboard
            } 
            
            else
            {
                feedbackLabel.setText("Invalid email or password. Please try again.");
                feedbackLabel.setStyle("-fx-text-fill: red;");
            }
        } 
        
        catch (Exception e)
        {
            feedbackLabel.setText("An error occurred during login. Please try again.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }


    private void switchToMemberHome() {
        try {
            // Load MemberDashboard.fxml
        	
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MemberDashboard.fxml"));
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Member Dashboard");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void handleRegister() {
        try {
            // Load MemberRegister.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MemberRegister.fxml"));
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) registerButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Member Register");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resetFields() {
        emailField.clear();
        passwordField.clear();
        feedbackLabel.setText("");
    }

    // Handle the back button action
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


