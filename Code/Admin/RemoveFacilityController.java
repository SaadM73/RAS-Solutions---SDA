package Admin;

import Member.*;
import Staff.*;
import application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RemoveFacilityController {

    @FXML
    private TableView<Facility> facilityTable;

    @FXML
    private TableColumn<Facility, Integer> colfacilityId;

    @FXML
    private TableColumn<Facility, String> colfacilityName;

    @FXML
    private TableColumn<Facility, String> colfacilitytype;

    @FXML
    private TableColumn<Facility, String> colfacilityavailability;

    @FXML
    private TableColumn<Facility, Integer> colcapacity;

    @FXML
    private TextField facilityIdField;

    @FXML
    private Label feedbackLabel;

    @FXML
    private Button searchButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button backButton;

    private final ObservableList<Facility> facilities = FXCollections.observableArrayList();

    @FXML
    public void initialize() 
    {
        colfacilityId.setCellValueFactory(new PropertyValueFactory<>("facilityId"));
        colfacilityName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colfacilitytype.setCellValueFactory(new PropertyValueFactory<>("type"));
        colfacilityavailability.setCellValueFactory(new PropertyValueFactory<>("availabilityStatus"));
        colcapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        // Load all facilities initially
        loadFacilities();
    }

    private void loadFacilities() {
        try {
            List<Facility> allFacilities = Facility.getAllFacilities();
            facilities.clear();
            facilities.addAll(allFacilities);
            facilityTable.setItems(facilities);
        } catch (SQLException e) {
            feedbackLabel.setText("Error loading facilities.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String facilityIdText = facilityIdField.getText();

        if (facilityIdText.isEmpty()) {
            feedbackLabel.setText("Please enter a Facility ID to search.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            int facilityId = Integer.parseInt(facilityIdText);

            Facility facility = Facility.getFacilityById(facilityId);
            facilities.clear();

            if (facility != null) 
            {
                facilities.add(facility);
                facilityTable.setItems(facilities);
                feedbackLabel.setText("Facility found.");
                feedbackLabel.setStyle("-fx-text-fill: green;");
            }
            
            else 
            {
                feedbackLabel.setText("No facility found with the given ID.");
                feedbackLabel.setStyle("-fx-text-fill: red;");
            }
        }
        
        catch (NumberFormatException e)
        {
            feedbackLabel.setText("Facility ID must be a numeric value.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
        }
        
        catch (SQLException e)
        {
            feedbackLabel.setText("Error searching for the facility.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRemove(ActionEvent event)
    {
        Facility selectedFacility = facilityTable.getSelectionModel().getSelectedItem();

        if (selectedFacility == null) {
            feedbackLabel.setText("Please select a facility to remove.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try 
        {
            Facility.deleteFacility(selectedFacility.getFacilityId());
            facilities.remove(selectedFacility);
            feedbackLabel.setText("Facility removed successfully.");
            feedbackLabel.setStyle("-fx-text-fill: green;");
        } catch (Exception e) {
            feedbackLabel.setText("Error removing the facility.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageFacility.fxml")); // Adjust the path if necessary
            AnchorPane root = loader.load();

            Stage currentStage = (Stage) backButton.getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Manage Facilities");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
