package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

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
        name = addTPName.getText();
        email = addTPEmail.getText();
        username = addTPUsername.getText();
        password = addTPPassword.getText();
        String msg = ApplicationState.currentlyLoggedIn.addTeacher(name,email,username,password);
        addTPLabel.setText(msg);
    }

    public void cancelTeacherButton(ActionEvent e){

    }
    public void cancelAction(ActionEvent e){
        Stage stage = (Stage) adminDashCancel.getScene().getWindow();
        stage.close();
    }
}
