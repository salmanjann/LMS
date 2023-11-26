package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class TeacherDashboardController implements Initializable {
    @FXML
    private Button teacherDashCancel;
    @FXML
    private Label teacherName;
    public void initialize(URL url, ResourceBundle resourceBundle) {
        teacherName.setText("Welcome "+ ApplicationState.currentlyLoggedTeacher.getName() + " !");
    }
    private void switchToLoginScene(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("login.fxml"));
            Parent root = fxmlLoader.load();

            Stage studentStage = new Stage();  // Create a new Stage
            studentStage.initStyle(StageStyle.UNDECORATED);
            studentStage.setScene(new Scene(root, 520, 400));
            studentStage.show();
            Stage stage = (Stage) teacherDashCancel.getScene().getWindow();
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
