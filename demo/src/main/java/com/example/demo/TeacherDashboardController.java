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
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class TeacherDashboardController implements Initializable {

    @FXML
    public ComboBox<courseSection> attendanceMenu;

    @FXML
    public DatePicker attendanceDate;
    @FXML
    public Button PopualateStudents;
    @FXML
    private TableView<attndStudentTable> attendanceTable;

    @FXML
    private TableColumn<attndStudentTable,String> RollNo;
    @FXML
    private TableColumn<attndStudentTable,Button> markedAttendance;
    @FXML
    private TableColumn<attndStudentTable,Button> absentAttendance;
    @FXML
    private AnchorPane teacherDashPane;
    @FXML
    private AnchorPane attendancePane;
    @FXML
    private Button teacherDashCancel;
    @FXML
    private Label teacherName;
    public void initialize(URL url, ResourceBundle resourceBundle) {
        teacherName.setText("Welcome "+ ApplicationState.currentlyLoggedTeacher.getName() + " !");
        attendanceDate.setValue(LocalDate.now());
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

    public void dashboardPane(ActionEvent e){
        teacherDashPane.setVisible(true);
        attendancePane.setVisible(false);
    }

    public void attendancePane(ActionEvent e) throws SQLException {
        teacherDashPane.setVisible(false);
        attendancePane.setVisible(true);

        String sql = "Select courseName, sectionName, teacherName from coursesection cs where cs.teachername = '" + ApplicationState.currentlyLoggedTeacher.getName() + "'";
        attendanceMenu.setItems(ApplicationState.currentlyLoggedTeacher.getCourseSections(sql));
    }

    /*public void markAttendance(ActionEvent e) {
        ObservableList<approveCourses> mAList = FXCollections.observableArrayList();
        String getCourses = "SELECT studentName,courseName FROM requestingCourse;";

        try {
            Statement statement = ApplicationState.connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(getCourses);

            while (queryResult.next()) {
                String student = queryResult.getString("studentName");
                String course = queryResult.getString("courseName");

                Button requestButton = createApproveButton(student,course);

                // Create CourseData object and add it to the list
                approveCourses data = new approveCourses(student,course,requestButton);
                mAList.add(data);
            }
        } catch (Exception w) {
            w.printStackTrace();
        }
        StudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        RollNo.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        markedAttendance.setCellValueFactory(new PropertyValueFactory<>("button"));
        attendanceTable.setItems(mAList);
    }*/
   /* private Button createApproveButton(String studentName,String courseName) {
        Button requestButton = new Button("Approve");
        requestButton.setOnAction(event -> handleApproveButtonClick(studentName,courseName,requestButton));
        return requestButton;
    }
    private void handleApproveButtonClick(String studentName,String courseName,Button _reqButton) {
        ApplicationState.currentlyLoggedAdmin.approveBtn(studentName,courseName);
        _reqButton.setText("Approved");
    }
*/
    public void ShowStudents(ActionEvent event) throws SQLException {
        ObservableList<attndStudentTable> attndncdLst = FXCollections.observableArrayList();
        Statement statement = ApplicationState.connectDB.createStatement();
        courseSection courseSectionSelected = attendanceMenu.getValue();
        String sql = "Select ss.id, studentName, rollNo from studentsections ss inner join student s on ss.studentname = s.rollno\n" +
                "where ss.courseSec = '" + courseSectionSelected.getSectionName() + "' and ss.courseName = '"  + courseSectionSelected.getCourseName() + "'";
        ResultSet queryResult = statement.executeQuery(sql);

        while (queryResult.next()) {
            int studentSectionId = queryResult.getInt("id");
            String student = queryResult.getString("studentName");
            String rollNo = queryResult.getString("rollNo");

            Button presentButton = createPresentButton(studentSectionId, attendanceDate.getValue(), true);
            Button absentButton = createPresentButton(studentSectionId, attendanceDate.getValue(), false);

            // Create CourseData object and add it to the list
            attndStudentTable tbl = new attndStudentTable(0,student,rollNo,presentButton,absentButton);
            attndncdLst.add(tbl);
        }

        RollNo.setCellValueFactory(new PropertyValueFactory<>("rollNo"));
        markedAttendance.setCellValueFactory(new PropertyValueFactory<>("presentButton"));
        absentAttendance.setCellValueFactory(new PropertyValueFactory<>("absentButton"));
        attendanceTable.setItems(attndncdLst);
    }

    private Button createPresentButton(int studentSectionId, LocalDate attendanceDate, boolean isPresent) {
        Button atndncButton = new Button("Present");
        atndncButton.setOnAction(event -> {
            try {
                handlePresentButtonClick(studentSectionId,attendanceDate, isPresent,atndncButton);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return atndncButton;
    }

    private void handlePresentButtonClick(int studentSectionId,LocalDate attendanceDate,boolean isPresent, Button atndncButton) throws SQLException {
        ApplicationState.currentlyLoggedTeacher.MarkAttendance(studentSectionId,attendanceDate, isPresent);
        atndncButton.setText("Marked");
    }

}
