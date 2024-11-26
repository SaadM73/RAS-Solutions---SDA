package Admin;

import Staff.*;
import application.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;

public class ViewStaffController {

    @FXML
    private TableView<Staff> staffTable;

    @FXML
    private TableColumn<Staff, Integer> colStaffId;

    @FXML
    private TableColumn<Staff, String> colName;

    @FXML
    private TableColumn<Staff, String> colRole;

    @FXML
    private TableColumn<Staff, String> colEmail;

    @FXML
    private TableColumn<Staff, String> colPhone;

    @FXML
    private Button backButton;

    // ObservableList to hold staff data
    private ObservableList<Staff> staff = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up the table columns to match Membership fields
    	colStaffId.setCellValueFactory(new PropertyValueFactory<>("staffId"));
    	colName.setCellValueFactory(new PropertyValueFactory<>("name"));
    	colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
    	colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    	colPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        // Load all memberships when the view initializes
        loadAllMemberships();
    }

    private void loadAllMemberships() {
        staff.clear();
        try {
            staff.addAll(Staff.getAllStaff());
            staffTable.setItems(staff);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load Staff.", Alert.AlertType.ERROR);
        }
    }
    
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleBack(ActionEvent event)
    {
        try
        {
            // Navigate back to the previous screen (e.g., Admin Dashboard)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/AdminHome.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Dashboard");
            stage.show();
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}
