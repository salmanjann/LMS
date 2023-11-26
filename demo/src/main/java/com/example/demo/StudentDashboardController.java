package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class StudentDashboardController implements Initializable {
    @FXML
    private Button studentDashCancel;
    @FXML
    private Label studentName;
    @FXML
    private AnchorPane applyCoursesPane;

    @FXML
    private Label applyCourseLabel;
    @FXML
    private ComboBox<Course> coursesMenu = new ComboBox<>();
    @FXML
    private ComboBox<Teacher> teachersMenu = new ComboBox<>();
    @FXML
    private Button applyBtn;
    @FXML
    private AnchorPane studentDashPane;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        studentName.setText("Welcome "+ ApplicationState.currentlyLoggedStudent.getName() + " !");
    }
    private void switchToLoginScene(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("login.fxml"));
            Parent root = fxmlLoader.load();

            Stage studentStage = new Stage();  // Create a new Stage
            studentStage.initStyle(StageStyle.UNDECORATED);
            studentStage.setScene(new Scene(root, 520, 400));
            studentStage.show();
            Stage stage = (Stage) studentDashCancel.getScene().getWindow();
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
        studentDashPane.setVisible(true);
        applyCoursesPane.setVisible(false);
    }

    public void applyCourses(ActionEvent e) throws SQLException {
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

        applyCoursesPane.setVisible(true);
    }

    private void handleCourseSelection() throws SQLException{
        Course selectedCourse = coursesMenu.getValue();

        int courseId = selectedCourse.getCourseId();
        var sql = "SELECT Teacher.id, Teacher.name, Teacher.username, Teacher.email, Teacher.password " +
                "FROM Teacher WHERE Teacher.id in (select userid from TeacherCourse where courseId = " + courseId + ")";

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
    }

    public void applyAction(ActionEvent e) throws SQLException {
        String msg = ApplicationState.currentlyLoggedStudent.studentApplyCourse(coursesMenu.getValue().getCourseId(), teachersMenu.getValue().getId());
        applyCourseLabel.setText(msg);
        teachersMenu.getSelectionModel().clearSelection();
        applyCourses(null);
    }
}
