package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AssignCoursesController {

    @FXML
    private ComboBox<Course> coursesMenu = new ComboBox<>();
    @FXML
    private ComboBox<Course> teachersMenu = new ComboBox<>();



    @FXML
    protected void initialize() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        var sql = "SELECT courseId, name, description FROM Course";
        Statement statement = connectDB.createStatement();
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

        coursesMenu.setOnAction(e -> handleCourseSelection());
    }

    private void handleCourseSelection() {
        Course selectedCourse = coursesMenu.getValue();
        if (selectedCourse != null) {
            System.out.println("Selected Course ID: " + selectedCourse.getCourseId());
            System.out.println("Selected Course Name: " + selectedCourse.getName());
        }
    }
}