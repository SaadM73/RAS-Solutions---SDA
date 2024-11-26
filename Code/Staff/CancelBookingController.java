package Staff;

import Member.*;
import Admin.*;
import application.*;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CancelBookingController 
{

    @FXML
    private TextField bookingIdField;
    
    @FXML
    private Button backButton;
    
    @FXML private Button cancelBookingButton;
    
    @FXML private Label feedbackLabel;

    @FXML
    void handleCancelBooking(ActionEvent event)
    {
        String bookingId = bookingIdField.getText().trim();

        if (bookingId.isEmpty()) {
            feedbackLabel.setText("Booking ID is required.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            int bookingID = Integer.parseInt(bookingId);
            boolean isCancelled = Booking.cancelBooking(bookingID);

            if (isCancelled) {
                feedbackLabel.setText("Booking cancelled successfully.");
                feedbackLabel.setStyle("-fx-text-fill: green;");
            } else {
                feedbackLabel.setText("Booking not found .");
                feedbackLabel.setStyle("-fx-text-fill: red;");
            }
        } catch (NumberFormatException e) {
            feedbackLabel.setText("Invalid Booking ID. Please enter a numeric value.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            feedbackLabel.setText("An error occurred. Please try again.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    @FXML
    void handleReset(ActionEvent event) {
        bookingIdField.clear();
        System.out.println("Form Reset.");
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
