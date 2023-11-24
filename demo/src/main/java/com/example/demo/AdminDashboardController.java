package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AdminDashboardController {
    @FXML
    private Button adminDashCancel;

    @FXML
    private Button AssignCoursesBtn;
    public void cancelAction(ActionEvent e){
        Stage stage = (Stage) adminDashCancel.getScene().getWindow();
        stage.close();
    }

    public void coursesAction(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("AssignCoursesView.fxml"));
        Parent root = fxmlLoader.load();

        Stage teacherStage = new Stage();  // Create a new Stage
        teacherStage.initStyle(StageStyle.UNDECORATED);
        teacherStage.setScene(new Scene(root, 520, 400));
        teacherStage.show();
    }
}
