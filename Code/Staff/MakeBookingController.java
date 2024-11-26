package Staff;

import Member.*;
import Admin.*;
import application.*;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class MakeBookingController {

    @FXML
    private TextField memberIdField;

    @FXML
    private ComboBox<String> facilityComboBox;

    @FXML
    private DatePicker bookingDateField;

    @FXML
    private ComboBox<String> timeSlotComboBox;

    @FXML
    private TextArea notesField;
    
    @FXML 
    private Button backButton;
    
    @FXML private Button submitButton;
    @FXML private Button cancelButton;
    
    @FXML private Label feedbackLabel;
    
    @FXML
    public void initialize() 
    {
        // Populate ComboBoxes with example data (can be fetched dynamically later)
        facilityComboBox.getItems().addAll("Swimming Pool", "Fitness Gym", "Sauna Room", "Golf Course" , "Badminton Court" , "Cricket Ground" , "Tennis Court" ," Conference Room" , "Basketball Court" , "Football Ground", "Kids Play Area", "Yoga Hall", "Table Tennis Room"," Cycling Track", "Squash Court", "Library", "Running Track"," Spa Room"," Boxing Ring"," Archery Range");
        timeSlotComboBox.getItems().addAll("08:00 AM - 09:00 AM", "09:00 AM - 10:00 AM", "10:00 AM - 11:00 AM");
    }

    @FXML
    void handleSubmit(ActionEvent event) 
    {
        String memberIdText = memberIdField.getText();
        String facilityText = facilityComboBox.getValue();
        LocalDate bookingDateText = bookingDateField.getValue();
        String timeSlotText = timeSlotComboBox.getValue();
        String notes = notesField.getText();

        // Validate required fields
        if (memberIdText.isEmpty() || facilityText == null || timeSlotText == null || bookingDateText == null) {
            feedbackLabel.setText("All fields are required.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Validate if Member ID is numeric
        if (!isNumeric(memberIdText)) {
            feedbackLabel.setText("Invalid Member ID. Please enter a valid numeric value.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            // Parse inputs
            int memberId = Integer.parseInt(memberIdText);

            // Validate Member ID exists in the database
            if (!Member.isValidMemberId(memberId)) {
                feedbackLabel.setText("Invalid Member ID. Member does not exist.");
                feedbackLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            int facilityId = Facility.FacilityNametoID(facilityText);

            // Convert LocalDate to java.sql.Date
            java.sql.Date bookingDate = java.sql.Date.valueOf(bookingDateText);

            // Extract the start time from the selected time slot
            String[] timeParts = timeSlotText.split(" - ");
            if (timeParts.length < 2) {
                throw new IllegalArgumentException("Invalid time slot format.");
            }

            // Parse the start time into a valid java.sql.Time object
            java.sql.Time timeSlot = java.sql.Time.valueOf(LocalTime.parse(timeParts[0], DateTimeFormatter.ofPattern("hh:mm a")).toString() + ":00");

            // Insert booking
            Booking booking = new Booking(0, memberId, facilityId, bookingDate, timeSlot, "Confirmed");
            Booking.insertBooking(booking);

            // Success feedback
            feedbackLabel.setText("Booking made successfully.");
            feedbackLabel.setStyle("-fx-text-fill: green;");
        } 
        
        catch (IllegalArgumentException e) 
        {
            feedbackLabel.setText("Invalid date or time format.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
        } 
        
        catch (Exception e)
        {
            feedbackLabel.setText("An error occurred while making the booking. Please try again.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }


    private boolean isNumeric(String str)
    {
        if (str == null || str.isEmpty())
        {
            return false;
        }
        try 
        {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e)
        {
            return false;
        }
    }

    @FXML
    void handleCancel(ActionEvent event) 
    {
        System.out.println("Booking process canceled.");
        // Add logic to reset or navigate back to the previous page
    }
    
    @FXML
    private void handleBack()
    {
        try
        {
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


