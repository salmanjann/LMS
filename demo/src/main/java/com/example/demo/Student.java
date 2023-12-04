package com.example.demo;

import javafx.scene.control.Button;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class Student extends  User{
    private  String rollNo;
    public Student(){
        super();
        rollNo = "";
    }
    public  Student(int _id, String _name,String _username, String _email, String _password,String _rollNo){
        super(_id, _name,_username,_email,_password);
        rollNo = _rollNo;
    }

    public String getRollNo() {
        return rollNo;
    }

    @Override
    public String toString() {
        return getName();
    }

    public void requestCourse(String courseName){
        String query = "insert into requestingCourse(studentName,courseName) value('"+ApplicationState.currentlyLoggedStudent.getRollNo()+"','"+courseName+"');";
        try{
            Statement statement = ApplicationState.connectDB.createStatement();
            statement.executeUpdate(query);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void checkFeedbackTable(){
        String check = "select count(1) from FeedbackStatus where studentName = '"+ApplicationState.currentlyLoggedStudent.getRollNo()+"';";
        try {
            Statement statement = ApplicationState.connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(check);

            while (queryResult.next()) {
                if(queryResult.getInt(1) >= 1){
                    // Student exist, no need to insert
                }
                else{
                    // fetch courses from studentSection Table, and insert in feedbackStatus table
                    // SQL query to fetch data from the studentSection Table
                    String selectQuery = "SELECT studentName,courseName FROM studentSections where studentName = '"+ApplicationState.currentlyLoggedStudent.getRollNo()+"';";

                    // SQL query to insert data into the FeedbackStatus table (NewStudent)
                    String insertQuery = "INSERT INTO FeedbackStatus (studentName, courseName, status) VALUES (?, ?, ?)";
                    try{
                        // Create a statement for the select query
                        PreparedStatement selectStatement = ApplicationState.connectDB.prepareStatement(selectQuery);

                        // Create a statement for the insert query
                        PreparedStatement insertStatement = ApplicationState.connectDB.prepareStatement(insertQuery);
                        // Execute the select query and get the result set
                        ResultSet resultSet = selectStatement.executeQuery();

                        // Iterate over the result set
                        while (resultSet.next()) {
                            // Extract data from the result set
                            String student = resultSet.getString(1);
                            String course = resultSet.getString(2);
                            // Add more columns as needed

                            // Set parameters for the insert statement
                            insertStatement.setString(1, student);
                            insertStatement.setString(2, course);
                            insertStatement.setString(3, "Not Submitted");

                            // Execute the insert statement
                            insertStatement.executeUpdate();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Course> getCourses(String sql) throws SQLException {
        Statement statement = ApplicationState.connectDB.createStatement();
        ResultSet queryResult = statement.executeQuery(sql);

        ObservableList<Course> courses = FXCollections.observableArrayList();

        while (queryResult.next()) {
            Course course = new Course(
                    queryResult.getString("courseId"),
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
}
