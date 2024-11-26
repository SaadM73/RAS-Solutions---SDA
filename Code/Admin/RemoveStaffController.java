package Admin;

import Member.*;
import Staff.*;
import application.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RemoveStaffController {

    @FXML
    private TextField staffIDField;

    @FXML
    private TableView<Staff> staffTable;

    @FXML
    private TableColumn<Staff, Integer> colstaffId;

    @FXML
    private TableColumn<Staff, String> colstaffName;

    @FXML
    private TableColumn<Staff, String> colrole;

    @FXML
    private TableColumn<Staff, String> colemail;

    @FXML
    private TableColumn<Staff, String> colphone;
    
    @FXML
    private Button backButton;
    
    @FXML 
    private Button deleteButton;
    
    @FXML private Label feedbackLabel;

    private final ObservableList<Staff> staff = FXCollections.observableArrayList();
    @FXML
    public void initialize() {
        // Set up the columns for the table
    	colstaffId.setCellValueFactory(new PropertyValueFactory<>("staffId"));
    	colstaffName.setCellValueFactory(new PropertyValueFactory<>("name"));
    	colrole.setCellValueFactory(new PropertyValueFactory<>("role"));
    	colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
    	colphone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        // Load all facilities initially
        loadStaff();
    }
    
    private void loadStaff() {
        try {
            List<Staff> staffs = Staff.getAllStaff();
            staff.clear();
            staff.addAll(staffs);
            staffTable.setItems(staff);
        } catch (Exception e) {
            feedbackLabel.setText("Error loading facilities.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleDelete(ActionEvent event) {
        Staff selectedStaff = staffTable.getSelectionModel().getSelectedItem();

        if (selectedStaff == null) {
            feedbackLabel.setText("Please select a facility to remove.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try 
        {
            Staff.deleteStaff(selectedStaff.getStaffId());
            staff.remove(selectedStaff);
            feedbackLabel.setText("Facility removed successfully.");
            feedbackLabel.setStyle("-fx-text-fill: green;");
        } catch (Exception e) {
            feedbackLabel.setText("Error removing the facility.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
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



