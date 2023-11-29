package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import javafx.stage.StageStyle;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;

public class AdminDashboardController implements  Initializable {
    @FXML
    public AnchorPane approveCoursesPane;
    @FXML
    private ComboBox<Course> approveCoursesMenu = new ComboBox<>();
    @FXML
    private ComboBox<Teacher> approveTeachersMenu = new ComboBox<Teacher>();
    @FXML
    public Button approveBtn;
    @FXML
    public Label approveCourseLabel;
    @FXML
    public CheckComboBox<Student> approveStudentsMenu = new CheckComboBox<Student>();

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
    private AnchorPane assignCoursesPane;

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

    @FXML
    private  AnchorPane adminAddStudentPane;
    @FXML
    private Label assignCourseLabel;
    @FXML
    private ComboBox<Course> coursesMenu = new ComboBox<>();
    @FXML
    private ComboBox<Teacher> teachersMenu = new ComboBox<Teacher>();

    @FXML
    private TextField addSName;
    @FXML
    private TextField addSRollNo;
    @FXML
    private TextField addSUsername;
    @FXML
    private TextField addSEmail;
    @FXML
    private TextField addSPassword;

    @FXML
    private Label addSLabel;

    @FXML
    private TextField offerCourseName;

    @FXML
    private TextArea offerCourseDesc;

    @FXML
    private Label offerCourseLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        adminName.setText("Welcome "+ ApplicationState.currentlyLoggedAdmin.getName() + " !");
    }
    public void dashboardPane(ActionEvent e){
        adminDashPane.setVisible(true);
        adminAddStudentPane.setVisible(false);
        adminAddCoursePane.setVisible(false);
        adminAddTeacherPane.setVisible(false);
        assignCoursesPane.setVisible(false);
        approveCoursesPane.setVisible(false);
    }

    public void addCoursePane(ActionEvent e){
        adminDashPane.setVisible(false);
        adminAddStudentPane.setVisible(false);
        adminAddCoursePane.setVisible(true);
        adminAddTeacherPane.setVisible(false);
        assignCoursesPane.setVisible(false);
        approveCoursesPane.setVisible(false);
    }

    public void addTeacherPane(ActionEvent e){
        adminAddStudentPane.setVisible(false);
        adminDashPane.setVisible(false);
        adminAddCoursePane.setVisible(false);
        adminAddTeacherPane.setVisible(true);
        assignCoursesPane.setVisible(false);
        approveCoursesPane.setVisible(false);
    }
    public void addStudentPane(){
        adminAddStudentPane.setVisible(true);
        adminDashPane.setVisible(false);
        adminAddCoursePane.setVisible(false);
        adminAddTeacherPane.setVisible(false);
        assignCoursesPane.setVisible(false);
        approveCoursesPane.setVisible(false);
    }
    public void addTeacherButton(ActionEvent e){
        String name, email,username,password;
        if(addTPName.getText().isBlank() == false && addTPEmail.getText().isBlank() == false && addTPUsername.getText().isBlank() == false && addTPPassword.getText().isBlank() == false){
            name = addTPName.getText();
            email = addTPEmail.getText();
            username = addTPUsername.getText();
            password = addTPPassword.getText();
            String msg = ApplicationState.currentlyLoggedAdmin.addTeacher(name,email,username,password);
            addTPLabel.setText(msg);
        }
        else{
            addTPLabel.setText("Fill all fields");
        }

    }

    public void addCourseButton(ActionEvent e){
        String id, name,desc;
        if(offerCourseName.getText().isBlank() == false && offerCourseDesc.getText().isBlank() == false ){
            name = offerCourseName.getText();
            desc = offerCourseDesc.getText();
            String msg = ApplicationState.currentlyLoggedAdmin.addCourse(name,desc);
            offerCourseLabel.setText(msg);
        }
        else{
            offerCourseLabel.setText("Fill all fields");
        }

    }
    public void addStudentButton(ActionEvent e){
        String name, email,username,password,rollNo;
        if(addSName.getText().isBlank() == false && addSRollNo.getText().isBlank() == false && addSEmail.getText().isBlank() == false && addSUsername.getText().isBlank() == false&& addSPassword.getText().isBlank() == false){
            name = addSName.getText();
            email = addSEmail.getText();
            username = addSUsername.getText();
            password = addSPassword.getText();
            rollNo = addSRollNo.getText();
            String msg = ApplicationState.currentlyLoggedAdmin.addStudent(name,email,username,password,rollNo);
            addSLabel.setText(msg);
        }
        else{
            addSLabel.setText("Fill all fields");
        }

    }

    public void cancelTeacherButton(ActionEvent e){
        addTPName.setText("");
        addTPEmail.setText("");
        addTPUsername.setText("");
        addTPPassword.setText("");

    }

    public void cancelStudentButton(ActionEvent e){
        addSName.setText("");
        addSEmail.setText("");
        addSUsername.setText("");
        addSPassword.setText("");
        addSRollNo.setText("");
    }

    public void cancelCourseButton(ActionEvent e){
        offerCourseName.setText("");
        offerCourseDesc.setText("");
    }
    private void switchToLoginScene(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("login.fxml"));
            Parent root = fxmlLoader.load();

            Stage studentStage = new Stage();  // Create a new Stage
            studentStage.initStyle(StageStyle.UNDECORATED);
            studentStage.setScene(new Scene(root, 520, 400));
            studentStage.show();
            Stage stage = (Stage) adminDashCancel.getScene().getWindow();
            stage.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public void Logout(ActionEvent e){
        switchToLoginScene();
    }


    public void assignCoursesPane(ActionEvent e) throws SQLException {
        String sql = "SELECT courseId, name, description FROM Course";
        coursesMenu.setOnAction(null);
        coursesMenu.setItems(ApplicationState.currentlyLoggedAdmin.getCourses(sql));
        coursesMenu.setOnAction(f -> {
            try {
                handleAssignCourseSelection();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        adminDashPane.setVisible(false);
        adminAddCoursePane.setVisible(false);
        adminAddTeacherPane.setVisible(false);
        assignCoursesPane.setVisible(true);
        approveCoursesPane.setVisible(false);
    }

    private void handleAssignCourseSelection() throws SQLException {
        Course selectedCourse = coursesMenu.getValue();
        int courseId = selectedCourse.getCourseId();
        String sql = "SELECT Teacher.id, Teacher.name, Teacher.username, Teacher.email, Teacher.password " +
                "FROM Teacher WHERE Teacher.id not in (select userid from TeacherCourse where courseId = " + courseId + ")";
        teachersMenu.setItems(ApplicationState.currentlyLoggedAdmin.getTeachers(sql));
    }

    public void assignAction(ActionEvent e) throws SQLException {
        String msg = ApplicationState.currentlyLoggedAdmin.assignCourseToTeacher(coursesMenu.getValue().getCourseId(), teachersMenu.getValue().getId());
        assignCourseLabel.setText(msg);
        teachersMenu.getSelectionModel().clearSelection();
        assignCoursesPane(null);
    }

    public void approveCoursesPane(ActionEvent e) throws SQLException {
        approveCoursesMenu.setOnAction(null);
        String sql = "SELECT courseId, name, description FROM Course";
        approveCoursesMenu.setItems(ApplicationState.currentlyLoggedAdmin.getCourses(sql));
        approveCoursesMenu.setOnAction(f -> {
            try {
                handleApproveCourseSelection();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        adminDashPane.setVisible(false);
        adminAddCoursePane.setVisible(false);
        adminAddTeacherPane.setVisible(false);
        assignCoursesPane.setVisible(false);
        approveCoursesPane.setVisible(true);
    }

    private void handleApproveCourseSelection() throws SQLException {
        approveTeachersMenu.setOnAction(null);
        Course selectedCourse = (Course) approveCoursesMenu.getValue();
        int courseId = selectedCourse.getCourseId();
        String sql = "SELECT Teacher.id, Teacher.name, Teacher.username, Teacher.email, Teacher.password " +
                "FROM Teacher WHERE Teacher.id in (select userid from TeacherCourse where courseId = " + courseId + ")";
        approveTeachersMenu.setItems(ApplicationState.currentlyLoggedAdmin.getTeachers(sql));
        approveTeachersMenu.setOnAction(f -> {
            try {
                handleApproveTeacherSelection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void handleApproveTeacherSelection() throws SQLException {
        Course selectedCourse = (Course) approveCoursesMenu.getValue();
        Teacher selectedTeacher = (Teacher) approveTeachersMenu.getValue();
        int courseId = selectedCourse.getCourseId();
        int teacherId = selectedTeacher.getId();/*
        List<Student> selectedStudents = new List<>();*/
        String sql = "SELECT Student.id, Student.name, Student.username, Student.email, Student.password, Student.rollNo " +
                "FROM Student WHERE Student.id in (SELECT userid FROM StudentTeacherCourse " +
                "WHERE teacherCourseid in (SELECT id FROM teacherCourse WHERE courseId = " + courseId + " and userid = " +teacherId + ") AND StudentTeacherCourse.approved = 0)";

        approveStudentsMenu.getItems().addAll(ApplicationState.currentlyLoggedAdmin.getStudents(sql));

    }

    public void approveAction(ActionEvent e) throws SQLException {
        ObservableList<Student> studentsList = approveStudentsMenu.getCheckModel().getCheckedItems();
        String msg = ApplicationState.currentlyLoggedAdmin.approveStudentApplication(approveCoursesMenu.getValue().getCourseId(), approveTeachersMenu.getValue().getId(), studentsList);
        approveCourseLabel.setText(msg);
        approveTeachersMenu.getSelectionModel().clearSelection();
        approveStudentsMenu.getCheckModel().clearChecks();
        approveCoursesPane(null);
    }
}