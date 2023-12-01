package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class StudentDashboardController implements Initializable {
    // ANCHORS
    @FXML
    private AnchorPane studentDashPane;
    @FXML
    private AnchorPane enStudentsPane;
    @FXML
    private Button studentDashLogout;

    // DASHBORD
    @FXML
    private Label studentName;

    // ENROLL COURSES
    @FXML
    private TableView<enStudentTable> enCourseTable;
    @FXML
    private TableColumn<enStudentTable,String> enTableCourseName;
    @FXML
    private TableColumn<enStudentTable,Button> enTableCourseRequest;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        studentName.setText("Welcome "+ ApplicationState.currentlyLoggedStudent.getName() + " !");
    }
    public void EnrollCourse(){
        studentDashPane.setVisible(false);
        enStudentsPane.setVisible(true);
        ObservableList<enStudentTable> enList = FXCollections.observableArrayList();
        String getCourses = "SELECT c.courseName FROM Course c LEFT JOIN studentSections s ON c.courseName = s.courseName AND s.studentName = '"+ApplicationState.currentlyLoggedStudent.getRollNo()+"' LEFT JOIN requestingCourse r ON c.courseName = r.courseName AND r.studentName = '"+ApplicationState.currentlyLoggedStudent.getRollNo()+"' WHERE s.studentName IS NULL AND r.studentName IS NULL;";

        try {
            Statement statement = ApplicationState.connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(getCourses);

            while (queryResult.next()) {
                String courseName = queryResult.getString("courseName");
                Button requestButton = createRequestButton(courseName);

                // Create CourseData object and add it to the list
                enStudentTable courseData = new enStudentTable(courseName,requestButton);
                enList.add(courseData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        enTableCourseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        enTableCourseRequest.setCellValueFactory(new PropertyValueFactory<>("requestButton"));

        enCourseTable.setItems(enList);
    }
    // Create a Button for the Request column
    private Button createRequestButton(String courseName) {
        Button requestButton = new Button("Request");
        requestButton.setOnAction(event -> handleRequestButtonClick(courseName,requestButton));
        return requestButton;
    }
    // Your button click event handler
    private void handleRequestButtonClick(String courseName,Button _reqButton) {
        ApplicationState.currentlyLoggedStudent.requestCourse(courseName);
        _reqButton.setText("Requested");
    }
    public void dashboardPane(ActionEvent e){
        studentDashPane.setVisible(true);
        enStudentsPane.setVisible(false);
    }
    private void switchToLoginScene(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("login.fxml"));
            Parent root = fxmlLoader.load();

            Stage studentStage = new Stage();  // Create a new Stage
            studentStage.initStyle(StageStyle.UNDECORATED);
            studentStage.setScene(new Scene(root, 520, 400));
            studentStage.show();
            Stage stage = (Stage) studentDashLogout.getScene().getWindow();
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
