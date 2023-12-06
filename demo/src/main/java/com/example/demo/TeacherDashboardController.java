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
    public ComboBox<CourseSectionAttendance> attendanceMenu;
    @FXML
    public ComboBox<courseSection> viewFeedbackSections;
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
    @FXML
    private Label teacherEmail;
    @FXML
    private  AnchorPane viewFeedback;
    @FXML
    private TableView<viewFeedbackTable> viewFeedbackTable;
    @FXML
    private TableColumn<viewFeedbackTable,String> StudentCol;
    @FXML
    private TableColumn<viewFeedbackTable,String> Q1Col;
    @FXML
    private TableColumn<viewFeedbackTable,String> Q2Col;
    @FXML
    private TableColumn<viewFeedbackTable,String> Q3Col;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        teacherName.setText(ApplicationState.currentlyLoggedTeacher.getName());
        teacherEmail.setText(ApplicationState.currentlyLoggedTeacher.getEmail());
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
        viewFeedback.setVisible(false);

    }

    public void attendancePane(ActionEvent e) throws SQLException {
        teacherDashPane.setVisible(false);
        attendancePane.setVisible(true);
        viewFeedback.setVisible(false);

        String sql = "Select courseName, sectionName, teacherName from coursesection cs where cs.teachername = '" + ApplicationState.currentlyLoggedTeacher.getName() + "'";
        attendanceMenu.setItems(ApplicationState.currentlyLoggedTeacher.getCourseSectionAttendance(sql));
    }
    public void viewFeedbackPane(ActionEvent e) throws SQLException {
        teacherDashPane.setVisible(false);
        attendancePane.setVisible(false);
        viewFeedback.setVisible(true);
        String sql = "Select courseName, sectionName, teacherName from coursesection cs where cs.teachername = '" + ApplicationState.currentlyLoggedTeacher.getName() + "'";
        viewFeedbackSections.setItems(ApplicationState.currentlyLoggedTeacher.getCourseSections(sql));
    }

    public void ShowStudents(ActionEvent event) throws SQLException {
        ObservableList<attndStudentTable> attndncdLst = FXCollections.observableArrayList();
        Statement statement = ApplicationState.connectDB.createStatement();
        CourseSectionAttendance courseSectionSelected = attendanceMenu.getValue();
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
        if(!isPresent)
            atndncButton.setText("Absent");
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
        try {
            ApplicationState.currentlyLoggedTeacher.MarkAttendance(studentSectionId, attendanceDate, isPresent);
            atndncButton.setText("Marked");
        }
        catch (SQLException sqe)
        {
            Alert alerts = new Alert(Alert.AlertType.INFORMATION,"Attendance already marked.", ButtonType.OK);
            alerts.showAndWait();
            atndncButton.setText("Marked");
        }
    }
    public void ShowFeedback(ActionEvent event) throws SQLException {
        ObservableList<viewFeedbackTable> feedbackList = FXCollections.observableArrayList();
        Statement statement = ApplicationState.connectDB.createStatement();
        courseSection courseSectionSelected = viewFeedbackSections.getValue();
        String sql = "SELECT fs.studentName, fs.Q1, fs.Q2, fs.comments FROM FeedbackFormSubmissions fs JOIN studentSections ss ON fs.studentName = ss.studentName AND fs.courseName = ss.courseName WHERE ss.courseSec = '"+courseSectionSelected.getSectionName()+"' AND ss.courseName = '"+courseSectionSelected.getCourseName()+"';";
        ResultSet queryResult = statement.executeQuery(sql);

        while (queryResult.next()) {
            String rollNo = queryResult.getString(1);
            String Q1 = queryResult.getString(2);
            String Q2 = queryResult.getString(3);
            String Q3 = queryResult.getString(4);

            // Create CourseData object and add it to the list
            viewFeedbackTable feedback= new viewFeedbackTable(rollNo,Q1,Q2,Q3);
            feedbackList.add(feedback);
        }

        StudentCol.setCellValueFactory(new PropertyValueFactory<>("rollNo"));
        Q1Col.setCellValueFactory(new PropertyValueFactory<>("Q1"));
        Q2Col.setCellValueFactory(new PropertyValueFactory<>("Q2"));
        Q3Col.setCellValueFactory(new PropertyValueFactory<>("Q3"));

        viewFeedbackTable.setItems(feedbackList);
    }
}
