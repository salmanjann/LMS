package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StudentDashboardController {
    @FXML
    private Button studentDashCancel;
    public void cancelAction(ActionEvent e){
        Stage stage = (Stage) studentDashCancel.getScene().getWindow();
        stage.close();
    }
}
