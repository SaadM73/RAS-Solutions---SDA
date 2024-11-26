package Admin;
import Member.*;
import Staff.*;
import application.*;
import java.io.IOException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CancelMembershipController 
{

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
    @FXML private Button cancelButton;
    @FXML private Button backButton;
   @FXML  private Label feedbackLabel;


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

   private void loadAllMemberships() 
   {
       memberships.clear();
       try 
       {
           memberships.addAll(Membership.getAllMemberships());
           membershipTable.setItems(memberships);
       } 
       catch (Exception e) 
       {
           e.printStackTrace();
           showAlert("Error", "Failed to load memberships.", Alert.AlertType.ERROR);
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
    private void handleCancel() {
        String memberIdText = memberIdField.getText().trim();

        if (memberIdText.isEmpty()) {
            feedbackLabel.setText("Member ID is required.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            int memberId = Integer.parseInt(memberIdText);
            boolean isCancelled = Membership.cancelMembership(memberId);

            if (isCancelled) {
                feedbackLabel.setText("Membership cancelled successfully.");
                feedbackLabel.setStyle("-fx-text-fill: green;");
            } else {
                feedbackLabel.setText("Member not found or already inactive.");
                feedbackLabel.setStyle("-fx-text-fill: red;");
            }
        } catch (NumberFormatException e) {
            feedbackLabel.setText("Invalid Member ID. Please enter a numeric value.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            feedbackLabel.setText("An error occurred. Please try again.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    } // Ensure this closing brace exists

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
