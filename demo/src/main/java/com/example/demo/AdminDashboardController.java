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
import javafx.scene.control.cell.PropertyValueFactory;
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
    // ANCHORS
    @FXML
    private AnchorPane adminAddCoursePane;
    @FXML
    private AnchorPane adminDashPane;
    @FXML
    private AnchorPane adminAddTeacherPane;
    @FXML
    private AnchorPane assignCoursesPane;
    @FXML
    private  AnchorPane adminAddStudentPane;
    @FXML
    private AnchorPane ApproveApplications;

    // DASHBOARD VARIABLES
    @FXML
    private Button adminDashCancel; // LOG OUT BUTTON
    @FXML
    private Label adminName;

    // ADD TEACHER
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
    public Label approveCourseLabel;

    // ADD STUDENT
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

    // OFFER COURSE
    @FXML
    private TextField offerCourseName;
    @FXML
    private TextArea offerCourseDesc;
    @FXML
    private  TextField offerCourseId;
    @FXML
    private Label offerCourseLabel;

    // ASSIGN COURSE
    @FXML
    private Label assignCourseLabel;
    @FXML
    private ComboBox<Course> coursesMenu = new ComboBox<>();
    @FXML
    private ComboBox<Teacher> teachersMenu = new ComboBox<Teacher>();
    @FXML
    private ComboBox<courseSection> sectionMenu = new ComboBox<courseSection>();

    // APPROVE COURSES
    @FXML
    private TableView<approveCourses> approveCourseTable;
    @FXML
    private TableColumn<approveCourses,String> approveCourseStudent;
    @FXML
    private TableColumn<approveCourses,String> approveCourseCN;
    @FXML
    private TableColumn<approveCourses,Button> approveCourseApprove;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        adminName.setText("Welcome "+ ApplicationState.currentlyLoggedAdmin.getName() + " !");
    }
    public void dashboardPane(ActionEvent e){
        adminDashPane.setVisible(true);
        adminAddStudentPane.setVisible(false);
        adminAddTeacherPane.setVisible(false);
        adminAddCoursePane.setVisible(false);
        assignCoursesPane.setVisible(false);
        ApproveApplications.setVisible(false);
    }
    public void addCoursePane(ActionEvent e){
        adminDashPane.setVisible(false);
        adminAddStudentPane.setVisible(false);
        adminAddTeacherPane.setVisible(false);
        adminAddCoursePane.setVisible(true);
        assignCoursesPane.setVisible(false);
        ApproveApplications.setVisible(false);
    }
    public void addTeacherPane(ActionEvent e){
        adminDashPane.setVisible(false);
        adminAddStudentPane.setVisible(false);
        adminAddTeacherPane.setVisible(true);
        adminAddCoursePane.setVisible(false);
        assignCoursesPane.setVisible(false);
        ApproveApplications.setVisible(false);
    }
    public void addStudentPane(){
        adminDashPane.setVisible(false);
        adminAddStudentPane.setVisible(true);
        adminAddTeacherPane.setVisible(false);
        adminAddCoursePane.setVisible(false);
        assignCoursesPane.setVisible(false);
        ApproveApplications.setVisible(false);
    }
    public void assignCoursesPane(ActionEvent e) throws SQLException {
        adminDashPane.setVisible(false);
        adminAddStudentPane.setVisible(false);
        adminAddTeacherPane.setVisible(false);
        adminAddCoursePane.setVisible(false);
        assignCoursesPane.setVisible(true);
        ApproveApplications.setVisible(false);
        String sql = "SELECT courseId,courseName,courseDescription FROM Course";
        coursesMenu.setOnAction(null);
        coursesMenu.setItems(ApplicationState.currentlyLoggedAdmin.getCourses(sql));
        coursesMenu.setOnAction(f -> {
            try {
                handleAssignCourseSelection();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
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
        if(offerCourseName.getText().isBlank() == false && offerCourseDesc.getText().isBlank() == false && offerCourseId.getText().isBlank() == false ){
            name = offerCourseName.getText();
            desc = offerCourseDesc.getText();
            id = offerCourseId.getText();
            String msg = ApplicationState.currentlyLoggedAdmin.addCourse(name,desc,id);
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
        offerCourseId.setText("");
    }
    private void handleAssignCourseSelection() throws SQLException {
        Course selectedCourse = coursesMenu.getValue();
        String courseName = selectedCourse.getName();
        String sql = "select sectionName,courseName,teacherName from courseSection where courseName = '"+courseName+"';";
        sectionMenu.setItems(ApplicationState.currentlyLoggedAdmin.getCourseSections(sql));
        String sql2 = "SELECT t.* FROM TEACHER t LEFT JOIN (SELECT teacherName, COUNT(*) as courseCount FROM courseSection GROUP BY teacherName) c ON t.name = c.teacherName WHERE COALESCE(courseCount, 0) < 4;";
        teachersMenu.setItems(ApplicationState.currentlyLoggedAdmin.getTeachers(sql2));
    }
    public void assignAction(ActionEvent e) throws SQLException {
        Course selectedCourse = coursesMenu.getValue();
        Teacher selectedTeacher = teachersMenu.getValue();
        courseSection selectedSection = sectionMenu.getValue();
        if(selectedSection == null || selectedCourse == null || selectedCourse == null){
            assignCourseLabel.setText("Select all options");
        }
        else{
            String msg = ApplicationState.currentlyLoggedAdmin.assignCourseToTeacher(selectedCourse.getName(),selectedTeacher.getName(),selectedSection.getSectionName());
            assignCourseLabel.setText(msg);
        }
    }
    public void approveCourse(ActionEvent e){
        ApproveApplications.setVisible(true);
        adminDashPane.setVisible(false);
        adminAddCoursePane.setVisible(false);
        adminAddTeacherPane.setVisible(false);
        assignCoursesPane.setVisible(false);
        adminAddStudentPane.setVisible(false);
        ObservableList<approveCourses> apCList = FXCollections.observableArrayList();
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
                apCList.add(data);
            }
        } catch (Exception w) {
            w.printStackTrace();
        }
        approveCourseStudent.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        approveCourseCN.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        approveCourseApprove.setCellValueFactory(new PropertyValueFactory<>("button"));

        approveCourseTable.setItems(apCList);
    }
    private Button createApproveButton(String studentName,String courseName) {
        Button requestButton = new Button("Approve");
        requestButton.setOnAction(event -> handleApproveButtonClick(studentName,courseName,requestButton));
        return requestButton;
    }
    private void handleApproveButtonClick(String studentName,String courseName,Button _reqButton) {
        ApplicationState.currentlyLoggedAdmin.approveBtn(studentName,courseName);
        _reqButton.setText("Approved");
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
}