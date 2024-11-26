package Admin;

import Member.*;
import Staff.*;
import application.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MaintainFacilitiesController {

    @FXML
    private TextField facilityIdField;

    @FXML
    private DatePicker scheduledDateField;

    @FXML
    private TextArea taskDescriptionField;

    @FXML
    private TextField assignedStaffIdField;
    
    @FXML
    private Button backButton;
    
    @FXML private Label feedbackLabel;


    @FXML
    void handleReset(ActionEvent event) {
        facilityIdField.clear();
        scheduledDateField.setValue(null);
        taskDescriptionField.clear();
        assignedStaffIdField.clear();
        System.out.println("Form Reset.");
    }

    @FXML
    void handleSubmit(ActionEvent event) {
        String facilityIdText = facilityIdField.getText();
        LocalDate scheduledDate = scheduledDateField.getValue();
        String taskDescription = taskDescriptionField.getText();
        String assignedStaffIdText = assignedStaffIdField.getText();

        // Validate required fields
        if (facilityIdText.isEmpty() || scheduledDate == null || taskDescription.isEmpty() || assignedStaffIdText.isEmpty()) {
            feedbackLabel.setText("All fields are required.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Validate Facility ID is numeric
        if (!isNumeric(facilityIdText)) {
            feedbackLabel.setText("Invalid Facility ID. Please enter a valid numeric value.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Validate Staff ID is numeric
        if (!isNumeric(assignedStaffIdText)) {
            feedbackLabel.setText("Invalid Staff ID. Please enter a valid numeric value.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            // Parse inputs
            int facilityId = Integer.parseInt(facilityIdText);
            int assignedStaffId = Integer.parseInt(assignedStaffIdText);

            // Validate Facility ID exists in the database
            if (!Facility.isValidFacilityId(facilityId)) {
                feedbackLabel.setText("Invalid Facility ID. Facility does not exist.");
                feedbackLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            // Validate Staff ID exists in the database
            if (!Staff.isValidStaffId(assignedStaffId)) {
                feedbackLabel.setText("Invalid Staff ID. Staff member does not exist.");
                feedbackLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            // Convert LocalDate to java.sql.Date
            java.sql.Date sqlScheduledDate = java.sql.Date.valueOf(scheduledDate);

            // Insert facility maintenance record
            FacilityMaintenance maintenance = new FacilityMaintenance(0, facilityId, sqlScheduledDate, taskDescription, assignedStaffId, "Scheduled");
            FacilityMaintenance.maintainFacility(maintenance);

            // Success feedback
            feedbackLabel.setText("Facility maintenance task scheduled successfully.");
            feedbackLabel.setStyle("-fx-text-fill: green;");
        } catch (Exception e) {
            feedbackLabel.setText("An error occurred while scheduling the task. Please try again.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    
    @FXML
    private void handleBack() {
        try {
            // Load the previous scene (LoginPage or similar)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageFacility.fxml")); // Adjust path as necessary
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Admin Home");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
