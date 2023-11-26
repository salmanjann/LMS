package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboardController implements  Initializable {
    @FXML
    private Button adminDashCancel;
    @FXML
    private Label adminName;
    @FXML
    private AnchorPane adminAddCoursePane;
    @FXML
    private AnchorPane adminDashPane;
    @FXML
    private AnchorPane adminAddTeacherPane;

    @FXML
    private TextField addTPName;

    @FXML
    private TextField addTPEmail;

    @FXML
    private TextField addTPUsername;

    @FXML
    private PasswordField addTPPassword;

    @FXML
    private Label addTPLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        adminName.setText("Welcome "+ ApplicationState.currentlyLoggedIn.getName() + " !");
    }
    public void dashboardPane(ActionEvent e){
        adminDashPane.setVisible(true);
        adminAddCoursePane.setVisible(false);
        adminAddTeacherPane.setVisible(false);
    }

    public void addCoursePane(ActionEvent e){
        adminDashPane.setVisible(false);
        adminAddCoursePane.setVisible(true);
        adminAddTeacherPane.setVisible(false);
    }

    public void addTeacherPane(ActionEvent e){
        adminDashPane.setVisible(false);
        adminAddCoursePane.setVisible(false);
        adminAddTeacherPane.setVisible(true);
    }

    public void addTeacherButton(ActionEvent e){
        String name, email,username,password;
        if(addTPName.getText().isBlank() == false && addTPEmail.getText().isBlank() == false && addTPUsername.getText().isBlank() == false && addTPPassword.getText().isBlank() == false){
            name = addTPName.getText();
            email = addTPEmail.getText();
            username = addTPUsername.getText();
            password = addTPPassword.getText();
            String msg = ApplicationState.currentlyLoggedIn.addTeacher(name,email,username,password);
            addTPLabel.setText(msg);
        }
        else{
            addTPLabel.setText("Fill all fields");
        }

    }

    public void cancelTeacherButton(ActionEvent e){
        addTPName.setText("");
        addTPEmail.setText("");
        addTPUsername.setText("");
        addTPPassword.setText("");

    }
    private void switchToLoginScene(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("login.fxml"));
            Parent root = fxmlLoader.load();

            Stage studentStage = new Stage();  // Create a new Stage
            studentStage.initStyle(StageStyle.UNDECORATED);
            studentStage.setScene(new Scene(root, 520, 400));
            studentStage.show();
            Stage stage = (Stage) adminDashCancel.getScene().getWindow();
            stage.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public void Logout(ActionEvent e){
        switchToLoginScene();
    }
}
