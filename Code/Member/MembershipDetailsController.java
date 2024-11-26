package Member;

import Admin.*;
import Staff.*;
import application.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.classfile.components.ClassPrinter.Node;


public class MembershipDetailsController {

    @FXML
    private Label membershipTypeLabel;

    @FXML
    private Label startDateLabel;

    @FXML
    private Label endDateLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Label feedbackLabel;
    
    @FXML Button backButton;


    @FXML
    public void initialize() {
        if (!SessionManager.isLoggedIn())
        {
            feedbackLabel.setText("No member is currently logged in.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            System.out.println("SessionManager: No logged-in member.");
            return;
        }

        Member loggedInMember = SessionManager.getInstance().getLoggedInMember();
        System.out.println("Logged-in Member: " + loggedInMember.getName());

        try 
        {
            int memberId = loggedInMember.getMemberId();
            Membership membership = Membership.getMembershipDetails(memberId);
            Member mem = Member.getMemberByID(memberId);
           
            if
            (membership != null)
            {
                membershipTypeLabel.setText(mem.getMembershipType());
                startDateLabel.setText(membership.getStartDate().toString());
                endDateLabel.setText(membership.getEndDate().toString());
                statusLabel.setText(membership.getStatus());
            } 
            else {
                feedbackLabel.setText("Membership details not found.");
                feedbackLabel.setStyle("-fx-text-fill: red;");
                System.out.println("No membership found for Member ID: " + memberId);
            }
        } catch (Exception e) {
            feedbackLabel.setText("An error occurred while fetching membership details.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    
    	@FXML
    private void handleBack() {
    		try {
                // Load the previous scene (LoginPage or similar)
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MemberDashboard.fxml")); // Adjust path as necessary
                AnchorPane root = loader.load();

                // Get the current stage and set the new scene
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

