package application;

import Member.*;
import Staff.*;
import Admin.*;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainPageController {

    @FXML
    private Button memberButton;

    @FXML
    private Button staffButton;

    @FXML
    private Button adminButton;

    @FXML
    void handleAdminButton(ActionEvent event)
    {
        System.out.println("Administrator Interface button clicked!");
        try {
            // Load AdminHome.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/AdminLogin.fxml"));
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) adminButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Admin Login");
            currentStage.show();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    
    }

    @FXML
    void handleMemberButton(ActionEvent event) {
        System.out.println("Member Interface button clicked!");
        try {
            // Load AdminHome.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Member/MemberLogin.fxml"));
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) memberButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Member Login");
            currentStage.show();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    @FXML
    void handleStaffButton(ActionEvent event) {
        System.out.println("Staff Interface button clicked!");
        try {
            // Load AdminHome.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Staff/StaffLogin.fxml"));
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) staffButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Staff Login");
            currentStage.show();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}
