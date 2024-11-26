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
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class Report2Controller {

    @FXML
    private TableView<Booking> BookingTable;

    @FXML
    private TableColumn<Booking, Integer> colbookingID;

    @FXML
    private TableColumn<Booking, Integer> colmemberID;
    
    @FXML
    private TableColumn<Booking, Integer> colfacilityID; 

    @FXML
    private TableColumn<Booking, Date> coldate;

    @FXML
    private TableColumn<Booking, Time> coltime;

    @FXML
    private TableColumn<Booking, String> colstatus;

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

    private final ObservableList<Booking> bookings = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        // Initialize TableView columns with BookingDetails properties
        colbookingID.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colmemberID.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colfacilityID.setCellValueFactory(new PropertyValueFactory<>("facilityId"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        coltime.setCellValueFactory(new PropertyValueFactory<>("timeSlot"));
        colstatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        loadAllBookings();
    }
    
    private void loadAllBookings() {
        bookings.clear();
        try {
            List<Booking> bookingList = Booking.getAllBookings();
            bookings.addAll(bookingList);
            BookingTable.setItems(bookings);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load bookings.", Alert.AlertType.ERROR);
        }
    }
    
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
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
            bookings.clear();

            // Search bookings by facility ID
            List<Booking> results = Booking.getBookingsByFacility(facilityId);
            if (results.isEmpty()) {
                feedbackLabel.setText("No bookings found for the provided Facility ID.");
                feedbackLabel.setStyle("-fx-text-fill: red;");
            } else {
                bookings.addAll(results);
                feedbackLabel.setText("Search results displayed.");
                feedbackLabel.setStyle("-fx-text-fill: green;");
            }

            BookingTable.setItems(bookings);
        } catch (NumberFormatException e) {
            feedbackLabel.setText("Invalid Facility ID. Please enter a numeric value.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            feedbackLabel.setText("Error searching for bookings.");
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
            csvContent.append("Booking ID,Member ID,Booking Date,Time Slot,Status\n");

            // Add table data
            for (Booking booking : BookingTable.getItems()) {
                csvContent.append(booking.getBookingId()).append(",");
                csvContent.append(booking.getMemberId()).append(",");
                csvContent.append(booking.getBookingDate()).append(",");
                csvContent.append(booking.getTimeSlot()).append(",");
                csvContent.append(booking.getStatus()).append("\n");
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
