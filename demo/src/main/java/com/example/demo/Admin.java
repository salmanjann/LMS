package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

public class Admin extends User {
    public Admin() {
        super();
    }

    // No attributes now
    public Admin(int _id, String _name, String _username, String _email, String _password) {
        super(_id, _name, _username, _email, _password);
    }

    public String addTeacher(String _name, String _email, String _username, String _password) {
        String isTeacherExist = "SELECT COUNT(1) FROM `TEACHER` WHERE username = '" + _username + "' OR email = '" + _email + "'";
        String insertTeacher = "insert into `TEACHER`(`name`, username,email,`password`) value ('" + _name + "','" + _username + "','" + _email + "','" + _password + "');";

        try {
            Statement statement = ApplicationState.connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(isTeacherExist);


            if (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    return "Teacher Exists";
                } else {
                    Statement statement2 = ApplicationState.connectDB.createStatement();
                    statement2.executeUpdate(insertTeacher);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "New Teacher Added";
    }

    public String assignCourseToTeacher(int courseId, int teacherId) {
        String insertTeacherCourse = "insert into TeacherCourse (courseId, userId) value (" + courseId + ", " + teacherId + ");";
        try {
            Statement statement = ApplicationState.connectDB.createStatement();
            statement.executeUpdate(insertTeacherCourse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Course Assigned to Teacher";
    }

    public ObservableList<Course> getCourses(String sql) throws SQLException {
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
        return courses;
    }

    public ObservableList<Teacher> getTeachers(String sql) throws SQLException {
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
        return teachers;
    }

    public ObservableList<Student> getStudents(String sql) throws SQLException {
        Statement statement = ApplicationState.connectDB.createStatement();
        ResultSet queryResult = statement.executeQuery(sql);

        ObservableList<Student> students = FXCollections.observableArrayList();

        while (queryResult.next()) {
            Student student = new Student(
                    queryResult.getInt("id"),
                    queryResult.getString("name"),
                    queryResult.getString("username"),
                    queryResult.getString("email"),
                    queryResult.getString("password"),
                    queryResult.getString("rollNo")
            );
            students.add(student);
        }
        return students;
    }

    public String approveStudentApplication(int courseId, int teacherId, ObservableList<Student> students) throws SQLException {
        int sectionCount = 0;
        String approveApplicationsCall = "{CALL ApproveStudentsApplications(?,?,?)}";
        CallableStatement statement = ApplicationState.connectDB.prepareCall(approveApplicationsCall);
        int count = 0;
        for (int i = 0; i < students.stream().count();i++)
        {
            Student student = students.get(i);
            statement.setInt(1, teacherId);
            statement.setInt(2, courseId);
            statement.setInt(3, student.getId());

            statement.executeQuery();
        }

        return "Application Approved";
    }
}