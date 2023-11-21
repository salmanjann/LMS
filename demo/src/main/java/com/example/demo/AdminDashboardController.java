package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminDashboardController {
    @FXML
    private Button adminDashCancel;
    public void cancelAction(ActionEvent e){
        Stage stage = (Stage) adminDashCancel.getScene().getWindow();
        stage.close();
    }
}
