package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TeacherDashboardController {
    @FXML
    private Button teacherDashCancel;
    public void cancelAction(ActionEvent e){
        Stage stage = (Stage) teacherDashCancel.getScene().getWindow();
        stage.close();
    }
}
