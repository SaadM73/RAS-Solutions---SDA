package Member;

import Admin.*;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RequestRefundController {

    @FXML
    private TableView<Payment> paymentsTable;

    @FXML
    private TableColumn<Payment, Integer> colPaymentId;

    @FXML
    private TableColumn<Payment, Integer> colBookingId;

    @FXML
    private TableColumn<Payment, Double> colAmountPaid;

    @FXML
    private TextArea refundReasonField;

    @FXML
    private TextField paymentIDField;

    @FXML
    private Label feedbackLabel;

    @FXML
    private Button submitRefundButton;

    @FXML
    private Button backButton;

    private ObservableList<Payment> payments = FXCollections.observableArrayList();

    @FXML
    public void initialize()
    {
        // Set up the columns for the payments table
    	colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colAmountPaid.setCellValueFactory(new PropertyValueFactory<>("amount"));

        // Load all payments into the table
        loadPayments();
    }

    private void loadPayments() 
    {
        try
        {
            List<Payment> allPayments = Payment.getAllPayments();
            payments.clear();
            payments.addAll(allPayments);
            paymentsTable.setItems(payments);
        } 
        
        catch (SQLException e)
        {
            feedbackLabel.setText("Error loading payments.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSubmitRefund(ActionEvent event)
    {
        String paymentIdText = paymentIDField.getText();
        String refundReason = refundReasonField.getText();

        // Get the refund amount (for simplicity, hardcoding or calculating from payment details)
        double refundAmount = calculateRefundAmount(Integer.parseInt(paymentIdText));

        if (paymentIdText.isEmpty()) 
        {
            feedbackLabel.setText("Payment ID is required.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            return;
        }
        
        if (refundReason.isEmpty())
        {
            feedbackLabel.setText("Reason for refund is required.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try 
        {
            int paymentId = Integer.parseInt(paymentIdText);

            // Check if payment exists
            if (!Payment.isValidPaymentId(paymentId))
            {
                feedbackLabel.setText("Invalid Payment ID. Please try again.");
                feedbackLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            // Submit refund request
            Refund.requestRefund(paymentId,refundAmount, refundReason);

            feedbackLabel.setText("Refund request submitted successfully!");
            feedbackLabel.setStyle("-fx-text-fill: green;");
            refundReasonField.clear();
            paymentIDField.clear();
        } 
        
        catch (NumberFormatException e) 
        {
            feedbackLabel.setText("Payment ID must be numeric.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            feedbackLabel.setText("Error submitting refund request.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    private double calculateRefundAmount(int paymentId) 
    {        
        try {
            Payment payment = Payment.getPaymentByID(paymentId);
            return payment.getAmount();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0.0;
        }
    }


    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MemberDashboard.fxml")); // Adjust to your path
            AnchorPane root = loader.load();

            Stage currentStage = (Stage) backButton.getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Member Dashboard");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
