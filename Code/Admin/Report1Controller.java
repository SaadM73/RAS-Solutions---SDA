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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

import java.io.IOException;
import java.io.PrintWriter;


public class Report1Controller {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button backButton;

    @FXML
    private Label feedbackLabel;

    @FXML
    private TableView<Member> MembershipTable;

    @FXML
    private TableColumn<Member, Integer> colmemberID;

    @FXML
    private TableColumn<Member, String> colmemberName;

    @FXML
    private TableColumn<Member, String> colemail;

    @FXML
    private TableColumn<Member, String> colphoneNumber;

    @FXML
    private TableColumn<Member, String> colmembershipType;

    @FXML
    private TextField memberIdField;

    @FXML
    private Button searchButton;

    @FXML
    private Button downloadButton;


    private final ObservableList<Member> members = FXCollections.observableArrayList();

    @FXML
    public void initialize() 
    {
        // Initialize the table columns with Member properties
        colmemberID.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colmemberName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colphoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colmembershipType.setCellValueFactory(new PropertyValueFactory<>("membershipType"));

        // Load all members with active memberships initially
        loadActiveMemberships();
    }

    private void loadActiveMemberships() {
        members.clear();
        try {
            members.addAll(SQLPersistenceHandler.getInstance().getMembersWithActiveMemberships());
            MembershipTable.setItems(members);
        } catch (Exception e) {
            feedbackLabel.setText("Error loading active memberships.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    @FXML
    void handleSearch(ActionEvent event) {
        String memberIdText = memberIdField.getText();

        // Validate input
        if (memberIdText.isEmpty()) {
            feedbackLabel.setText("Please enter a Member ID.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            int memberId = Integer.parseInt(memberIdText);
            members.clear();

            // Search for the specific member ID
            Member member = Member.getMemberByID(memberId);
            if (member != null) {
                members.add(member);
                feedbackLabel.setText("Search results displayed.");
                feedbackLabel.setStyle("-fx-text-fill: green;");
            } else {
                feedbackLabel.setText("No member found with the provided ID.");
                feedbackLabel.setStyle("-fx-text-fill: red;");
            }

            MembershipTable.setItems(members);
        } catch (NumberFormatException e) {
            feedbackLabel.setText("Invalid Member ID. Please enter a numeric value.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            feedbackLabel.setText("Error searching for Member ID.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    public void handleDownload() {
        // Open a FileChooser dialog
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        // Show save dialog
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            saveTableDataToCSV(file);
        }
    }

    // Method to save TableView data to a CSV file
    private void saveTableDataToCSV(File file)
    {
        try (PrintWriter writer = new PrintWriter(file))
        {
            StringBuilder csvContent = new StringBuilder();

            // Add the header row
            csvContent.append("Member ID,Name,Email,Phone Number,Membership Type\n");

            // Get data from the TableView
            ObservableList<Member> members = MembershipTable.getItems();
            for (Member member : members) 
            {
                csvContent.append(member.getMemberId()).append(",");
                csvContent.append(member.getName()).append(",");
                csvContent.append(member.getEmail()).append(",");
                csvContent.append(member.getPhoneNumber()).append(",");
                csvContent.append(member.getMembershipType()).append("\n");
            }

            // Write to the file
            writer.write(csvContent.toString());

            // Success feedback
            System.out.println("CSV file saved successfully: " + file.getAbsolutePath());
        }
        
        catch (Exception e)
        {
            System.err.println("Error saving CSV file.");
            e.printStackTrace();
        }
    }


    @FXML
    void handleBack(ActionEvent event) {
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
