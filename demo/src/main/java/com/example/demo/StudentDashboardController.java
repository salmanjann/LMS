package com.example.demo;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
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
import javafx.util.Callback;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class StudentDashboardController implements Initializable {
    @FXML
    public AnchorPane showAttendance;
    @FXML
    public TableView attendanceTable;
    @FXML
    public TableColumn attendanceDate;
    @FXML
    public TableColumn attendanceStatus;
    // ANCHORS
    @FXML
    private AnchorPane studentDashPane;
    @FXML
    private AnchorPane enStudentsPane;
    @FXML
    private Button studentDashLogout;
    @FXML
    private  AnchorPane submitFeedbackPane;
    @FXML
    private AnchorPane FeedbackForm;
    @FXML
    private ComboBox<StudentSection> attSectionCourseMenu;
    // DASHBORD
    @FXML
    private Label studentName;
    @FXML
    private Label studentEmail;
    @FXML
    private Label studentRollNo;

    // ENROLL COURSES
    @FXML
    private TableView<enStudentTable> enCourseTable;
    @FXML
    private TableColumn<enStudentTable,String> enTableCourseName;
    @FXML
    private TableColumn<enStudentTable,Button> enTableCourseRequest;

    // SUBMIT FEEDBACK
    @FXML
    private TableView<FeedbackTable> submitFeedbackTable;
    @FXML
    private TableColumn<FeedbackTable,String> feedbackCourse;
    @FXML
    private TableColumn<FeedbackTable,String> feedbackStatus;
    @FXML
    private TableColumn<FeedbackTable,Button> feedbackSubmit;

    // FEEDBACK FORM
    // Q1 RADIO BUTTONS
    @FXML
    private RadioButton Q1Yes;
    @FXML
    private RadioButton Q1No;
    @FXML
    private RadioButton Q1NotSure;
    // Q2 RADIO BUTTONS
    @FXML
    private RadioButton Q2Yes;
    @FXML
    private RadioButton Q2No;
    // Q3 TEXT AREA
    @FXML
    private TextArea   Q3Text;
    private String  FeedbackCourse ;
    @FXML
    private  Label FeedbackAlertLabel;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        studentName.setText(ApplicationState.currentlyLoggedStudent.getName());
        studentEmail.setText(ApplicationState.currentlyLoggedStudent.getEmail());
        studentRollNo.setText(ApplicationState.currentlyLoggedStudent.getRollNo());
    }
    public void EnrollCourse(){
        studentDashPane.setVisible(false);
        enStudentsPane.setVisible(true);
        submitFeedbackPane.setVisible(false);
        showAttendance.setVisible(false);

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
    public  void feedbackPane(){
        submitFeedbackPane.setVisible(true);
        studentDashPane.setVisible(false);
        enStudentsPane.setVisible(false);
        showAttendance.setVisible(false);

        ApplicationState.currentlyLoggedStudent.checkFeedbackTable();
        ObservableList<FeedbackTable> feedbackList = FXCollections.observableArrayList();
        String getCourses = "select courseName,status from FeedbackStatus where studentName = '"+ApplicationState.currentlyLoggedStudent.getRollNo()+"';";

        try {
            Statement statement = ApplicationState.connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(getCourses);

            while (queryResult.next()) {
                String courseName = queryResult.getString("courseName");
                String status = queryResult.getString("status");
                Button submitButton = createSubmitButton(courseName,status);
                if(status.equalsIgnoreCase("Submitted")){
                    submitButton.setVisible(false);
                }
                // Create CourseData object and add it to the list
                FeedbackTable courseData = new FeedbackTable(courseName,status,submitButton);
                feedbackList.add(courseData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        feedbackCourse.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        feedbackStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        feedbackSubmit.setCellValueFactory(new PropertyValueFactory<>("button"));

        submitFeedbackTable.setItems(feedbackList);
    }
    private Button createSubmitButton(String courseName,String status) {
        Button requestButton = new Button("Submit");
        requestButton.setOnAction(event -> handleSubmitButtonClick(courseName,status,requestButton));
        return requestButton;
    }
    // Your button click event handler
    private void handleSubmitButtonClick(String courseName,String status,Button _reqButton) {
        FeedbackForm.setVisible(true);
        FeedbackCourse = courseName;
    }
    public void FeedbackSubmit(ActionEvent e){
        if(Q3Text.getText().isBlank() == true){
            FeedbackAlertLabel.setText("*Fill all fields");
        }
        else{
            FeedbackForm.setVisible(false);
            String Q1 = "Yes";
            if(Q1Yes.isSelected())
                Q1 = "Yes";
            else if(Q1No.isSelected())
                Q1 = "No";
            else if(Q1NotSure.isSelected())
                Q1 = "Not Sure";

            String Q2= "No";
            if(Q2Yes.isSelected())
                Q2 = "Yes";
            else if(Q2No.isSelected())
                Q2 = "No";

            String insertIntoFeedback = "insert into FeedbackFormSubmissions(studentName,courseName,Q1,Q2,comments) value ('"+ApplicationState.currentlyLoggedStudent.getRollNo()+"','"+FeedbackCourse+"','"+Q1+"','"+Q2+"','"+Q3Text.getText()+"');";
            try {
                Statement statement = ApplicationState.connectDB.createStatement();
                statement.executeUpdate(insertIntoFeedback);
            }
            catch (Exception w){
                w.printStackTrace();
            }
            String updateStatus = "UPDATE FeedbackStatus SET status = 'Submitted' WHERE studentName = '"+ApplicationState.currentlyLoggedStudent.getRollNo()+"' AND courseName = '"+FeedbackCourse+"';";
            try {
                Statement statement = ApplicationState.connectDB.createStatement();
                statement.executeUpdate(updateStatus);
            }
            catch (Exception w){
                w.printStackTrace();
            }
            Q1Yes.setSelected(true);
            Q1No.setSelected(false);
            Q1NotSure.setSelected(false);
            Q2Yes.setSelected(true);
            Q2No.setSelected(false);
            Q3Text.setText("");
            FeedbackAlertLabel.setText("");
        }
    }
    public void FeedbackCancel(ActionEvent e){
        FeedbackForm.setVisible(false);
        Q1Yes.setSelected(true);
        Q1No.setSelected(false);
        Q1NotSure.setSelected(false);
        Q2Yes.setSelected(true);
        Q2No.setSelected(false);
        Q3Text.setText("");
    }
    public void dashboardPane(ActionEvent e){
        studentDashPane.setVisible(true);
        enStudentsPane.setVisible(false);
        submitFeedbackPane.setVisible(false);
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


    public void showAttendance(ActionEvent event) throws SQLException {
        studentDashPane.setVisible(false);
        enStudentsPane.setVisible(false);
        submitFeedbackPane.setVisible(false);
        showAttendance.setVisible(true);
        ObservableList<StudentSection> stdntSectionList = FXCollections.observableArrayList();

        String sql = "SELECT id, courseName, courseSec from studentsections where studentName = '" + ApplicationState.currentlyLoggedStudent.getRollNo() + "';";
        Statement stmt = ApplicationState.connectDB.createStatement();
        ResultSet queryResult = stmt.executeQuery(sql);
        while(queryResult.next()) {
            StudentSection studentSectionItem = new StudentSection(queryResult.getInt("id"),
                    queryResult.getString("courseSec"),queryResult.getString("courseName"));
            stdntSectionList.add(studentSectionItem);
        }
        attSectionCourseMenu.setItems(stdntSectionList);
    }

    public void ShowData(ActionEvent event) throws SQLException {
        int id = attSectionCourseMenu.getValue().getId();
        String sql = "SELECT attendanceDate, IsPresent FROM lms.attendance where studentSectionId = " + id + ";";
        ObservableList<Attendance> attendanceList = FXCollections.observableArrayList();
        Statement stmt = ApplicationState.connectDB.createStatement();
        ResultSet queryResult = stmt.executeQuery(sql);
        while(queryResult.next()) {
            Attendance atndnd = new Attendance(queryResult.getDate("attendanceDate").toLocalDate(),
                    queryResult.getBoolean("IsPresent"));
            attendanceList.add(atndnd);
        }

        attendanceDate.setCellValueFactory(new PropertyValueFactory<>("attendanceDate"));
        attendanceStatus.setCellValueFactory(new PropertyValueFactory<>("attendanceStatus"));
        attendanceTable.setItems(attendanceList);


    }
}
