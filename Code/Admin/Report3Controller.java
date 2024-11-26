package Admin;

import Member.*;
import Staff.*;
import application.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class Report3Controller {

    @FXML
    private TableView<Facility> BookingTable;

    @FXML
    private TableColumn<Facility, Integer> colfacilityID;

    @FXML
    private TableColumn<Facility, String> colfacilityName;

    @FXML
    private TableColumn<Facility, String> coltype;

    @FXML
    private TableColumn<Facility, String> colstatus;

    @FXML
    private TableColumn<Facility, Integer> colcapacity;

    @FXML
    private TextField facilityIdField;

    @FXML
    private Button backButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button downloadButton;

    @FXML
    private Label feedbackLabel;

    private final ObservableList<Facility> facilities = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        // Initialize TableView columns with Facility properties
        colfacilityID.setCellValueFactory(new PropertyValueFactory<>("facilityId"));
        colfacilityName.setCellValueFactory(new PropertyValueFactory<>("name"));
        coltype.setCellValueFactory(new PropertyValueFactory<>("type"));
        colstatus.setCellValueFactory(new PropertyValueFactory<>("availabilityStatus"));
        colcapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        // Load all facilities initially
        loadAllFacilities();
    }

    private void loadAllFacilities() {
        facilities.clear();
        try {
            // Fetch facilities ordered by availability status
            facilities.addAll(Facility.getFacilitiesOrderedByAvailability());
            BookingTable.setItems(facilities);
        } catch (Exception e) {
            feedbackLabel.setText("Error loading facilities.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    @FXML
    void handleSearch(ActionEvent event) {
        String facilityIdText = facilityIdField.getText();

        // Validate input
        if (facilityIdText.isEmpty()) {
            feedbackLabel.setText("Please enter a Facility ID.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            int facilityId = Integer.parseInt(facilityIdText);
            facilities.clear();

            // Search for a specific facility by ID
            Facility facility = Facility.getFacilityById(facilityId);
            if (facility != null) {
                facilities.add(facility);
                feedbackLabel.setText("Search results displayed.");
                feedbackLabel.setStyle("-fx-text-fill: green;");
            } else {
                feedbackLabel.setText("No facility found with the provided ID.");
                feedbackLabel.setStyle("-fx-text-fill: red;");
            }

            BookingTable.setItems(facilities);
        } catch (NumberFormatException e) {
            feedbackLabel.setText("Invalid Facility ID. Please enter a numeric value.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            feedbackLabel.setText("Error searching for facility.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    @FXML
    void handleDownload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            saveTableDataToCSV(file);
        }
    }

    private void saveTableDataToCSV(File file) {
        try (PrintWriter writer = new PrintWriter(file)) {
            StringBuilder csvContent = new StringBuilder();

            // Add the header row
            csvContent.append("Facility ID,Facility Name,Type,Availability Status,Capacity\n");

            // Add table data
            for (Facility facility : BookingTable.getItems()) {
                csvContent.append(facility.getFacilityId()).append(",");
                csvContent.append(facility.getName()).append(",");
                csvContent.append(facility.getType()).append(",");
                csvContent.append(facility.getAvailabilityStatus()).append(",");
                csvContent.append(facility.getCapacity()).append("\n");
            }

            writer.write(csvContent.toString());
            feedbackLabel.setText("CSV file downloaded successfully.");
            feedbackLabel.setStyle("-fx-text-fill: green;");
        } catch (Exception e) {
            feedbackLabel.setText("Error saving CSV file.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    @FXML
    void handleBack(ActionEvent event)
    {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GenerateReport.fxml")); // Adjust the path if necessary
            AnchorPane root = loader.load();

            Stage currentStage = (Stage) backButton.getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Generate Report");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
