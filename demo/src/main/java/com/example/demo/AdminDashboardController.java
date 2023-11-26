package com.example.demo;

import javafx.collections.FXCollections;
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

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AdminDashboardController implements  Initializable {
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
    private Label assignCourseLabel;
    @FXML
    private ComboBox<Course> coursesMenu = new ComboBox<>();
    @FXML
    private ComboBox<Teacher> teachersMenu = new ComboBox<Teacher>();
    @FXML
    private Button assignBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        adminName.setText("Welcome "+ ApplicationState.currentlyLoggedAdmin.getName() + " !");
    }
    public void dashboardPane(ActionEvent e){
        adminDashPane.setVisible(true);
        adminAddCoursePane.setVisible(false);
        adminAddTeacherPane.setVisible(false);
        assignCoursesPane.setVisible(false);
    }

    public void addCoursePane(ActionEvent e){
        adminDashPane.setVisible(false);
        adminAddCoursePane.setVisible(true);
        adminAddTeacherPane.setVisible(false);
        assignCoursesPane.setVisible(false);
    }

    public void addTeacherPane(ActionEvent e){
        adminDashPane.setVisible(false);
        adminAddCoursePane.setVisible(false);
        adminAddTeacherPane.setVisible(true);
        assignCoursesPane.setVisible(false);
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

    public void cancelTeacherButton(ActionEvent e){
        addTPName.setText("");
        addTPEmail.setText("");
        addTPUsername.setText("");
        addTPPassword.setText("");

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
        coursesMenu.setOnAction(null);

        var sql = "SELECT courseId, name, description FROM Course";
        Statement statement = ApplicationState.connectDB.createStatement();
        ResultSet queryResult = statement.executeQuery(sql);

        ObservableList<Course> courses = FXCollections.observableArrayList();

        while (queryResult.next()) {
            Course course = new Course(
                    queryResult.getInt("courseId"),
                    queryResult.getString("name"),
                    queryResult.getString("description")
            );
            courses.add(course);
        }


        coursesMenu.setItems(courses);

        coursesMenu.setOnAction(f -> {
            try {
                handleCourseSelection();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        assignCoursesPane.setVisible(true);
    }

    private void handleCourseSelection() throws SQLException{
        Course selectedCourse = coursesMenu.getValue();

        int courseId = selectedCourse.getCourseId();
        var sql = "SELECT Teacher.id, Teacher.name, Teacher.username, Teacher.email, Teacher.password " +
                "FROM Teacher WHERE Teacher.id not in (select userid from TeacherCourse where courseId = " + courseId + ")";

        Statement statement = ApplicationState.connectDB.createStatement();
        ResultSet queryResult = statement.executeQuery(sql);

        ObservableList<Teacher> teachers = FXCollections.observableArrayList();

        while (queryResult.next()) {
            Teacher teacher = new Teacher(
                    queryResult.getInt("id"),
                    queryResult.getString("name"),
                    queryResult.getString("username"),
                    queryResult.getString("email"),
                    queryResult.getString("password")
            );
            teachers.add(teacher);
        }

        teachersMenu.setItems(teachers);

        teachersMenu.setOnAction(g -> handleTeacherSelection());
    }

    public void assignAction(ActionEvent e) throws SQLException {
        String msg = ApplicationState.currentlyLoggedAdmin.assignCourseToTeacher(coursesMenu.getValue().getCourseId(), teachersMenu.getValue().getId());
        assignCourseLabel.setText(msg);
        teachersMenu.getSelectionModel().clearSelection();
        assignCoursesPane(null);
    }

    private void handleTeacherSelection() {
        Teacher selectedTeacher = teachersMenu.getValue();
        if (selectedTeacher != null) {

        }
    }
}
