package Admin;

import Member.*;
import Staff.*;
import application.*;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ReportController {
    @FXML private ComboBox<String> reportTypeComboBox;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private ComboBox<String> facilityComboBox;
    @FXML private Button generateButton;
    @FXML private Button exportButton;
    @FXML private Button resetButton;
    @FXML private TextArea reportOutputArea;
    @FXML private Label feedbackLabel;
    @FXML private Button backButton;

    @FXML
    public void initialize() {
        // Initialize facility options
        facilityComboBox.setItems(FXCollections.observableArrayList("Swimming Pool", "Gym", "Sauna", "Golf Course"));

        // Set button actions
        generateButton.setOnAction(event -> generateReport());
        exportButton.setOnAction(event -> exportReport());
        resetButton.setOnAction(event -> resetFilters());
    }

    private void generateReport() {
        String reportType = reportTypeComboBox.getValue();
        String startDate = startDatePicker.getValue() != null ? startDatePicker.getValue().toString() : null;
        String endDate = endDatePicker.getValue() != null ? endDatePicker.getValue().toString() : null;
        String facility = facilityComboBox.getValue();

        if (reportType == null || startDate == null || endDate == null) {
            feedbackLabel.setTextFill(Color.RED);
            feedbackLabel.setText("Please fill out all required fields!");
            return;
        }

        // Simulate report generation (Replace this with actual logic)
        String report = String.format("Generating %s Report\nStart Date: %s\nEnd Date: %s\nFacility: %s\n",
                reportType, startDate, endDate, facility != null ? facility : "All Facilities");
        reportOutputArea.setText(report);

        feedbackLabel.setTextFill(Color.GREEN);
        feedbackLabel.setText("Report generated successfully!");
    }

    private void exportReport() {
        // Simulate exporting the report (Replace this with actual logic)
        if (reportOutputArea.getText().isEmpty()) {
            feedbackLabel.setTextFill(Color.RED);
            feedbackLabel.setText("No report to export!");
            return;
        }

        feedbackLabel.setTextFill(Color.GREEN);
        feedbackLabel.setText("Report exported successfully!");
    }

    private void resetFilters() {
        reportTypeComboBox.getSelectionModel().clearSelection();
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        facilityComboBox.getSelectionModel().clearSelection();
        reportOutputArea.clear();
        feedbackLabel.setText("");
    }
    
    @FXML
    private void handleBack() {
        try {
            // Load the previous scene (LoginPage or similar)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHome.fxml")); // Adjust path as necessary
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
