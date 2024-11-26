package Staff;

import Member.*;
import Admin.*;
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
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class ViewBookingsController {

    @FXML
    private TextField memberIdField;

    @FXML
    private TableView<Booking> bookingsTable;

    @FXML
    private TableColumn<Booking, Integer> colBookingId;

    @FXML
    private TableColumn<Booking, Integer> colMemberId;

    @FXML
    private TableColumn<Booking, Integer> colFacilityId; // Optional, replace with facilityName if preferred

    @FXML
    private TableColumn<Booking, Date> colBookingDate;

    @FXML
    private TableColumn<Booking, Time> colTimeSlot;

    @FXML
    private TableColumn<Booking, String> colStatus;
    
    @FXML private Button backButton;

    private final ObservableList<Booking> bookings = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up table columns
        colBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colFacilityId.setCellValueFactory(new PropertyValueFactory<>("facilityId"));
        colBookingDate.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        colTimeSlot.setCellValueFactory(new PropertyValueFactory<>("timeSlot"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Load all bookings initially
        loadAllBookings();
    }

    private void loadAllBookings() {
        bookings.clear();
        try {
            List<Booking> bookingList = Booking.getAllBookings();
            bookings.addAll(bookingList);
            bookingsTable.setItems(bookings);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load bookings.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String memberIdText = memberIdField.getText().trim();
        if (memberIdText.isEmpty()) {
            loadAllBookings();
            return;
        }

        try {
            int memberId = Integer.parseInt(memberIdText);
            List<Booking> filteredBookings = Booking.getAllBookingsByMember(memberId);
            bookings.clear();
            bookings.addAll(filteredBookings);
            bookingsTable.setItems(bookings);
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Member ID must be numeric.", Alert.AlertType.WARNING);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to fetch bookings for the given Member ID.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    @FXML
    private void handleBack() {
        try {
            // Load the previous scene (LoginPage or similar)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffMain.fxml")); // Adjust path as necessary
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Staff Main");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
