package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;

public class Teacher extends User{
    // No attributes now
    public Teacher(){
        super();
    }
    public  Teacher(int _id, String _name,String _username, String _email, String _password){
        super(_id, _name,_username,_email,_password);
    }

    @Override
    public String toString() {
        return getName();
    }

    public ObservableList<courseSection> getCourseSections(String sql) throws SQLException {
        Statement statement = ApplicationState.connectDB.createStatement();
        ResultSet queryResult = statement.executeQuery(sql);

        ObservableList<courseSection> sections = FXCollections.observableArrayList();

        while (queryResult.next()) {
            courseSection courseSec = new courseSection(
                    queryResult.getString("sectionName"),
                    queryResult.getString("courseName"),
                    queryResult.getString("teacherName")
            );
            sections.add(courseSec);
        }
        return sections;
    }

    public void MarkAttendance(int studentSectionId, LocalDate attendanceDate, boolean isPresent) throws SQLException {
        Statement statement = ApplicationState.connectDB.createStatement();
        String sql = "Insert into Attendance (studentSectionId,attendanceDate,IsPresent) values (" + studentSectionId + ", '" + attendanceDate + "'," + isPresent + ")";
        statement.executeUpdate(sql);
    }
}
