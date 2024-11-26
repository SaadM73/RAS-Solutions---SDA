package Admin;

import Member.*;
import Staff.*;
import application.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
/*
public class AdminLoginController {
    @FXML private TextField usernameField;
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
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.equals("admin") && password.equals("admin123")) {
            feedbackLabel.setTextFill(Color.GREEN);
            feedbackLabel.setText("Login successful!");
            switchToAdminHome();
        } else {
            feedbackLabel.setTextFill(Color.RED);
            feedbackLabel.setText("Invalid username or password.");
        }
    }

    private void switchToAdminHome() {
        try {
            // Load AdminHome.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHome.fxml"));
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Admin Home");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resetFields() {
        usernameField.clear();
        passwordField.clear();
        feedbackLabel.setText("");
    }
    
    @FXML
    private void handleBack() {
        try {
            // Load the previous scene (LoginPage or similar)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main Page.fxml")); // Adjust path as necessary
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
}*/


public class AdminLoginController
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
    
    @FXML
    void handleLogin() {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            feedbackLabel.setText("Email and Password are required.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            if (Administrator.validateAdminLogin(email, password)) {
                feedbackLabel.setText("Login successful! Redirecting...");
                feedbackLabel.setStyle("-fx-text-fill: green;");
                switchToAdminHome();
                // Navigate to Administrator Dashboard
            } else {
                feedbackLabel.setText("Invalid email or password. Please try again.");
                feedbackLabel.setStyle("-fx-text-fill: red;");
            }
        } catch (Exception e) {
            feedbackLabel.setText("An error occurred during login. Please try again.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }
    
    private void switchToAdminHome() {
        try {
            // Load AdminHome.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHome.fxml"));
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Admin Home");
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

