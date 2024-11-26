package Staff;


import application.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.io.IOException;

public class StaffLoginController
{
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button resetButton;
    @FXML private Label feedbackLabel;
    @FXML private Button backButton;

    @FXML
    public void initialize() {
        loginButton.setOnAction(event -> handleLogin());
        resetButton.setOnAction(event -> resetFields());
    }

    private void handleLogin() {
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
            if (Staff.validateStaffLogin(email, password)) 
            {
                feedbackLabel.setText("Login successful! Redirecting...");
                feedbackLabel.setStyle("-fx-text-fill: green;");
                switchToStaffHome();
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

    private void switchToStaffHome()
    {
        try 
        {
            // Load AdminHome.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffMain.fxml"));
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Staff Main");
            currentStage.show();
        }
        
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void resetFields() {
        emailField.clear();
        passwordField.clear();
        feedbackLabel.setText("");
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
