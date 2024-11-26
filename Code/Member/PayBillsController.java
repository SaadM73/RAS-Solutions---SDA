package Member;

import Admin.*;
import Staff.*;
import application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PayBillsController
{

    @FXML
    private TableView<Payment> billsTable;

    @FXML
    private TableColumn<Payment, Integer> colPaymentId;

    @FXML
    private TableColumn<Payment, Integer> colMemberId;

    @FXML
    private TableColumn<Payment, Integer> colBookingId;

    @FXML
    private TableColumn<Payment, Double> colAmountDue;

    @FXML
    private TableColumn<Payment, String> colStatus;

    @FXML
    private TextField paymentIDField;

    @FXML
    private Label feedbackLabel;

    @FXML
    private Button backButton;

    @FXML
    private Button payNowButton;

    private final ObservableList<Payment> payments = FXCollections.observableArrayList();

    @FXML
    public void initialize()
    {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colAmountDue.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Load all bills
        loadAllBills();
    }

    private void loadAllBills() {
        payments.clear();
        try {
            List<Payment> allPayments = Payment.getAllPayments();
            payments.addAll(allPayments);
            billsTable.setItems(payments);
        } 
        
        catch (SQLException e) {
        	
            e.printStackTrace();
            feedbackLabel.setText("Error loading bills.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handlePayNow(ActionEvent event) {
        String paymentIdText = paymentIDField.getText().trim();

        if (paymentIdText.isEmpty()) {
            feedbackLabel.setText("Please enter a Payment ID.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            int paymentId = Integer.parseInt(paymentIdText);

            // Mark the payment as paid in the database
            boolean success = Payment.processPayment(paymentId);

            if (success) {
                feedbackLabel.setText("Payment successful!");
                feedbackLabel.setStyle("-fx-text-fill: green;");
                loadAllBills(); // Refresh the table
            } else {
                feedbackLabel.setText("Payment failed. Please check the Payment ID.");
                feedbackLabel.setStyle("-fx-text-fill: red;");
            }
        } catch (NumberFormatException e) {
            feedbackLabel.setText("Invalid Payment ID. Please enter a numeric value.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            e.printStackTrace();
            feedbackLabel.setText("Error processing payment.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MemberDashboard.fxml")); // Adjust the path as needed
            AnchorPane root = loader.load();

            // Switch scene
            Stage currentStage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Member Dashboard");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
