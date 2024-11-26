package Admin;

import Member.*;
import Staff.*;
import application.*;
import java.io.IOException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;



public class ViewMembershipController {

	@FXML
    private TextField memberIdField;

    @FXML
    private TableView<Membership> membershipTable;

    @FXML
    private TableColumn<Membership, Integer> colMembershipId;

    @FXML
    private TableColumn<Membership, Integer> colMemberId;

    @FXML
    private TableColumn<Membership, LocalDate> colStartDate;

    @FXML
    private TableColumn<Membership, LocalDate> colEndDate;

    @FXML
    private TableColumn<Membership, String> colStatus;
    
    @FXML
    private Button backButton;
    
    private ObservableList<Membership> memberships = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up the table columns to match Membership fields
        colMembershipId.setCellValueFactory(new PropertyValueFactory<>("membershipId"));
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Load all memberships when the view initializes
        loadAllMemberships();
    }

    private void loadAllMemberships() {
        memberships.clear();
        try {
            memberships.addAll(Membership.getAllMemberships());
            membershipTable.setItems(memberships);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load memberships.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String memberId = memberIdField.getText();
        memberships.clear();

        try
        {
            if (memberId.isEmpty()) 
            {
                memberships.addAll(Membership.getAllMemberships());
            }
            
            else
            {
                memberships.addAll(Membership.getMembershipsByMemberId(Integer.parseInt(memberId)));
            }
            membershipTable.setItems(memberships);
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter a valid numeric Member ID.", Alert.AlertType.WARNING);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to search memberships.", Alert.AlertType.ERROR);
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
    private void handleBack() {
        try {
            // Load the previous scene (LoginPage or similar)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageMembership.fxml")); // Adjust path as necessary
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Manage Membership");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
