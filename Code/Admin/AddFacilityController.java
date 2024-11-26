package Admin;

import Member.*;
import Staff.*;
import application.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.IOException;

import javafx.event.ActionEvent;

public class AddFacilityController
{

    @FXML
    private TextField nameField;

    @FXML
    private TextField typeField;

    @FXML
    private TextField capacityField;

    @FXML
    private Label feedbackLabel;
    
    @FXML 
    private Button backButton;
    
    @FXML private Button addButton;
    @FXML private Button cancelButton;

    @FXML
    void handleAddButton(ActionEvent event) {
        String name = nameField.getText().trim();
        String type = typeField.getText().trim();
        String capacityText = capacityField.getText().trim();

        if (name.isEmpty() || type.isEmpty() || capacityText.isEmpty()) {
            feedbackLabel.setText("All fields are required.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try
        {
            int capacity = Integer.parseInt(capacityText);
            Facility facility = FacilityFactory.createFacility(0, name, type, "Available", capacity);
            Facility.insertFacility(facility);
            feedbackLabel.setText("Facility added successfully.");
            feedbackLabel.setStyle("-fx-text-fill: green;");
        } 
        
        catch (NumberFormatException e) 
        {
            feedbackLabel.setText("Invalid capacity. Please enter a numeric value.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
        }
        
        catch (Exception e) 
        {
            feedbackLabel.setText("An error occurred. Please try again.");
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

    private void clearForm() 
    {
        nameField.clear();
        typeField.clear();
        capacityField.clear();
        
    }
    

    // Ensure the connection is closed when the controller is destroyed
    public void close() 
    {
        SQLPersistenceHandler.getInstance().closeConnection();
    }
    
    @FXML
    private void handleBack()
    {
        try {
            // Load the previous scene (LoginPage or similar)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageFacility.fxml")); // Adjust path as necessary
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Manage Facility");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}

